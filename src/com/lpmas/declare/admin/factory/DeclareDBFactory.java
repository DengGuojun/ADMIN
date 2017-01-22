package com.lpmas.declare.admin.factory;

import java.sql.SQLException;

import com.lpmas.declare.admin.config.DeclareDBConfig;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.db.MysqlDBExecutor;
import com.lpmas.framework.db.MysqlDBObject;

public class DeclareDBFactory extends DBFactory {

	public DBObject getDBObjectR() throws SQLException {
		return new MysqlDBObject(DeclareDBConfig.DB_LINK_DECLARE_R);
	}

	public DBObject getDBObjectW() throws SQLException {
		return new MysqlDBObject(DeclareDBConfig.DB_LINK_DECLARE_W);
	}

	@Override
	public DBExecutor getDBExecutor() {
		return new MysqlDBExecutor();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}
}
