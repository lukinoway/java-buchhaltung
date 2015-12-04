package konto.view;

import java.time.LocalDate;

import com.vaadin.data.Item;
import com.vaadin.ui.Table;

import konto.model.Transaktion;

public class TransaktionsTable extends Table {
	
	TransaktionsContainer transaktionContainer = new TransaktionsContainer();
	
	public TransaktionsTable() {
		this.setContainerDataSource(transaktionContainer);
		
		Object test[] = {1 , "test", 20.0 , LocalDate.now(), "hashvalue"};
		
		Item newItem = transaktionContainer.getItem(transaktionContainer.addItem());
		
	}
	
	public void addEntry(Transaktion transaktion) {
		transaktionContainer.addItem("test");
	}
	
	public void removeEntry(Transaktion transaktion) {
		
	}

}
