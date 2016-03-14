package konto.ui;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import konto.data.DBUtil.IUser;
import konto.data.DBUtil.UserDBUtil;
import konto.data.model.LoginUser;
import konto.ui.session.SessionManager;
import konto.ui.view.User.NewUserWindow;

public class LoginForm extends Panel {

    private static final long serialVersionUID = 1L;
    VerticalLayout main = new VerticalLayout();
    HorizontalLayout buttonBar = new HorizontalLayout();
    TextField userName = new TextField("Benutzer");
    PasswordField userPass = new PasswordField("Password");
    Button loginBtn = new Button("Login");
    Button createUserBtn = new Button("new User");
    private boolean validLogin = false;

    IUser userUtil = new UserDBUtil();
    private LoginUser loginUser;

    public LoginForm() {

	this.setWidth(300, Unit.PIXELS);
	//this.setHeight(500, Unit.PIXELS);

	main.setWidth(100, Unit.PERCENTAGE);

	this.setCaption("Login to HomeAPP");

	main.addComponent(userName);
	userName.setWidth(100, Unit.PERCENTAGE);
	userName.addStyleName(ValoTheme.TEXTFIELD_SMALL);
	main.addComponent(userPass);
	userPass.setWidth(100, Unit.PERCENTAGE);
	userPass.addStyleName(ValoTheme.TEXTFIELD_SMALL);
	
	// build button bar	
	main.addComponent(buttonBar);
	buttonBar.setWidth(100, Unit.PERCENTAGE);
	
	buttonBar.addComponent(loginBtn);
	buttonBar.setComponentAlignment(loginBtn, Alignment.BOTTOM_LEFT);
	loginBtn.setClickShortcut(KeyCode.ENTER);
	loginBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);

	buttonBar.addComponent(createUserBtn);
	buttonBar.setComponentAlignment(createUserBtn, Alignment.BOTTOM_RIGHT);
	createUserBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);

	this.setContent(main);
	this.setStyleName("light");
	
	
	// action part
	loginBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		loginAttemp();

	    }

	});

	// add possibillity to add user in login mask
	createUserBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		NewUserWindow w = new NewUserWindow();
		UI.getCurrent().addWindow(w);
		w.focus();
	    }

	});
    }

    public boolean isValidLogin() {
	return validLogin;
    }

    private boolean validInput() {
	boolean valid = false;
	try {
	    if (userName == null || userName.getValue() == "") {
		userName.focus();
		throw new NullPointerException("kein BenutzerName");
	    }
	    if (userPass == null || userPass.getValue() == "") {
		userPass.focus();
		throw new NullPointerException("kein Passwort");
	    } else {
		valid = true;
	    }

	} catch (NullPointerException e) {
	    System.out.println("LoginForm: " + e);
	}

	return valid;
    }

    /**
     * try to Login
     */
    private void loginAttemp() {
	if (validInput()) {
	    if (!validLogin) {
		loginUser = new LoginUser(userName.getValue(), userPass.getValue());
		validLogin = userUtil.validateLogin(loginUser);
		if (validLogin) {
		    userUtil.loadUserId(loginUser);
		    SessionManager.setUser(loginUser);
		    ((MainApp) getUI()).buildMainView();
		} else {
		    Notification.show("Fehler beim Login - Bitte Eingabe Prüfen");
		    userName.focus();
		}
	    }
	}
    }

}
