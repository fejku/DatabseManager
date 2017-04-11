package kk.databasesManager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Database {
	private final StringProperty databaseName;
	
	public Database() {
		this(null);
	}
	
	public Database(String databaseName) {
		this.databaseName = new SimpleStringProperty(databaseName);
	}
	
	public String getDatabaseName() {
		return databaseName.get();
	}
	
	public void setDatabaseName(String databaseName) {
		this.databaseName.set(databaseName);
	}
	
	public StringProperty datebaseNameProperty() {
		return databaseName;
	}
}
