package com.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.PageData;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.product.dao.MiLanDao;
import com.product.model.DataTableParamter;
import com.product.model.PagingData;
import com.product.service.MiLanService;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 2015年2月11日 上午10:24:20
 * 
 */
@Service
public class MiLanServiceImpl implements MiLanService{
    
	@Autowired
	private MiLanDao miLanDao;
	
	public PagingData loadMiLanList(DataTableParamter dtp) {
		String strJson = dtp.getsSearch();
		if(strJson != null && !strJson.isEmpty()){
			List<Criterion> criterions = new ArrayList<Criterion>();
			JSONObject jsonObject = JSONObject.parseObject(strJson);
			if(jsonObject.getString("category") != null && !jsonObject.getString("category").isEmpty()){
				criterions.add(Restrictions.eq("category",jsonObject.getString("category")));
			}
			if(jsonObject.getString("brand") != null && !jsonObject.getString("brand").isEmpty()){
				criterions.add(Restrictions.eq("brand",jsonObject.getString("brand")));
			}
			if(jsonObject.getString("pname") != null && !jsonObject.getString("pname").isEmpty()){
				criterions.add(Restrictions.like("pname", jsonObject.getString("pname"), MatchMode.ANYWHERE));
			}
			if(jsonObject.getString("price_from")!=null && !jsonObject.getString("price_from").isEmpty()&&
					   jsonObject.getString("price_to")!=null && !jsonObject.getString("price_to").isEmpty()){
		        criterions.add(Restrictions.between("mktprice", Float.parseFloat(jsonObject.getString("price_from")), Float.parseFloat(jsonObject.getString("price_to"))));
			}else if(jsonObject.getString("price_from")!=null && !jsonObject.getString("price_from").isEmpty()){
		        criterions.add(Restrictions.ge("mktprice", Float.parseFloat(jsonObject.getString("price_from"))));
		    }else if(jsonObject.getString("price_to")!=null && !jsonObject.getString("price_to").isEmpty()){
		    	criterions.add(Restrictions.le("mktprice", Float.parseFloat(jsonObject.getString("price_to"))));      
		    }
			if(jsonObject.getString("disprice_from")!=null && !jsonObject.getString("disprice_from").isEmpty()&&
					   jsonObject.getString("disprice_to")!=null && !jsonObject.getString("disprice_to").isEmpty()){
		        criterions.add(Restrictions.between("discount", Float.parseFloat(jsonObject.getString("disprice_from")), Float.parseFloat(jsonObject.getString("disprice_to"))));
			}else if(jsonObject.getString("disprice_from")!=null && !jsonObject.getString("disprice_from").isEmpty()){
		        criterions.add(Restrictions.ge("discount", Float.parseFloat(jsonObject.getString("disprice_from"))));
		    }else if(jsonObject.getString("disprice_to")!=null && !jsonObject.getString("disprice_to").isEmpty()){
		    	criterions.add(Restrictions.le("discount", Float.parseFloat(jsonObject.getString("disprice_to"))));      
		    }
			
			Criterion [] criterion = new Criterion[criterions.size()];
			for(int i=0;i<criterions.size();i++){
				criterion[i]=criterions.get(i);
			}
			
			return miLanDao.findPage(dtp.getsSortType(), dtp.issSort(),criterion, dtp.getiDisplayStart(), dtp.getiDisplayLength());
			
		}
		
		    return miLanDao.findPage(dtp.getsSortType(), dtp.issSort(), dtp.getiDisplayStart(), dtp.getiDisplayLength());
	}

	public List<String> getByGroupBy(String value) {
		// TODO Auto-generated method stub
		return miLanDao.getByGroupBy(value);
	}

	public long getUpdateTime() {
		// TODO Auto-generated method stub
		return miLanDao.getMaxValue("createtime");
	}

}
