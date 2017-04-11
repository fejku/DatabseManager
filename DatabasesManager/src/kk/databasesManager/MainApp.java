package kk.databasesManager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kk.databasesManager.model.Database;
import kk.databasesManager.util.DBUtil;
import kk.databasesManager.view.MainLayoutController;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private ObservableList<Database> databaseDate = FXCollections.observableArrayList();
	
	public MainApp() {
		ResultSet rs;
		try {
			rs = DBUtil.dbExecuteQuery(
					"SELECT datname FROM pg_database WHERE datistemplate = false and datname <> 'postgres' ORDER BY 1");
			while (rs.next()) {
				databaseDate.add(new Database(rs.getString(1)));
				
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

//		databaseDate.add(new Database("qwe"));
//		databaseDate.add(new Database("asd"));
//		databaseDate.add(new Database("qwe"));
//		databaseDate.add(new Database("asd"));
//		databaseDate.add(new Database("qwe"));
//		databaseDate.add(new Database("asd"));
//		databaseDate.add(new Database("qwe"));
//		databaseDate.add(new Database("asd"));
//		databaseDate.add(new Database("qwe"));
//		databaseDate.add(new Database("asd"));
//		databaseDate.add(new Database("qwe"));
//		databaseDate.add(new Database("asd"));
//		databaseDate.add(new Database("qwe"));
//		databaseDate.add(new Database("asd"));
//		databaseDate.add(new Database("qwe"));
//		databaseDate.add(new Database("asd"));		
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Title");
		
		initRootLayout();
		
		showPersonOverview();
	}
	
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainLayout.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
            MainLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public ObservableList<Database> getDatabaseData() {
    	return databaseDate;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
