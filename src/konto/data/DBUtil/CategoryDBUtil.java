package konto.data.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import konto.data.model.Category;
import konto.ui.view.Category.CategoryContainer;

public class CategoryDBUtil extends DBCommunicator implements ICategory {

    private static final long serialVersionUID = 1L;
    private ResultSet resSet = null;
    private PreparedStatement pStmt = null;

    public CategoryDBUtil() {
	super();
    }

    @Override
    public void createCategory(Category category) {
	try {
	    String pSql = "insert into db_transaktion_type(type_text) values(?)";
	    pStmt = connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);
	    pStmt.setString(1, category.getTypeText());
	    pStmt.executeUpdate();

	    resSet = pStmt.getGeneratedKeys();
	    resSet.next();

	    category.setTypeId(resSet.getInt(1));
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}

    }

    @Override
    public void deleteCategory(Category category) {
	try {
	    String pSql = "delete from db_transaktion_type where type_id = ?";
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, category.getTypeId());
	    pStmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}

    }

    @Override
    public CategoryContainer getAllCategories() {
	CategoryContainer data = null;
	try {
	    String pSql = "select type_id, type_text from db_transaktion_type";
	    pStmt = connect.prepareStatement(pSql);
	    resSet = pStmt.executeQuery();

	    ArrayList<Category> categoryList = new ArrayList<Category>();
	    while (resSet.next()) {
		categoryList.add(new Category(resSet.getInt(1), resSet.getString(2)));
	    }
	    data = new CategoryContainer(categoryList);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}

	return data;
    }

    // Close everything
    public void close() {
	try {
	    if (resSet != null) {
		resSet.close();
	    }
	    if (pStmt != null) {
		pStmt.close();
	    }
	    super.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
