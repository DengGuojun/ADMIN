package com.lpmas.declare.admin.business;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.lpmas.declare.admin.config.DeclareImageAdminConfig;
import com.lpmas.declare.admin.dao.DeclareImageDao;
import com.lpmas.declare.bean.DeclareImageBean;
import com.lpmas.declare.config.DeclareImageInfoConfig;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.transfer.FileUploadKit;
import com.lpmas.framework.transfer.FileUploadResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;

public class DeclareImageBusiness {
	public int addDeclareImage(DeclareImageBean bean) {
		DeclareImageDao dao = new DeclareImageDao();
		return dao.insertDeclareImage(bean);
	}

	public int updateDeclareImage(DeclareImageBean bean) {
		DeclareImageDao dao = new DeclareImageDao();
		return dao.updateDeclareImage(bean);
	}

	public DeclareImageBean getDeclareImageByKey(int declareId, String imageType) {
		DeclareImageDao dao = new DeclareImageDao();
		return dao.getDeclareImageByKey(declareId, imageType);
	}

	public FileUploadResultBean saveFile(HttpServletRequest request, String uploadPath, String fileName) {
		FileUploadKit fileUploadKit = new FileUploadKit();
		fileUploadKit.setAllowedFileType(DeclareImageAdminConfig.ALLOWED_FILE_TYPE);// 设置允许上传的文件类型
		fileUploadKit.setMaxSize(5 * DeclareImageAdminConfig.MAX_SIZE);
		return fileUploadKit.fileUpload(request, "file", uploadPath, fileName);
	}

	public PageResultBean<DeclareImageBean> getDeclareImagePageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		DeclareImageDao dao = new DeclareImageDao();
		return dao.getDeclareImagePageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyDeclareImageBean(DeclareImageBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getImageType())
				|| !bean.getImageType().trim().equals(DeclareImageInfoConfig.IMG_TYPE_ONE_INCH)) {
			result.setMessage("图片类型错误");
		} else if (!StringKit.isValid(bean.getImagePath())) {
			result.setMessage("图片路径必须填写");
		}
		return result;
	}

}