package com.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.product.dao.MrPorterDao;
import com.product.model.DataTableParamter;
import com.product.model.PagingData;
import com.product.service.MrPorterService;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 2015年2月4日 上午10:24:53
 * 
 */
@Service
public class MrPorterServiceImpl implements MrPorterService {
    
	@Autowired 
	private MrPorterDao mrPorterDao;
	
	public PagingData loadMrPorterList(DataTableParamter dtp) {
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
					return mrPorterDao.findPage("price",dtp.issSort(),criterion, dtp.getiDisplayStart(), dtp.getiDisplayLength());
				}
				return mrPorterDao.findPage("price",dtp.issSort(),dtp.iDisplayStart, dtp.iDisplayLength);
	}

	public List<String> getByGroupBy(String value) {
		// TODO Auto-generated method stub
		return mrPorterDao.getByGroupBy(value);
	}

	public long getUpdateTime() {
		// TODO Auto-generated method stub
		return mrPorterDao.getMaxValue("createtime");
	}
	
	

}
