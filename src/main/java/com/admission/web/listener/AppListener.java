package com.admission.web.listener;

import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.logicalcobwebs.proxool.ProxoolFacade;

import com.admission.util.NumberPool;

public class AppListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ProxoolFacade.shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		TimeZone time = TimeZone.getTimeZone("GMT+8");
		TimeZone.setDefault(time);
		NumberPool.getInstance().init();
	}

}
