package kk.databasesManager.view;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kk.databasesManager.MainApp;
import kk.databasesManager.model.Database;
import kk.databasesManager.util.DBUtil;

public class MainLayoutController {
	@FXML
	private TableView<Database> databaseTable;
	@FXML
	private TableColumn<Database, String> databaseNameColumn;

	@FXML
	private Label databaseNameLabel;

	private MainApp mainApp;

	public MainLayoutController() {
	}

	@FXML
	private void initialize() {
		databaseNameColumn.setCellValueFactory(cellData -> cellData.getValue().datebaseNameProperty());

		showDatabaseOnLabel(null);

		databaseTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showDatabaseOnLabel(newValue));
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		databaseTable.setItems(mainApp.getDatabaseData());
		databaseTable.getSelectionModel().select(0);
	}

	public void showDatabaseOnLabel(Database database) {
		if (database != null) {
			databaseNameLabel.setText(database.getDatabaseName());
		} else {
			databaseNameLabel.setText("");
		}
	}

	@FXML
	private void handleDeleteDatabase() {
		int selectedIndex = databaseTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			String databaseName = databaseTable.getSelectionModel().getSelectedItem().getDatabaseName();
			DBUtil.terminateConnection(databaseName);
			try {
				DBUtil.dbExecuteUpdate("DROP DATABASE " + databaseName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			databaseTable.getItems().remove(selectedIndex);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Success");
			alert.setHeaderText("Database was successfully removed.");
			
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No database selected.");
			alert.setContentText("You need to select the database you want to delete.");
			
			alert.showAndWait();
		}
	}

	@FXML
	private void handleCopyDatabase() {
		Database database = databaseTable.getSelectionModel().getSelectedItem();
		if (database != null) {
			//TODO zmieniæ na fukcjê sprawdzanie która wersja
			String newDatabaseName = database.getDatabaseName() + "_v2";
			
			System.out.println(database.getDatabaseName());
			String databaseName = database.getDatabaseName();
			DBUtil.terminateConnection(databaseName);
			try {
				DBUtil.dbExecuteUpdate("CREATE DATABASE " + newDatabaseName + " WITH TEMPLATE " + databaseName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mainApp.getDatabaseData().add(new Database(newDatabaseName));
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No database selected.");
			alert.setContentText("You need to select the database you want to copy.");
			
			alert.showAndWait();
		}
	}
}
