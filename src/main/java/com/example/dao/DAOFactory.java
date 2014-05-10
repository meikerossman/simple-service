package com.example.dao;

import java.util.Properties;

public class DAOFactory {

	private static BaseDAO baseDAO = null;

	static {
		Properties properties = new Properties();

		try {
			properties.load(DAOFactory.class
					.getResourceAsStream("/dao.properties"));
			String className = properties.get("class").toString();
			baseDAO = (BaseDAO) Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static BaseDAO getDAO() {
		return baseDAO;
	}

}
