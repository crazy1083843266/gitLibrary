package com.jt.manage.vo;

import java.util.List;

import com.jt.manage.pojo.Item;

public class EasyUITree {
	private Integer total;	 //记录总数
	private List<Item> rows; //查询记录数
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<Item> getRows() {
		return rows;
	}
	public void setRows(List<Item> rows) {
		this.rows = rows;
	}
}
