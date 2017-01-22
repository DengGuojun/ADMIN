package com.lpmas.declare.admin.business;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.lpmas.declare.admin.dao.FarmerInfoDao;
import com.lpmas.declare.bean.FarmerInfoBean;
import com.lpmas.framework.annotation.FieldTag;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.ReflectKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;

public class FarmerInfoBusiness {
	public int addFarmerInfo(FarmerInfoBean bean) {
		FarmerInfoDao dao = new FarmerInfoDao();
		return dao.insertFarmerInfo(bean);
	}

	public int updateFarmerInfo(FarmerInfoBean bean) {
		FarmerInfoDao dao = new FarmerInfoDao();
		return dao.updateFarmerInfo(bean);
	}

	public FarmerInfoBean getFarmerInfoByKey(int declareId) {
		FarmerInfoDao dao = new FarmerInfoDao();
		return dao.getFarmerInfoByKey(declareId);
	}

	public PageResultBean<FarmerInfoBean> getFarmerInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		FarmerInfoDao dao = new FarmerInfoDao();
		return dao.getFarmerInfoPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyFarmerInfoBean(FarmerInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getUserName())) {
			result.setMessage("姓名必须填写");
		} else if (bean.getUserGender() == 0) {
			result.setMessage("性别必须填写");
		} else if (!StringKit.isValid(bean.getNationality())) {
			result.setMessage("民族必须填写");
		} else if (!StringKit.isValid(bean.getEducation())) {
			result.setMessage("文化程度必须填写");
		} else if (!StringKit.isValid(bean.getIdentityNumber())) {
			result.setMessage("身份证号必须填写");
		} else if (!StringKit.isValid(bean.getPoliticalStatus())) {
			result.setMessage("政治面貌必须填写");
		} else if (bean.getUserBirthday() == null) {
			result.setMessage("出生日期必须填写");
		}

		// 对所有数值类型作非负判断
		for (Field field : BeanKit.getDeclaredFieldList(bean)) {
			Object value = ReflectKit.getPropertyValue(bean, field.getName());
			if (value != null) {
				if (value instanceof Integer) {
					if (((Integer) value) < 0)
						result.setMessage(field.getAnnotation(FieldTag.class).name() + "不能小于0");
				} else if (value instanceof Double) {
					if (((Double) value) < 0)
						result.setMessage(field.getAnnotation(FieldTag.class).name() + "不能小于0");
				}
			}
		}
		return result;
	}

}