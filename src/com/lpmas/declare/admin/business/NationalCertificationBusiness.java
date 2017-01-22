package com.lpmas.declare.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.declare.admin.dao.NationalCertificationDao;
import com.lpmas.declare.bean.NationalCertificationBean;
import com.lpmas.framework.config.Constants;

public class NationalCertificationBusiness {

	public List<NationalCertificationBean> getNationalCertificationAllList(){
		NationalCertificationDao dao = new NationalCertificationDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getNationalCertificationListByMap(condMap);
	}

}