package com.letsplay.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.letsplay.repository.Player;
import com.letsplay.service.StaticSignupServiceUsage;
import com.letsplay.utils.ConfirmPasswordValidator;
import com.letsplay.utils.UsernameValidator;
import com.vaadin.addon.locale.LocaleComboBox;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
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
	
	@Autowired
	public SignupWindow(UsernameValidator usernameValidator, ConfirmPasswordValidator confirmPasswordValidator) {
		
		FormLayout form = new FormLayout();
		form.setMargin(true);
		Player player = new Player();
		Binder<Player> usernameBinder = new Binder<Player>();
		Binder<Player> confirm = new Binder<Player>();
		
		TextField userName = new TextField("Username");
		userName.setRequiredIndicatorVisible(true);
		userName.setIcon(VaadinIcons.MAILBOX);
		usernameBinder.forField(userName)
						.withValidator(usernameValidator)
						.bind(Player:: getUserName, Player:: setUserName);
		
		PasswordField password = new PasswordField("Password");
		password.setRequiredIndicatorVisible(true);
		password.setIcon(VaadinIcons.PASSWORD);
		password.addValueChangeListener(listener -> {
			confirmPasswordValidator.setPasswordToCheckAgainst(password.getValue());
		});
		
		PasswordField confirmPassword = new PasswordField("Confirm Password");
		confirmPassword.setRequiredIndicatorVisible(true);
		confirmPassword.setIcon(VaadinIcons.PASSWORD);
		confirm.forField(confirmPassword)
				.withValidator(confirmPasswordValidator)
				.bind(Player::getPassword, Player::setPassword);
		
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
			
			
			try {
				usernameBinder.writeBean(player);
				confirm.writeBean(player);
				Locale locale = country.getValue();
				
				player.setCountry(locale.getCountry());
				
				StaticSignupServiceUsage.registerUser(player);
				
				Notification.show("User registered",Type.HUMANIZED_MESSAGE);
				
				this.close();
			} catch (ValidationException e) {
				Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
			}
			
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
