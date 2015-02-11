package com.product.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.product.model.DataTableParamter;
import com.product.model.PagingData;
import com.product.service.MiLanService;

/**
 * @author silvasong E-mail:silvasong@campray.com
 * @version 2015年2月11日 上午10:27:31
 * 
 */
@Controller
@RequestMapping(value="/product")
public class MiLanProductController {
	
	@Autowired
	private MiLanService miLanService;
	
	@RequestMapping(value="milan",method=RequestMethod.GET)
	public ModelAndView miLan(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		List <String> category = miLanService.getByGroupBy("category");
		mav.addObject("category", category);
		List <String> brand = miLanService.getByGroupBy("brand");
		mav.addObject("brand", brand);
		long createtime = miLanService.getUpdateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date(createtime));
		mav.addObject("createtime",time);
		mav.setViewName("product/milan");
		return mav;
	}
	
	@RequestMapping(value="milan_list",method=RequestMethod.GET)
	@ResponseBody
	public String miLanList(HttpServletRequest request,DataTableParamter dtp){
		PagingData pagingData = miLanService.loadMiLanList(dtp);
		Object [] obj = null;
		if(pagingData.getAaData() == null){
			obj = new Object[]{};
			pagingData.setAaData(obj);
		}
		return JSON.toJSONString(pagingData);
	}

}
