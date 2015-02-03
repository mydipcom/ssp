package com.product.service;

import java.util.List;

import com.product.model.DataTableParamter;
import com.product.model.PagingData;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 创建时间：2015年1月14日 下午1:48:14
 * 
 */
public interface NapAPorterService {
	
	PagingData loadNapAPorterList(DataTableParamter dtp);
	
	List <String> getByGroupBy(String value);
	
	long getUpdateTime();

}
