package com.lpmas.declare.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.declare.admin.dao.JobTypeDao;
import com.lpmas.declare.bean.JobTypeBean;
import com.lpmas.framework.config.Constants;

public class JobTypeBusiness {
	public List<JobTypeBean> getJobTypeAllList() {
		JobTypeDao dao = new JobTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getJobTypeListByMap(condMap);
	}

}