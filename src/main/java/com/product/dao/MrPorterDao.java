package com.product.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.product.dao.base.BaseDao;
import com.product.dto.MrProduct;
import com.product.dto.NapProduct;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 2015年2月4日 上午10:19:26
 * 
 */
@Repository
public class MrPorterDao extends BaseDao<MrProduct> {
	
public List<String> getByGroupBy(String value){
		
		String hql = "from MrProduct as nap group by "+value;
		Query query = this.currentSession().createQuery(hql);
		List<String> strs =new ArrayList<String>();
		List list = query.list();
	    Iterator iterator=list.iterator();
	     while(iterator.hasNext()){
	    	   if(value.equals("pcat")){
	           strs.add((((MrProduct)iterator.next()).getPcat()).toString());}
	    	   else if(value.equals("pbrand")){
	    		   strs.add((((MrProduct)iterator.next()).getPbrand()).toString());
	    	   }
	     }
		
		return strs;
	}

}
