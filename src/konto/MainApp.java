package konto;

import konto.model.Konto;
import konto.model.Transaktion;
import konto.model.TransaktionDetail;
import konto.view.RootLayoutController;
import konto.view.TransaktionDetailOverviewController;
import konto.view.TransaktionDetailUtilController;
import konto.view.TransaktionOverviewController;
import konto.Util.DB_Util;
import konto.Util.ParseCSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Transaktion> transaktionData = FXCollections.observableArrayList();
	// Initialize Konto
	Konto kn = new Konto("1","temp");

    // Constructor
    public MainApp() {
    	// added test data
    	try {
			transaktionData.add(new Transaktion(LocalDate.of(2015, 2, 21), 20.0 , "test"));
	    	transaktionData.add(new Transaktion(LocalDate.of(2015, 3, 21), -300.0 , "test2"));
	    	transaktionData.add(new Transaktion(LocalDate.of(2015, 4, 21), 420.0 , "test3"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Returns the data as an observable list of Persons.
     * @return
     */
    public ObservableList<Transaktion> getTransaktionData() {
        return transaktionData;
    }

	@Override
	public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("KontoApp");
        //this.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("D:\\Programmieren\\java-buchhaltung\\img\\main_icon.png")));

        initRootLayout();
        showTransaktionOverview();
        showDetailOverview();

	}

    /**
     * Initializes the root layout.
     */
	public void initRootLayout() {
	    try {
	        // Load root layout from fxml file.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
	        rootLayout = (BorderPane) loader.load();

	        // Show the scene containing the root layout.
	        Scene scene = new Scene(rootLayout);
	        primaryStage.setScene(scene);

	        // Give the controller access to the main app.
	        RootLayoutController controller = loader.getController();
	        controller.setMainApp(this);

	        primaryStage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    /**
     *  Shows Transaktion Overview inside the root layout
     */
    public void showTransaktionOverview() {
    	try {
    		// load transaktion overview
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainApp.class.getResource("view/TransaktionOverview.fxml"));
    		AnchorPane transaktionOverview = (AnchorPane) loader.load();

    		// set TransaktionOverview in the center of root
    		//rootLayout.setCenter(transaktionOverview);
    		rootLayout.setLeft(transaktionOverview);

    		// give the controller acces to the main app
    		TransaktionOverviewController controller = loader.getController();
    		controller.setMainApp(this);

    	} catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * With this function we will read data from csv and insert to DB
     * @param file
     * @throws Exception
     */
    public void loadDataFromFile (File file) throws Exception {
    	try {
			new ParseCSV(file.toString(), kn);
			DB_Util con = new DB_Util();
			con.writeDataBase(kn);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
	    	Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data");
	        alert.setContentText("Could not load data from file:\n" + file.getPath());

	        alert.showAndWait();
		}
    }

    /**
     * shows DetailOverview
     */
	public void showDetailOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TransaktionDetailOverview.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // load to the right site of rootLayout
            rootLayout.setRight(page);

            TransaktionDetailOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

	/**
	 * load details for selected transaction
	 * @param kn
	 */
	public void loadDetailForSelectedTransaktion(Transaktion selectedTransaktion) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/TransaktionDetailOverview.fxml"));
        TransaktionDetailOverviewController controller = loader.getController();
        controller.setMainApp(this);
        controller.setTransaktion(selectedTransaktion);
        controller.setTransaktionDetail(selectedTransaktion);

	}

	/**
	 * open frame for transaktionDetail creation
	 * @param selectedTransaktion
	 * @return
	 */
	public boolean showCreateDetailDialog(Transaktion selectedTransaktion) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TransaktionDetailUtil.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("neues Detail");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the detail into the controller.
            TransaktionDetailUtilController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.loadTransaktion(selectedTransaktion);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}

}
