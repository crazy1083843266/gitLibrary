package com.jt.manage.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.vo.ItemCatTree;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;
	

	// 通过spring依赖注入jedis对象
	@Autowired
	//private Jedis jedis;
	private JedisCluster jedisCluster;
	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 1.根据父级Id查询商品分类列表 2.将分类列表数据封装为ItemCatTree
	 */
	@Override
	public List<ItemCatTree> findItemCatById(Long parentId) {
		/*ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		// 1.查询数据 根据其中不为null的属性充当where条件
		List<ItemCat> itemCats = itemCatMapper.select(itemCat);*/
		List<ItemCat> itemCats = findItemCatCache(parentId);
		// 2.进行数据封装
		List<ItemCatTree> itemCatTreeList = new ArrayList<ItemCatTree>();
		for (ItemCat itemCatTemp : itemCats) {
			ItemCatTree itemCatTree = new ItemCatTree();
			itemCatTree.setId(itemCatTemp.getId());
			itemCatTree.setText(itemCatTemp.getName());
			// 是父级则closed 如果不是则open
			String state = itemCatTemp.getIsParent() == true ? "closed" : "open";
			itemCatTree.setState(state);
			itemCatTreeList.add(itemCatTree);
		}
		return itemCatTreeList;
	}

	/**
	 * 通过parentId 查询itemCats数据 1.先查缓存
	 * 
	 * @param parentId
	 * @return
	 */

	public List<ItemCat> findItemCatCache(Long parentId) {
		List<ItemCat> itemCats = new ArrayList<>();
		String key = "ITEM_CAT" + parentId;
		String jsonData = jedisCluster.get(key);
		try {
			if (StringUtils.isEmpty(jsonData)) {
				ItemCat itemCat = new ItemCat();
				itemCat.setParentId(parentId);
				itemCats = itemCatMapper.select(itemCat);

				// 将查询的结果先转换为Json后保存redis中
				String itemCatJson = objectMapper.writeValueAsString(itemCats);
				jedisCluster.set(key, itemCatJson);

			}else{
				//表示缓存中有数据，将ItemCatListJson转换List<ItenCat>对象
				ItemCat[] arrayItemCats = objectMapper.readValue(jsonData, ItemCat[].class);
				itemCats=Arrays.asList(arrayItemCats);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCats;
	}
	
	/**
	 * 步骤：
	 * 1.创建需要返回的对象ItemCatResult
	 * 2.准备一个合理的数据结构，封装子父级关系
	 * 	Map<parentId,List<ItemCat>>
	 * 3.准备一级商品分类菜单
	 */
	@Override
	public ItemCatResult findItemCatAll() {
		//1.定义封装对象
		ItemCatResult itemCatResult=new ItemCatResult();
		
		ItemCat itemCatTemp=new ItemCat();
		itemCatTemp.setStatus(1);
		//2.查询全部商品分类信息
		List<ItemCat> itemCatDBList=itemCatMapper.select(itemCatTemp);
		
		//2.1封装子父级关系
		Map<Long,List<ItemCat>> map=new HashMap<>();
		for (ItemCat itemCat : itemCatDBList) {
			
			if(map.containsKey(itemCat.getParentId())){
				//将该数据追加到list集合中
				map.get(itemCat.getParentId()).add(itemCat);
			}else{
				//代表第一次添加这个父级ID
				List<ItemCat> tempCatList=new ArrayList<>();
				tempCatList.add(itemCat);
				map.put(itemCat.getParentId(), tempCatList);
			}
		}
		
		//3.准备一级商品分类菜单
		List<ItemCatData> itemCatList1=new ArrayList<>();
//		itemCatResult.setItemCats(itemCatList1);
		
		//3.1遍历一级商品分类菜单封装一级商品分类
		for (ItemCat itemCat1 : map.get(0L)) {
			ItemCatData itemCatData1=new ItemCatData();
			
			itemCatData1.setUrl("/products/"+itemCat1.getId()+".html");
			itemCatData1.setName("<a href='"+itemCatData1.getUrl()+"'>"+itemCat1.getName()+"</a>");
			
			//3.2	封装2级商品分类菜单
			List<ItemCatData> itemCatList2=new ArrayList<>();
			for (ItemCat itemCat2 : map.get(itemCat1.getId())) {
				ItemCatData itemCatData2=new ItemCatData();
				itemCatData2.setUrl("/product/"+itemCat2.getId());
				itemCatData2.setName(itemCat2.getName());
				
				//3.3	封装3级商品分类菜单
				List<String> itemCatList3=new ArrayList<>();
				for (ItemCat itemCat3 : map.get(itemCat2.getId())) {
					itemCatList3.add("/products/"+itemCat2.getId()+"|"+itemCat3.getName());
				}
				itemCatData2.setItems(itemCatList3);
				itemCatList2.add(itemCatData2);
			}
			
			itemCatData1.setItems(itemCatList2);
			
			if(itemCatList1.size()>13){
				break;
			}
			itemCatList1.add(itemCatData1);
			System.out.println(itemCatList1);
		}
		itemCatResult.setItemCats((itemCatList1));
		return itemCatResult;
	}
	/**
	 * 1.用户查询时先查询缓存
	 * 2.将缓存中的数据转换为需要的对象
	 */
	@Override
	public ItemCatResult findItemCatCache() {
		String key="ITEM_CAT_ALL";
		String jsonData = jedisCluster.get(key);
		ItemCatResult itmeCatResult=null;
		try {
			if(StringUtils.isEmpty(jsonData)){
				itmeCatResult=findItemCatAll();
				
				String jsonResult = objectMapper.writeValueAsString(itmeCatResult);
				jedisCluster.set(key, jsonResult);
				//System.out.println("第一次");
			}else{
				itmeCatResult = objectMapper.readValue(jsonData, ItemCatResult.class);
				//System.out.println("第二次");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itmeCatResult;
	}

}
