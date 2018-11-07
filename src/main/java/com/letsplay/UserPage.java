package com.letsplay;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoaderListener;

import com.letsplay.ui.GameArea;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.UI;

@SpringUI(path = "/home")
@Theme("mytheme")
@VaadinSessionScope
@PreserveOnRefresh
public class UserPage extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1258878106012788721L;
	
	@Autowired
	GameArea gameArea;
	
	@Override
	protected void init(VaadinRequest request) {
		this.setContent(gameArea);
		this.setId("game");
	}
	
	@WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }
	
	@WebServlet(urlPatterns = "/*", name = "UserPageUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = UserPage.class, productionMode = false)
	public static class UserPageUIServlet extends SpringVaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2982260643338511845L;
		
	
	}
}

