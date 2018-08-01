package com.jt.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private HttpClientService httpClient;
	static ObjectMapper objectMapper=new ObjectMapper();
	@Override
	public Item findItemById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemById/";
		Map<String, String> params=new HashMap<>();
		params.put("itemId", itemId+"");
		
		String jsonResult=httpClient.doGet(url, params,null);
		//System.out.println(jsonResult);
		Item item=null;
		try {
			 item = objectMapper.readValue(jsonResult,Item.class );
			// System.out.println("==============");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return item;
	}
	
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemDescById/";
		Map<String, String> params=new HashMap<>();
		params.put("itemId", itemId+"");
			
		String jsonResult=httpClient.doGet(url, params);
		ItemDesc itemDesc=null;
		try {
			 itemDesc = objectMapper.readValue(jsonResult,ItemDesc.class );
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return itemDesc;
	}

}
