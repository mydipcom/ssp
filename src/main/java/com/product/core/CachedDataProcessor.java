/**   
 * @Title: CachedDataProcessor.java 
 * @Package com.bps.core 
 *
 * @Description: User Points Management System
 * 
 * @date Nov 13, 2014 5:23:42 PM
 * @version V1.0   
 */ 
package com.product.core;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.product.service.SuService;
/** 
 * <p>Sping容器初始化完成后的缓存甚而数据的处理方法</p>
 * @ClassName: CachedDataProcessor 
 * @author Phills Li 
 * 
 */
public class CachedDataProcessor implements ApplicationListener<ContextRefreshedEvent> {
	
    public void onApplicationEvent(ContextRefreshedEvent event) {
		//root application context
    	if(event.getApplicationContext().getParent() == null){
    		SuService suService = (SuService)event.getApplicationContext().getBean("suService");
    		suService.cachedData();
    	}
    	else{//projectName-servlet  context
    		
    	}
    }
}
