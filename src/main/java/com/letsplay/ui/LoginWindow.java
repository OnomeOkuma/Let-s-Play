package com.letsplay.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

import com.letsplay.logic.Gamestate;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@VaadinSessionScope
public class LoginWindow extends Window{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 14601904453460151L;
	@Autowired
	Gamestate gamestate;

	@Autowired
	VaadinSharedSecurity vaadinSecurity;
	 
	public LoginWindow() {
	
		FormLayout form = new FormLayout();
		form.setMargin(true);

		TextField username = new TextField("Email");
		username.setRequiredIndicatorVisible(true);
		username.setIcon(VaadinIcons.MAILBOX);

		PasswordField password = new PasswordField("Password");
		password.setRequiredIndicatorVisible(true);
		password.setIcon(VaadinIcons.PASSWORD);

		Button logIn = new Button("Log in");
		logIn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		logIn.addClickListener(click -> {
			
			try {
				
				vaadinSecurity.login(username.getValue(), password.getValue());
				
			} catch (AuthenticationException e) {
				Notification.show("Incorrect Username or Password",Type.ERROR_MESSAGE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		form.addComponents(username, password, logIn);
		setContent(form);
		setModal(true);
		center();
		setCaption("Log In");
		setResizable(false);

	}
}
