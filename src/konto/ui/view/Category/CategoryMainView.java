package konto.ui.view.Category;

import org.vaadin.teemu.VaadinIcons;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.VerticalLayout;

import konto.data.DBUtil.CategoryDBUtil;
import konto.data.DBUtil.ICategory;
import konto.data.container.CategoryContainer;
import konto.ui.session.SessionManager;

public class CategoryMainView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    CategoryGrid grid;
    Button addCategoryBtn = new Button();

    ICategory categoryUtil = new CategoryDBUtil();

    private CategoryContainer container;

    public CategoryMainView() {

	// fill container and store in session
	container = categoryUtil.getAllCategories();
	SessionManager.setCategoryContainer(container);

	grid = new CategoryGrid(container);
	this.addComponent(grid);
	calcGridHeight();

	addCategoryBtn.setIcon(VaadinIcons.PLUS_CIRCLE);
	addCategoryBtn.setStyleName("addButton");
	addCategoryBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	addCategoryBtn.setClickShortcut(KeyCode.A);
	this.addComponent(addCategoryBtn);
	this.setComponentAlignment(addCategoryBtn, Alignment.BOTTOM_CENTER);

	addCategoryBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		CategoryWindow w = new CategoryWindow();
		UI.getCurrent().addWindow(w);
		w.focus();

	    }

	});
    }
    
    public void calcGridHeight() {
	grid.setHeight(UI.getCurrent().getPage().getBrowserWindowHeight()-100, Unit.PIXELS);
    }

}
