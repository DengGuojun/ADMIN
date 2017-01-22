package com.lpmas.declare.admin.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.declare.config.DeclareInfoConfig;
import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class DeclareInfoRecommendConfig {
	// 提交动作
	public static final String COMMIT_ACTION_SUBMIT = "SUBMIT"; // 提交
	public static final String COMMIT_ACTION_CITY_APPROVE = "CITY_APPROVE"; // 县级通过
	public static final String COMMIT_ACTION_APPROVE = "APPROVE"; // 通过
	public static final String COMMIT_ACTION_NOT_APPROVE = "NOT_APPROVE"; // 不通过
	public static final String COMMIT_ACTION_REJECT = "REJECT"; // 驳回
	public static final String COMMIT_ACTION_DELETE = "DELETE"; // 删除
	public static final String COMMIT_ACTION_CHANGE = "CHANGE"; // 转换类型
	// 页面类型
	public static final Integer TYPE_VERIFY = 1; // 对象审核
	public static final Integer TYPE_MANAGE = 2; // 对象管理
	public static final Integer TYPE_CLASSIFY = 3; // 对象类别
	public static List<StatusBean<Integer, String>> MODEL_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static HashMap<Integer, String> MODEL_TYPE_MAP = new HashMap<Integer, String>();
	// 审核状态
	public static List<StatusBean<String, String>> REVIEW_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> REVIEW_STATUS_MAP = new HashMap<String, String>();

	static {
		initModelTypeList();
		initModelTypeMap();

		initReviewStatusList();
		initReviewStatusMap();
	}

	private static void initModelTypeList() {
		MODEL_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
		MODEL_TYPE_LIST.add(new StatusBean<Integer, String>(TYPE_VERIFY, "对象审核"));
		MODEL_TYPE_LIST.add(new StatusBean<Integer, String>(TYPE_MANAGE, "对象管理"));
		MODEL_TYPE_LIST.add(new StatusBean<Integer, String>(TYPE_CLASSIFY, "对象类别"));

	}

	private static void initModelTypeMap() {
		MODEL_TYPE_MAP = StatusKit.toMap(MODEL_TYPE_LIST);

	}

	private static void initReviewStatusList() {
		REVIEW_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
		REVIEW_STATUS_LIST.add(new StatusBean<String, String>(DeclareInfoConfig.DECLARE_STATUS_SUBMIT, "待审核"));
		REVIEW_STATUS_LIST.add(new StatusBean<String, String>(DeclareInfoConfig.DECLARE_STATUS_CITY_APPROVE, "县级通过"));
		REVIEW_STATUS_LIST.add(new StatusBean<String, String>(DeclareInfoConfig.DECLARE_STATUS_APPROVE, "通过"));

	}

	private static void initReviewStatusMap() {
		REVIEW_STATUS_MAP = StatusKit.toMap(REVIEW_STATUS_LIST);

	}

}
