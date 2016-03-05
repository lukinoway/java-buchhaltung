package konto.ui.view.User;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import konto.data.DBUtil.IUser;
import konto.data.DBUtil.UserDBUtil;
import konto.data.model.LoginUser;

public class NewUserWindow extends Window{
	
	private static final long serialVersionUID = 1L;
	
    // Layout stuff
    GridLayout gridView = new GridLayout(2, 4);
    TextField userName = new TextField("Username");
    TextField userPass = new TextField("Passwort");
    
    Button saveBtn = new Button("speichern");
    Button cancelBtn = new Button("abbrechen");
    
    IUser userUtil = new UserDBUtil();

    
	public NewUserWindow() {
		this.setContent(gridView);
		this.center();
		this.setCaption("Neuer Benutzer");
		buildGrid();
		
		
		// add new entry
		saveBtn.addClickListener(new ClickListener() {
	
		    private static final long serialVersionUID = 1L;
	
		    @Override
		    public void buttonClick(ClickEvent event) {
		    	addData();	
		    }
		});
		
		// close window
		cancelBtn.addClickListener(new ClickListener() {
	

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				NewUserWindow.this.close();
				
			}
			
		});
		
		
		// add data on enter
		saveBtn.addShortcutListener(new ShortcutListener("enter User", ShortcutAction.KeyCode.ENTER, null) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				addData();	
			}
		});
		
		// close window on ESC
		this.addShortcutListener(new ShortcutListener("ESC User", ShortcutAction.KeyCode.ESCAPE, null) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				NewUserWindow.this.close();
			}
		});
	}
	
	
    private void buildGrid() {
		gridView.addComponent(userName, 0, 0, 1, 0);
		userName.setWidth(100, Unit.PERCENTAGE);
		
		gridView.addComponent(userPass, 0, 1, 1, 1);
		userPass.setWidth(100, Unit.PERCENTAGE);
		
		gridView.addComponent(saveBtn, 0, 3, 0, 3);
		gridView.addComponent(cancelBtn, 1, 3, 1, 3);
		gridView.setMargin(true);
    }
    
    
	/**
	 * validate Input
	 * @return
	 */
	private boolean validateInput() {
		boolean  valid = false;
		try {
			if (userName == null || userName.getValue() == "") {
				userName.focus();
				throw new NullPointerException("User Name fehlt");
			}
			if (userPass == null || userPass.getValue() == "") {
				userPass.focus();
				throw new NullPointerException("User Passwort fehlt");
			}
			else {
				valid = true;
			}
		} catch (NullPointerException e) {
			System.out.println("NewUserWindow - " + e);
		}
		
		return valid;
	}
	
	
    public void addData() {
    	if(validateInput()) {
    		try {
    			LoginUser user = new LoginUser(userName.getValue(), userPass.getValue());	
    			System.out.println("new User: '" + user.getUserName() + "'");
    			System.out.println("passhash: '" + user.getUserHash() + "'");
    			
    			// create new user
    			int userId = userUtil.createUser(user);
    			System.out.println("Created new User in DB - ID:" + userId);
    			
    			// reset input fields
    			userName.setValue("");
    			userPass.setValue("");
    			
    			userName.focus();
    				
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    

}
