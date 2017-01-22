package com.lpmas.admin.factory;

import java.sql.SQLException;

import com.lpmas.admin.config.AdminDBConfig;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.db.MysqlDBExecutor;
import com.lpmas.framework.db.MysqlDBObject;

public class AdminDBFactory extends DBFactory {

	public DBObject getDBObjectR() throws SQLException {
		return new MysqlDBObject(AdminDBConfig.DB_LINK_ADMIN_R);
	}

	public DBObject getDBObjectW() throws SQLException {
		return new MysqlDBObject(AdminDBConfig.DB_LINK_ADMIN_W);
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
