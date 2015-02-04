package com.product.service;

import java.util.List;

import com.product.model.DataTableParamter;
import com.product.model.PagingData;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 2015年2月4日 上午10:24:15
 * 
 */
public interface MrPorterService {
	
	public PagingData loadMrPorterList(DataTableParamter dtp);
	
    List <String> getByGroupBy(String value);
	
	long getUpdateTime();

}
