package com.letsplay.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import com.letsplay.repository.Player;
import com.letsplay.service.StaticSignupServiceUsage;
import com.vaadin.addon.locale.LocaleComboBox;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@VaadinSessionScope
public class SignupWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2455092946066475537L;
	
	public SignupWindow() {
		
		FormLayout form = new FormLayout();
		form.setMargin(true);
		
		TextField userName = new TextField("Username");
		userName.setRequiredIndicatorVisible(true);
		userName.setIcon(VaadinIcons.MAILBOX);
		
		PasswordField password = new PasswordField("Password");
		password.setRequiredIndicatorVisible(true);
		password.setIcon(VaadinIcons.PASSWORD);
		
		PasswordField confirmPassword = new PasswordField("Confirm Password");
		confirmPassword.setRequiredIndicatorVisible(true);
		confirmPassword.setIcon(VaadinIcons.PASSWORD);
		
		LocaleComboBox country = new LocaleComboBox();
		Locale[] temp = Locale.getAvailableLocales();
		ArrayList<Locale> visibleLocales = new ArrayList<Locale>(Arrays.asList(temp));
		visibleLocales.add(new Locale("en", "NG"));
		visibleLocales.add(new Locale("en", "GH"));
		
		country.setVisibleLocales(visibleLocales);
		country.setPlaceholder("Select Country");
		country.setRequiredIndicatorVisible(true);
		country.setIcon(VaadinIcons.LOCATION_ARROW_CIRCLE);
		country.setEmptySelectionAllowed(false);

		Button signUp = new Button("Sign Up");
		signUp.addClickListener(click -> {
			
			Player player = new Player();
			player.setUserName(userName.getValue());
			player.setPassword(password.getValue());
			Locale locale = country.getValue();
			
			player.setCountry(locale.getCountry());
			
			StaticSignupServiceUsage.registerUser(player);
			
			Notification.show("User registered",Type.HUMANIZED_MESSAGE);
			
			this.close();
		});
		
		signUp.setStyleName(ValoTheme.BUTTON_PRIMARY);

		form.addComponents(userName, password, confirmPassword, country, signUp);

		setContent(form);
		setModal(true);
		center();
		setResizable(false);
		addCloseListener(listener -> {
			UI.getCurrent().removeWindow(this);
		});
	}
	
}
