package com.product.service.impl;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Array;
import com.product.commons.SystemConfig;
import com.product.dao.SuDao;
import com.product.dto.SuProduct;
import com.product.model.DataTableParamter;
import com.product.model.PagingData;
import com.product.service.SuService;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 创建时间：2015年1月16日 下午3:21:52
 * 
 */
public class SuServiceImpl implements SuService{
    
	@Autowired
	private SuDao suDao;
	
	public PagingData loadSuProductList(DataTableParamter dtp) {
		// TODO Auto-generated method stub
		String strJson = dtp.getsSearch();
		if(strJson != null && !strJson.isEmpty()){
			List<Criterion> criterions = new ArrayList<Criterion>();
			JSONObject jsonObject = JSONObject.parseObject(strJson);
			if(!jsonObject.getString("vendorName").equals("0")){
					if(jsonObject.getString("vendorName").equals("1")){
						criterions.add(Restrictions.eq("vendorName", "苏宁自营"));
					}else if(jsonObject.getString("vendorName").equals("2")){
						criterions.add(Restrictions.ne("vendorName", "苏宁自营"));
					}
					
			}
			if(jsonObject.getString("category-one") != null && !jsonObject.getString("category-one").isEmpty()){
				String category = "";
				category += jsonObject.getString("category-one")+">";
				if(jsonObject.getString("category-two") != null && !jsonObject.getString("category-two").isEmpty()){
					category += jsonObject.getString("category-two")+">";
					if(jsonObject.getString("category-three") != null && !jsonObject.getString("category-three").isEmpty()){
						category += jsonObject.getString("category-three");
					}
				}
				criterions.add(Restrictions.like("productCatagory", category,MatchMode.START));
			}
			if(jsonObject.getString("price_from")!=null && !jsonObject.getString("price_from").isEmpty()&&
					   jsonObject.getString("price_to")!=null && !jsonObject.getString("price_to").isEmpty()){
		        criterions.add(Restrictions.between("promotionPrice", Float.parseFloat(jsonObject.getString("price_from")), Float.parseFloat(jsonObject.getString("price_to"))));
			}else if(jsonObject.getString("price_from")!=null && !jsonObject.getString("price_from").isEmpty()){
		        criterions.add(Restrictions.ge("promotionPrice", Float.parseFloat(jsonObject.getString("price_from"))));
		    }else if(jsonObject.getString("price_to")!=null && !jsonObject.getString("price_to").isEmpty()){
		    	criterions.add(Restrictions.le("promotionPrice", Float.parseFloat(jsonObject.getString("price_to"))));      
		    }
			if(jsonObject.getString("disprice_from")!=null && !jsonObject.getString("disprice_from").isEmpty()&&
					   jsonObject.getString("disprice_to")!=null && !jsonObject.getString("disprice_to").isEmpty()){
		        criterions.add(Restrictions.between("disPrice", Float.parseFloat(jsonObject.getString("disprice_from")), Float.parseFloat(jsonObject.getString("disprice_to"))));
			}else if(jsonObject.getString("disprice_from")!=null && !jsonObject.getString("disprice_from").isEmpty()){
		        criterions.add(Restrictions.ge("disPrice", Float.parseFloat(jsonObject.getString("disprice_from"))));
		    }else if(jsonObject.getString("disprice_to")!=null && !jsonObject.getString("disprice_to").isEmpty()){
		    	criterions.add(Restrictions.le("disPrice", Float.parseFloat(jsonObject.getString("disprice_to"))));      
		    }
			
			if(jsonObject.getString("productName") != null && !jsonObject.getString("productName").isEmpty()){
				criterions.add(Restrictions.ilike("productName", jsonObject.getString("productName"), MatchMode.ANYWHERE));
			}
			
			Criterion [] criterion = new Criterion[criterions.size()];
			for(int i=0;i<criterions.size();i++){
				criterion[i]=criterions.get(i);
			}
			
			return suDao.findPage(dtp.getsSortType(), dtp.issSort(),criterion, dtp.getiDisplayStart(), dtp.getiDisplayLength());
			
		}
		
		    return suDao.findPage(dtp.getsSortType(), dtp.issSort(), dtp.getiDisplayStart(), dtp.getiDisplayLength());
	}

	public List<String> getByGroupBy(String value) {
		// TODO Auto-generated method stub
		return suDao.getByGroupBy(value);
	}

	public void cachedData() {
		// TODO Auto-generated method stub
		Map<String,List<String>> categoryMap = null;
		List<String> categoryList =null;
		List<String> categorys = suDao.getByGroupBy("productCatagory");
		for(String category:categorys){
			String str[] = category.split(">");
			if(str.length == 3){
			  if(!SystemConfig.suningCategory.containsKey(str[0])){
				categoryList = new ArrayList<String>();
				categoryMap = new LinkedHashMap<String, List<String>>();
				categoryList.add(str[2]);
				categoryMap.put(str[1], categoryList);
				SystemConfig.suningCategory.put(str[0], categoryMap);
			   }else{
				   categoryMap = SystemConfig.suningCategory.get(str[0]);
				   if(!categoryMap.containsKey(str[1])){
					   categoryList = new ArrayList<String>();
					   categoryList.add(str[2]);
					   categoryMap.put(str[1], categoryList);
					   SystemConfig.suningCategory.put(str[0], categoryMap);
				   }else{
					   categoryList = categoryMap.get(str[1]);
					   if(!categoryList.contains(str[2])){
						   categoryList.add(str[2]);
						   categoryMap.put(str[1], categoryList);
						   SystemConfig.suningCategory.put(str[0], categoryMap);
						}
				   }
			   }
			   
			}
		}
	}

	public long getUpdateTime() {
		// TODO Auto-generated method stub
		return suDao.getMaxValue("createtime");
	}

	public List<SuProduct> loadAll() {
		// TODO Auto-generated method stub
		return suDao.LoadAll();
	}

	public void updateSuProduct(SuProduct suProduct) {
		// TODO Auto-generated method stub
		suDao.update(suProduct);
	}
     
	
	
	
}
