package com.product.service;

import java.util.List;

import com.product.model.DataTableParamter;
import com.product.model.PagingData;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 2015年2月11日 上午10:23:50
 * 
 */
public interface MiLanService {
	
	public PagingData loadMiLanList(DataTableParamter dtp);
	
	public List <String> getByGroupBy(String value);
	
	public long getUpdateTime();

}
