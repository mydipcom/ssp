package com.product.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.product.dao.base.BaseDao;
import com.product.dto.NapProduct;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 创建时间：2015年1月14日 下午1:44:05
 * 
 */
@Repository
public class NapAPorterDao extends BaseDao<NapProduct>{
	
	public List<String> getByGroupBy(String value){
		
		String hql = "from NapProduct as nap group by "+value;
		Query query = this.currentSession().createQuery(hql);
		List<String> strs =new ArrayList<String>();
		List list = query.list();
	    Iterator iterator=list.iterator();
	     while(iterator.hasNext()){
	    	   if(value.equals("category")){
	           strs.add((((NapProduct)iterator.next()).getCategory()).toString());}
	    	   else if(value.equals("brand")){
	    		   strs.add((((NapProduct)iterator.next()).getBrand()).toString());
	    	   }
	     }
		
		return strs;
	}

}
