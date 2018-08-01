package com.jt.manage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;
import com.jt.manage.vo.EasyUITree;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/findAll")
	@ResponseBody //将数据转化为JSON
	public List<Item> findAll(){
		
		return itemService.findAll();
	}
	
	///query?page=1&rows=50
	@RequestMapping("/query")
	@ResponseBody
	public EasyUITree findItemByPage(int page,int rows){
		
		return itemService.findItemByPage(page,rows);
	}
	
	
	//实现商品分类目录回显
	/**
	 * 1.如果返回对象  使用@ResponseBody时采用UTF-8编码进行数据返回
	 * 2.如果返回字符串 则使用默认的ISO-8859-1编码
	 * 
	 * @param itemId  
	 * @return
	 */
	@RequestMapping(value="/cat/queryItemName",produces="text/html;charset=utf-8")
	@ResponseBody  //{userJSON串} 返回字符串 
	public String findItemCatName(Long itemId){
		
		return itemService.findItemCatName(itemId);
	}
	
	//例子
	/*public void findName(Long itemId,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		User user = itemService.findItemCatName(itemId);
		//将User转化为JSON数据后返回
		
		response.getWriter().write(name);
	}*/
	
	
	//实现商品的新增
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc){
		try {
			//实现商品详情入库
			itemService.saveItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品入库失败");
	}
	
	//实现商品的修改
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc){
		try {
			itemService.updateItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品修改失败");
	}
	
	//实现商品下架
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(Long[] ids){
		try {
			int status = 2; //下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201,"商品下架失败");
	}
	
	
	//商品上架
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelf(Long[] ids){
		try {
			int status = 1; //下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品上架失败");
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids){
		try {
			itemService.deleteItems(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品删除失败");
	}
	
	//实现商品描述信息查询
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDesc(@PathVariable Long itemId){
		try {
			//页面中需要展现商品详情信息
			ItemDesc itemDesc = itemService.findItemDesc(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201,"商品描述查询失败");
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
