package com.jt.manage.service;

import java.util.List;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.vo.ItemCatTree;

public interface ItemCatService {

	List<ItemCatTree> findItemCatById(Long parentId);

	ItemCatResult findItemCatAll();

	ItemCatResult findItemCatCache();

}
