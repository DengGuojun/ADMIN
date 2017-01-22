package com.lpmas.declare.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.declare.admin.dao.IndustryTypeDao;
import com.lpmas.declare.bean.IndustryTypeBean;
import com.lpmas.framework.config.Constants;

public class IndustryTypeBusiness {

	public List<IndustryTypeBean> getIndustryTypeAllList() {
		IndustryTypeDao dao = new IndustryTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getIndustryTypeListByMap(condMap);
	}

}