package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.service.ItemCatService;
import com.jt.manage.service.ItemService;
import com.jt.manage.vo.ItemCatTree;

@Controller
@RequestMapping("/item")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	//实现商品分类列表
	/**
	 * @RequestParam(value="",defaultValue="",required=true)
	 * value=""接收参数的名称
	 * defaultValue=""默认值 如果参数为null 则添加默认值
	 * required=true 表示改参数必须传递,否则违反SpringMVC校验规则,返回报错信息.
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/cat/list")
	@ResponseBody
	public List<ItemCatTree> findItemCatById
	(@RequestParam(value="id",
				   defaultValue="0",
				   required=true) Long parentId){
		//1.实现商品一级分类查询
		//Long parentId = 0L;
		return itemCatService.findItemCatById(parentId);
	}
}
