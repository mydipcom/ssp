/**   
 * @Title: UserController.java 
 * @Package com.uswop.action 
 * @Description: TODO
 * @author Phills Li    
 * @date Sep 2, 2014 7:25:22 PM 
 * @version V1.0   
 */
package com.product.action;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.product.commons.SystemConfig;
import com.product.dto.SuProduct;
import com.product.model.DataTableParamter;
import com.product.model.PagingData;
import com.product.model.SuCategoryModel;
import com.product.service.MrPorterService;
import com.product.service.NapAPorterService;
import com.product.service.SuService;





/**
 * @ClassName: ProductController
 * @Description: TODO
 * @author 
 * @date 
 * 
 */
@Controller
@RequestMapping("/product")
public class NatProductController extends BaseController {

	private Logger logger = Logger.getLogger(NatProductController.class);
	
	@Autowired
	private NapAPorterService napAPorterService;
	
	
	
	
	
	@RequestMapping(value="nat_a_porter",method=RequestMethod.GET)
	public ModelAndView natAPorter(HttpServletRequest request){
		List<String> category =napAPorterService.getByGroupBy("category");
		List<String> brand =napAPorterService.getByGroupBy("brand");
	    ModelAndView mav=new ModelAndView();
		mav.addObject("category",category);
		mav.addObject("brand",brand);
		long createtime = napAPorterService.getUpdateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date(createtime));
		mav.addObject("createtime",time);
		mav.setViewName("product/nat_a_porter");
		return mav;
	}
	
	@RequestMapping(value="nat_a_porter_list" ,method=RequestMethod.GET)
	@ResponseBody
	public String natAPorterList(HttpServletRequest request,DataTableParamter dtp){
		PagingData pagingData =napAPorterService.loadNapAPorterList(dtp);
		Object objects[] = pagingData.getAaData();
		if(objects == null){
			objects = new Object[]{};
			pagingData.setAaData(objects);
		}
		String respString =JSON.toJSONString(pagingData);
		return respString;
	}
	
	
	
}
