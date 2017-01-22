package com.lpmas.declare.admin.business;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.lpmas.declare.admin.dao.FarmerSkillInfoDao;
import com.lpmas.declare.bean.FarmerSkillInfoBean;
import com.lpmas.framework.annotation.FieldTag;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.ReflectKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;

public class FarmerSkillInfoBusiness {
	public int addFarmerSkillInfo(FarmerSkillInfoBean bean) {
		FarmerSkillInfoDao dao = new FarmerSkillInfoDao();
		return dao.insertFarmerSkillInfo(bean);
	}

	public int updateFarmerSkillInfo(FarmerSkillInfoBean bean) {
		FarmerSkillInfoDao dao = new FarmerSkillInfoDao();
		return dao.updateFarmerSkillInfo(bean);
	}

	public FarmerSkillInfoBean getFarmerSkillInfoByKey(int declareId) {
		FarmerSkillInfoDao dao = new FarmerSkillInfoDao();
		return dao.getFarmerSkillInfoByKey(declareId);
	}

	public PageResultBean<FarmerSkillInfoBean> getFarmerSkillInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		FarmerSkillInfoDao dao = new FarmerSkillInfoDao();
		return dao.getFarmerSkillInfoPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyFarmerSkillInfo(FarmerSkillInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getApplyType())) {
			result.setMessage("申报方式必须填写");
		} else if (bean.getIsTrained() == Constants.STATUS_VALID) {
			if (bean.getOtherTrainingTime() == 0) {
				result.setMessage("其他培训次数/年必须填写且不能为0");
			}
		} else if (bean.getHasNewTypeCertification() == Constants.STATUS_VALID) {
			if (!StringKit.isValid(bean.getCertificationGrade())) {
				result.setMessage("认定等级必须填写");
			} else if (bean.getCertificationDate() == null) {
				result.setMessage("认定时间必须填写");
			} else if (!StringKit.isValid(bean.getCertificationDepartment())) {
				result.setMessage("认定部门");
			}
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