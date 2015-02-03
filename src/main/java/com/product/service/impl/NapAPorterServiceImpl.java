package com.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Array;
import com.product.commons.ConvertTools;
import com.product.dao.NapAPorterDao;
import com.product.model.DataTableParamter;
import com.product.model.PagingData;
import com.product.service.NapAPorterService;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 创建时间：2015年1月14日 下午1:47:55
 * 
 */
public class NapAPorterServiceImpl implements NapAPorterService{
    
	@Autowired
	private NapAPorterDao napAPorterDao;
	
	public PagingData loadNapAPorterList(DataTableParamter dtp) {
		// TODO Auto-generated method stub
		String searchStr = dtp.getsSearch();
		List<Criterion> criterions = new ArrayList<Criterion>();
		if(searchStr !=null && !searchStr.isEmpty()){
			JSONObject jsonObject = JSONObject.parseObject(searchStr);
			Set<String> keys=jsonObject.keySet();
			for(String key :keys){
				String value = jsonObject.getString(key);
				if(value != null && !value.isEmpty()){
					if(!key.equals("price_from") && !key.equals("price_to")){
						criterions.add(Restrictions.eq(key, value));
					}
				}
			}
			if(jsonObject.getString("price_from")!=null && !jsonObject.getString("price_from").isEmpty()&&
			   jsonObject.getString("price_to")!=null && !jsonObject.getString("price_to").isEmpty()){
                criterions.add(Restrictions.between("price", Float.parseFloat(jsonObject.getString("price_from")), Float.parseFloat(jsonObject.getString("price_to"))));
			}else if(jsonObject.getString("price_from")!=null && !jsonObject.getString("price_from").isEmpty()){
		        criterions.add(Restrictions.ge("price", Float.parseFloat(jsonObject.getString("price_from"))));
		    }else if(jsonObject.getString("price_to")!=null && !jsonObject.getString("price_to").isEmpty()){
		    	criterions.add(Restrictions.le("price", Float.parseFloat(jsonObject.getString("price_to"))));      
		    }
			
			Criterion criterion [] = new Criterion[criterions.size()];
			for(int i=0 ;i<criterions.size();i++){
				criterion[i]=criterions.get(i);
			}
			return napAPorterDao.findPage("price",dtp.issSort(),criterion, dtp.getiDisplayStart(), dtp.getiDisplayLength());
		}
		return napAPorterDao.findPage("price",dtp.issSort(),dtp.iDisplayStart, dtp.iDisplayLength);
		      
	}

	
	public List<String> getByGroupBy(String value) {
		// TODO Auto-generated method stub
	    return napAPorterDao.getByGroupBy(value);
	}


	public long getUpdateTime() {
		// TODO Auto-generated method stub
		return napAPorterDao.getMaxValue("createtime");
	}

}
