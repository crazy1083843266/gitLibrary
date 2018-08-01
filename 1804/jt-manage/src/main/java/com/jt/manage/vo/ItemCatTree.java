package com.jt.manage.vo;
//为了实现商品分类数据展现
public class ItemCatTree {
	private Long id;        //id
	private String text;	//文本内容
	private String state;	//节点是否打开
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
