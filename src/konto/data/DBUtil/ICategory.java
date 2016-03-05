package konto.data.DBUtil;

import konto.data.model.Category;
import konto.ui.view.Category.CategoryContainer;

public interface ICategory {
	
	public void createCategory(Category category);
	
	public void deleteCategory(Category category);
	
	public CategoryContainer getAllCategories();

}
