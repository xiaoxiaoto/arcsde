package org.aoto.spatialdatahandle;

import com.esri.sde.sdk.client.SeConnection;
import com.esri.sde.sdk.client.SeException;

public class ArcSDEFactory {
	private String server;
	private String instance;
	private String database;
	private String username;
	private String password;

	public SeConnection create() {
		SeConnection conn = null;
		try {
			conn = new SeConnection(server, instance, database, username, password);
		} catch (SeException e) {
			e.printStackTrace();
		}
		return conn;

	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
