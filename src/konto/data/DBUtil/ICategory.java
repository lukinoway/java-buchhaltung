package konto.data.DBUtil;

import konto.data.container.CategoryContainer;
import konto.data.model.Category;

public interface ICategory {
	
	public void createCategory(Category category);
	
	public void updateCategory(Category category);
	
	public void deleteCategory(Category category);
	
	public CategoryContainer getAllCategories();

}
