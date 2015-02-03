package com.product.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.product.dao.base.BaseDao;

import com.product.dto.SuProduct;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 创建时间：2015年1月16日 下午3:19:32
 * 
 */

@Repository
public class SuDao extends BaseDao<SuProduct>{
public List<String> getByGroupBy(String value){
		
		String hql = "from SuProduct as su group by "+value;
		Query query = this.currentSession().createQuery(hql);
		List<String> strs =new ArrayList<String>();
		List list = query.list();
	    Iterator iterator=list.iterator();
	     while(iterator.hasNext()){
	    	   strs.add(((SuProduct)iterator.next()).getProductCatagory());
	     }
		
		return strs;
	}

}
