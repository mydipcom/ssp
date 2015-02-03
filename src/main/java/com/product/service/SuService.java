package com.product.service;

import java.util.List;

import com.product.dto.SuProduct;
import com.product.model.DataTableParamter;
import com.product.model.PagingData;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 创建时间：2015年1月16日 下午3:20:58
 * 
 */
public interface SuService {
	
	PagingData loadSuProductList(DataTableParamter dtp);
	
	List <String> getByGroupBy(String value);
	
	void cachedData();
	
	long getUpdateTime();
	
	List<SuProduct> loadAll();
	
	void updateSuProduct(SuProduct suProduct);

}
