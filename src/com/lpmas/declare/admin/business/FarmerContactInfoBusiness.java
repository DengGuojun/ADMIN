package com.lpmas.declare.admin.business;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.lpmas.declare.admin.dao.FarmerContactInfoDao;
import com.lpmas.declare.bean.FarmerContactInfoBean;
import com.lpmas.framework.annotation.FieldTag;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.NumberKit;
import com.lpmas.framework.util.ReflectKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;

public class FarmerContactInfoBusiness {
	public int addFarmerContactInfo(FarmerContactInfoBean bean) {
		FarmerContactInfoDao dao = new FarmerContactInfoDao();
		return dao.insertFarmerContactInfo(bean);
	}

	public int updateFarmerContactInfo(FarmerContactInfoBean bean) {
		FarmerContactInfoDao dao = new FarmerContactInfoDao();
		return dao.updateFarmerContactInfo(bean);
	}

	public FarmerContactInfoBean getFarmerContactInfoByKey(int declareId) {
		FarmerContactInfoDao dao = new FarmerContactInfoDao();
		return dao.getFarmerContactInfoByKey(declareId);
	}

	public PageResultBean<FarmerContactInfoBean> getFarmerContactInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		FarmerContactInfoDao dao = new FarmerContactInfoDao();
		return dao.getFarmerContactInfoPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyFarmerContactInfo(FarmerContactInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getUserMobile())) {
			result.setMessage("手机号码必须填写");
		} else if (!StringKit.isValid(bean.getProvince()) || !StringKit.isValid(bean.getCity())
				|| !StringKit.isValid(bean.getRegion())) {
			result.setMessage("户籍所在地必须填写");
		} else if (!NumberKit.isAllDigit(bean.getUserMobile())) {
			result.setMessage("手机号码必须填数字");
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