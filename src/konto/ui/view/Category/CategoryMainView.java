package konto.ui.view.Category;

import java.util.ArrayList;

import org.vaadin.teemu.VaadinIcons;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

import konto.data.DBUtil.CategoryDBUtil;
import konto.data.DBUtil.ICategory;
import konto.data.model.Category;

public class CategoryMainView extends VerticalLayout {
	
	private static final long serialVersionUID = 1L;
	CategoryGrid grid;
	Button addCategoryBtn = new Button();
	
	ICategory categoryUtil = new CategoryDBUtil();
	
	private CategoryContainer container;

	public CategoryMainView() {
		
		//createTestData();
		container = categoryUtil.getAllCategories();
		
		grid = new CategoryGrid(container);
		this.addComponent(grid);
		
		addCategoryBtn.setIcon(VaadinIcons.PLUS_CIRCLE);
		addCategoryBtn.setStyleName("addButton");
		this.addComponent(addCategoryBtn);
		this.setComponentAlignment(addCategoryBtn, Alignment.BOTTOM_CENTER);
		
		addCategoryBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				NewCategoryWindow w = new NewCategoryWindow(container);
				UI.getCurrent().addWindow(w);
				w.focus();
				
			}
			
		});
	}
	
	@SuppressWarnings("unused")
	private void createTestData() {
		try {
			ArrayList<Category> collector = new ArrayList<Category>();
			
			collector.add(new Category("test1"));
			collector.add(new Category("test2"));
			collector.add(new Category("test3"));
			
			container = new CategoryContainer(collector);
			
		} catch (Exception e) {
			
		}
	}

}
