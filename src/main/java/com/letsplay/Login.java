package com.letsplay;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoaderListener;

import com.letsplay.ui.HomeLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.UI;

@SpringUI(path = "/login")
@Theme("mytheme")
@VaadinSessionScope
public class Login extends UI{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -9112070551019561907L;

	@Autowired
    HomeLayout homeLayout;
    
	
    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }
    
    
	@Override
	protected void init(VaadinRequest request) {
		
			this.setContent(homeLayout);
		
		
	}

	@WebServlet(urlPatterns = "/*", name = "HomeUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = Login.class, productionMode = false)
	public static class HomeUIServlet extends SpringVaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4271979574370629630L;

	}
}
