package com.jt.search.service;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;

import com.jt.dubbo.pojo.Item;
import com.jt.dubbo.service.DubboSearchService;

public class DubboSearchServiceImpl implements DubboSearchService{
	
	@Autowired
	private HttpSolrServer httpSolrServer;
	
	
	@Override
	public List<Item> findItemsByKeyWord(String keyWord) {
		//定义查询参数
		SolrQuery  solrQuery = new SolrQuery();
		solrQuery.setQuery(keyWord);	//定义查询关键字
		solrQuery.setStart(0);
		solrQuery.setRows(20);
		
		try {
			QueryResponse queryResponse = httpSolrServer.query(solrQuery);	
			List<Item> itemList = queryResponse.getBeans(Item.class);
			System.out.println("全文检索实现成功!!!!!!!!!!!!!!!!!");
			return itemList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
