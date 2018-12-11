package com.letsplay.ui;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public class Scoreboard extends CustomComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2760277948452723296L;
	private TextField score;
	private int intScore;
                     
	private Label name;
	
	public Scoreboard(){
		this.intScore = 0;
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(false);
		this.name = new Label();
		
		this.score = new TextField();
		this.score.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
		this.score.addStyleName(ValoTheme.TEXTFIELD_LARGE);
		this.score.setWidth("110px");
		this.score.setReadOnly(true);
		this.score.setValue("0");
		layout.addComponents(this.name, this.score);
		
		setCompositionRoot(layout);
		
		
	}
	
	protected void setScore(int score) {
		this.intScore += score;
		this.score.setValue(Integer.toString(this.intScore));
	}
                      
	protected void setName(String name){
        this.name.setValue(name);
    }
	
	protected void resetScore() {
		this.intScore = 0;
		this.score.setValue(Integer.toString(this.intScore));
	}
	
	protected int getScore() {
		return this.intScore;
	}
}
