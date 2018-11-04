package com.letsplay.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class HomeLayout extends CustomComponent{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4385017434567626205L;

	@Autowired
	private LoginWindow login;
	
	@Autowired
	private SignupWindow signup;
	
	public HomeLayout(){
	        super();
	       
	         Label header = new Label("Welcome to Lets Play");
	         header.setStyleName(ValoTheme.LABEL_H1);

	         Button logIn = new Button("Log In");
	         logIn.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
	         logIn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
	         logIn.setIcon(VaadinIcons.MALE);
	         logIn.addClickListener(e -> {
	        	 login.setHeight("200px");
	        	 login.setWidth("400px");
	        	 UI.getCurrent().addWindow(login);
	        	 
	         });
	         
	         Button signUp = new Button("Sign Up");
	         signUp.addStyleName(ValoTheme.BUTTON_FRIENDLY);
	         signUp.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
	         signUp.setIcon(VaadinIcons.FORM);
	         signUp.addClickListener(click -> {
	        	 
	        	 signup.setWidth("400px");
	        	 UI.getCurrent().addWindow(signup);
	         
	         });
	         
	         HorizontalLayout hLayout = new HorizontalLayout();
	         hLayout.setStyleName(ValoTheme.UI_WITH_MENU);
	         hLayout.setWidth("100%");
	         hLayout.setId("Child Layout");
	         hLayout.addComponents(header, logIn, signUp);
	         hLayout.setExpandRatio(header, 1.0f);
	         
	         
	         VerticalLayout vLayout = new VerticalLayout();
	         vLayout.addComponent(hLayout);
	         vLayout.setId("Main Layout");
	         super.setCompositionRoot(vLayout);
	    }
}
