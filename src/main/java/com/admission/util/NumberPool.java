package com.admission.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.admission.service.ApplicationService;

public class NumberPool {
	private static NumberPool instance;
	private static Logger log = Logger.getLogger(NumberPool.class);
	
	private ArrayList<Integer> idList = new ArrayList<Integer>();
	private Set<Integer> idSet = new HashSet<Integer>();
	
	private int nextNumber;
	
	private WebApplicationContext webApplication;
	
	private NumberPool() {
		
	}
	
	public static synchronized NumberPool getInstance() {
		if(instance == null) {
			instance = new NumberPool();
			instance.webApplication = ContextLoader.getCurrentWebApplicationContext();
		}
		
		return instance;
	}
	
	public synchronized void init() {
		nextNumber = 1;
		
		try {
			ApplicationService appService = (ApplicationService)webApplication.getBean("applicationService");
			List<Integer> ids = appService.getApplicationIdList();
			if(ids != null && ids.size() > 0) {
				int lastNumber = 0;
				for(int i=0; i<ids.size(); i++) {
					int tn = ids.get(i);
					for(int k=1; k<tn-lastNumber; k++) {
						idList.add(lastNumber+k);
					}
					
					lastNumber = tn;
				}
			}
		} catch (Exception e){
			log.debug("NumberPool init failed", e);
		}
		
		log.info(idList);
	}
	
	public synchronized int getNumber() {
		int number; 
		if(idList.size() > 0) {
			number = idList.remove(0);
			idSet.remove(number);
		} else {
			number = nextNumber;
			nextNumber++;
		}
		
		return number;
	}
	
	public synchronized void returnNumber(int number) {
		if(number <= 0)	return;
		if(idSet.contains(number))	return;
		
		for(int i=0; i<idList.size(); i++) {
			if(number < idList.get(i)) {
				idList.add(i, number);
				idSet.add(number);
				return;
			}
		}
		idList.add(number);
		idSet.add(number);
	}
}
