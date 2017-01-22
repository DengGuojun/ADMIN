package com.lpmas.declare.admin.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.bean.StatusBean;

public class DeclareLevelConfig {

	public static final String COUNTRY = "国家";

	public static final String LEVEL_COUNTYY = "COUNTRY";
	public static final String LEVEL_CITY = "CITY";
	public static final String LEVEL_PROVICE = "PROVICE";
	public static final String LEVEL_REGION = "REGION";
	// 区域等级
	public static List<StatusBean<String, String>> LEVEL_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> LEVEL_MAP = new HashMap<String, String>();

	static {
		// initLevelList();
		// initLevelMap();
	}

	// private static void initLevelList() {
	// LEVEL_LIST = new ArrayList<StatusBean<String, String>>();
	// LEVEL_LIST.add(new StatusBean<String, String>(LEVEL_COUNTYY, "国家"));
	// LEVEL_LIST.add(new StatusBean<String, String>(LEVEL_PROVICE, "省"));
	// LEVEL_LIST.add(new StatusBean<String, String>(LEVEL_CITY, "市"));
	// LEVEL_LIST.add(new StatusBean<String, String>(LEVEL_REGION, "区"));
	// }

	// private static void initLevelMap() {
	// LEVEL_MAP = StatusKit.toMap(LEVEL_LIST);
	// }
}
