package com.jt.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jt.dubbo.pojo.Item;
import com.jt.dubbo.service.DubboSearchService;

/**
 * 作用：全文检索
 * 接收关键字，返回关键字相关商品列表
 * query
 * itemList
 * @author Administrator
 *
 */
@Controller
public class SearchController {
	@Autowired
	private DubboSearchService searchService;
	
	
	@RequestMapping("/search")
	public String searchItem(@RequestParam("q")String keyWord,Model model) throws UnsupportedEncodingException{
		//处理乱码
		keyWord=new String(keyWord.getBytes("ISO-8859-1"),"utf-8");
		List<Item> itemList=searchService.findItemsByKeyWord(keyWord);
		model.addAttribute("query", keyWord);
		model.addAttribute("itemList", itemList);
	
		return "search";
	}
}
