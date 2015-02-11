package com.product.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.product.dao.base.BaseDao;
import com.product.dto.MiLanProduct;
import com.product.dto.MrProduct;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 2015年2月11日 上午10:21:55
 * 
 */
@Repository
public class MiLanDao extends BaseDao<MiLanProduct>{
	
public List<String> getByGroupBy(String value){
		
		String hql = "from MiLanProduct as milan group by "+value;
		Query query = this.currentSession().createQuery(hql);
		List<String> strs =new ArrayList<String>();
		List list = query.list();
	    Iterator iterator=list.iterator();
	     while(iterator.hasNext()){
	    	   if(value.equals("category")){
	           strs.add((((MiLanProduct)iterator.next()).getCategory()).toString());}
	    	   else if(value.equals("brand")){
	    		   strs.add((((MiLanProduct)iterator.next()).getBrand()).toString());
	    	   }
	     }
		
		return strs;
	}

}
