package konto.view;


import konto.MainApp;
import konto.model.Konto;
import konto.model.Rechnung;
import konto.model.Transaktion;
import konto.DBUtil.RechnungsDBUtil;
import konto.DBUtil.TransaktionDBUtil;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

public class TransaktionOverviewController {

	// Ref. für GUI
	@FXML
	private DatePicker startdate;
	@FXML
	private DatePicker enddate;
	@FXML
	private ComboBox<String> typebox;
	
	// ref for transaktion table
    @FXML
    public TableView<Transaktion> TransaktionsTable;
    @FXML
    public TableColumn<Transaktion, LocalDate> trDateColumn;
    @FXML
    public TableColumn<Transaktion, Number> trBetragColumn;
    @FXML
    public TableColumn<Transaktion, String> trTextColumn;
    @FXML
    public TableColumn<Transaktion, Number> trIDColumn;

    // ref for bill pool table
    @FXML
    public TableView<Rechnung> RechnungsPoolTable;
    @FXML
    public TableColumn<Rechnung, Number> rechnungsId;
    @FXML
    public TableColumn<Rechnung, LocalDate> rechnungsDatum;
    @FXML
    public TableColumn<Rechnung, String> rechnungsText;
    
    // Reference to the main application.
    private MainApp mainApp;

    
    /**
     * Constructor, allways set it to public!!
     */
    public TransaktionOverviewController() {
    }

    /**
     * Initialisiere Controller nachdem das FXML geladen wurde
     */
    @FXML
    private void initialize() {
    	// load RechnungsPool
    	loadRechnungsPool();
    }

    /**
     * Called when the user clicks the show detail button.
     * details for the selected transaktion.
     */
    @FXML
    private void handleShowDetail() {
        Transaktion selectedTransaktion = TransaktionsTable.getSelectionModel().getSelectedItem();
        if (selectedTransaktion != null) {
        	try {
        		mainApp.loadDetailForSelectedTransaktion(selectedTransaktion);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("Keine Transaktion ausgewählt");
            alert.setContentText("Bitte Transaktion ausgewählt.");

            alert.showAndWait();
        }
    }
    
    /**
     * Main Function to get Data from DB
     */
    @FXML
    private void loadTransaktionFromDB() {
    	try {
    		TransaktionDBUtil util = new TransaktionDBUtil();
    		util.setController(this);
    		// now perform some checking on selected values
    		if (this.startdate.getValue()!=null && this.enddate.getValue()!=null) {
    			if (typebox.getValue()!=null) {
    				util.getTransaktionFromDB(this.startdate.getValue(), this.enddate.getValue(), typebox.getValue()); 				
    			} 
    			else {
    				util.getTransaktionFromDB(this.startdate.getValue(), this.enddate.getValue());
    			}
    		} 
    		else if (typebox.getValue()!=null) {
    			util.getTransaktionFromDB(typebox.getValue());
    		}
    		else {
                // wrong input
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Fehlerhafte Eingabe");
                alert.setHeaderText("Bitte Eingabe prüfen");
                alert.setContentText("Datum vorhanden? Type ausgewählt?");

                alert.showAndWait();
    		}
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * This Function will add a new bill to the Pool
     */
    @FXML
    public void addBillToPool() {
    	String billText = "unbekannt";
    	RechnungsDBUtil util = new RechnungsDBUtil();
    	FileChooser fileChooser = new FileChooser();
    	
    	//Show file dialog
    	File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
    	
    	// add text to bill
    	TextInputDialog dialog = new TextInputDialog("RechnungsText");
    	dialog.setTitle("RechnungsText");
    	dialog.setHeaderText("Bitte eine Beschreibung zur ausgewählen Rechnung eingeben");
    	dialog.setContentText("Text:");
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()) {
    		billText = result.get();
    	}
    	
    	if (file != null) {
    		util.attachBilltoPool(file, billText);
    		loadRechnungsPool();
    	}
    }

    
    /**
     * Main Function to get Bill Pool
     */
    public void loadRechnungsPool() {
    	try {
    		RechnungsDBUtil util = new RechnungsDBUtil();
    		util.setController(this);
    		util.loadRechnungsPool();
    		
    	} catch (Exception e) {
    		System.out.println("Hier lief was schief - loadRechnungsPool");
    		e.printStackTrace();
    	}
    }
    
    @FXML
    private void downloadFromPool() {
    	try {
    		System.out.println("start with download : " + LocalTime.now());
    		RechnungsDBUtil util = new RechnungsDBUtil();
    		Rechnung selectedRechnung = RechnungsPoolTable.getSelectionModel().getSelectedItem();
    	
    		if(selectedRechnung != null) {
    		
    			String filepath = util.downloadBillFromPool(selectedRechnung.getRechnungsId());
    			File file = new File (filepath);
    			
    			System.out.println("finished download : " + LocalTime.now());
    			// open file with standard program
    			Desktop.getDesktop().open(file);
    		}
    	} catch (Exception e) {
    		System.out.println("Hier lief was schief - downloadFromPool");
    		e.printStackTrace();
    	}
    }
    
    @FXML
    private void deleteFromPool() {
    	try {
    		RechnungsDBUtil util = new RechnungsDBUtil();
    		Rechnung selectedRechnung = RechnungsPoolTable.getSelectionModel().getSelectedItem();
    	
    		if(selectedRechnung != null) {
    			util.deleteBillFromPool(selectedRechnung.getRechnungsId());
    			
    			// reload POOL
    			loadRechnungsPool();
    		}
    		
    	} catch(Exception e) {
    		System.out.println("Hier lief was schief - deleteFromPool");
    		e.printStackTrace();
    	}
    }
    
    @FXML void linkBillToTransaktion() {
    	try {
    		RechnungsDBUtil util = new RechnungsDBUtil();
    		Rechnung selectedRechnung = RechnungsPoolTable.getSelectionModel().getSelectedItem();
    		Transaktion selectedTransaktion = TransaktionsTable.getSelectionModel().getSelectedItem();
    		
    		if (selectedRechnung != null) {
    			if (selectedTransaktion != null) {
    				util.linkBillToTransaktion(selectedTransaktion.getTransaktions_id(), selectedRechnung.getRechnungsId());
    			} else {
        			// show warning
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("Daten fehlerhaft");
                    alert.setHeaderText("Keine Transaktion gewählt");
                    alert.setContentText("Bitte wählen Sie eine Transaktion aus!");
                    
                    alert.showAndWait();
    			}
    		} else {
    			// show warning
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Daten fehlerhaft");
                alert.setHeaderText("Keine Rechnung gewählt");
                alert.setContentText("Bitte wählen Sie eine Rechnung aus dem Pool aus!");
                
                alert.showAndWait();
    		}
    		// reload pool
    		loadRechnungsPool();
    		handleShowDetail();
    		
    	} catch (Exception e) {
    		System.out.println("Hier lief was schief - linkBillToTransaktion");
    		e.printStackTrace();
    	}
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        TransaktionsTable.setItems(mainApp.getTransaktionData());
    }
}
