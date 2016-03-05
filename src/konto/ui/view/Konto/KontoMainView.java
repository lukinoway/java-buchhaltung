package konto.ui.view.Konto;

import org.vaadin.teemu.VaadinIcons;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

import konto.data.DBUtil.IKonto;
import konto.data.DBUtil.KontoDBUtil;
import konto.data.model.LoginUser;

public class KontoMainView extends VerticalLayout {
	
	private static final long serialVersionUID = 1L;
	KontoGrid grid;
	Button addKontoBtn = new Button();
	LoginUser user;
	IKonto kontoUtil = new KontoDBUtil();
	
	private KontoContainer container;

	public KontoMainView(LoginUser user) {
		
		this.user = user;
		
		container = kontoUtil.getKontoForUser(user);
		
		grid = new KontoGrid(container);
		this.addComponent(grid);
		
		addKontoBtn.setIcon(VaadinIcons.PLUS_CIRCLE);
		addKontoBtn.setStyleName("addButton");
		this.addComponent(addKontoBtn);
		this.setComponentAlignment(addKontoBtn, Alignment.BOTTOM_CENTER);
		
		addKontoBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				NewKontoWindow w = new NewKontoWindow(container, user);
				UI.getCurrent().addWindow(w);
				w.focus();
				
			}
			
		});
		
	}
	

}