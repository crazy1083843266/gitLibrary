package com.jt.manage.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.vo.EasyUITree;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public List<Item> findAll() {
		
		return itemMapper.findAll();
	}
	
	/**
	 * 1.total 记录总数
	 * 2.rows  分页后的结果
	 */
	/* (non-Javadoc)
	 * @see com.jt.manage.service.ItemService#findItemByPage(int, int)
	 */
	@Override
	public EasyUITree findItemByPage(int page, int rows) {
		//获取总数
		//int total = itemMapper.findCount();
		int total = itemMapper.selectCount(null);
		
		/**
		 * 例子:
		 * 	select * from tb_item limit 起始位置,查询行数
		 * 	select * from tb_item limit 0,20  第一页
		 * 	select * from tb_item limit 20,20 第二页
		 * 	select * from tb_item limit 40,20 第三页
		 * 	select * from tb_item limit (page-1)*rows,rows 第三页
		 */
		int start = (page - 1) * rows ;
		List<Item> rowList = itemMapper.findItemByPage(start,rows);
		EasyUITree easyUITree = new EasyUITree();
		easyUITree.setTotal(total);
		easyUITree.setRows(rowList);
		
		return easyUITree;
	}

	@Override
	public String findItemCatName(Long itemId) {
		
		//暂时借用itmemapper查询,后期维护时改进
		return itemMapper.findItemCatName(itemId);
	}

	@Override
	public void saveItem(Item item,String desc) {
		//封装数据
		item.setStatus(1);  //1正常，2下架，3删除
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		//实现商品详情入库操作
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId()); //mybatis动态获取插入最大值
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void updateItem(Item item,String desc) {
		item.setUpdated(new Date());
		//采用动态更新的操作,只更新不为null的数据
		itemMapper.updateByPrimaryKeySelective(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}

	@Override
	public void updateStatus(Long[] ids, int status) {
		
		/*for (Long id : ids) {
			Item item  = new Item();
			item.setId(id);
			item.setStatus(status);
			itemMapper.updateByPrimaryKeySelective(item);
		}*/
		
		itemMapper.updateStatus(ids,status);
	}

	@Override
	public void deleteItems(Long[] ids) {
		
		itemMapper.deleteByIDS(ids);
		itemDescMapper.deleteByIDS(ids);
	}

	@Override
	public ItemDesc findItemDesc(Long itemId) {
		
		return itemDescMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public Item findItemById(Long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}
	
	
	
	
	
	
}
