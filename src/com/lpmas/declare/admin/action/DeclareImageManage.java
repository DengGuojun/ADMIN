package com.lpmas.declare.admin.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.declare.admin.config.DeclareImageAdminConfig;
import com.lpmas.declare.config.DeclareImageInfoConfig;
import com.lpmas.framework.crypto.BASE64;
import com.lpmas.framework.util.FileKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.invoker.picture.bean.UploadInfoBean;
import com.lpmas.invoker.picture.bean.UploadResultBean;
import com.lpmas.invoker.picture.client.PictureUploadClient;
import com.lpmas.invoker.picture.client.impl.AliYunUploadClient;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;

/**
 * Servlet implementation class DeclareImageManage
 */
@WebServlet("/declare/admin/DeclareImageManage.do")
public class DeclareImageManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareImageManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ReturnMessageBean returnMessage = new ReturnMessageBean();
		String imgData = ParamKit.getParameter(request, "imgData", "");
		String imgType = ParamKit.getParameter(request, "imgType", "");
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();

		// 检验是否为空
		if (!StringKit.isValid(imgData) || !StringKit.isValid(imgType)) {
			returnMessage.setMessage("上传图片为空");
			HttpResponseKit.printJson(request, response, returnMessage, "");
			return;
		}

		// 获取文件后缀名
		String fileType = imgType.split("[/]")[1].toLowerCase();
		String subFixx = "." + fileType;
		// 判定文件格式
		if (!StringKit.isValid(fileType) || !DeclareImageAdminConfig.ALLOWED_FILE_TYPE_SET.contains(fileType)) {
			returnMessage.setMessage("图片类型错误，请上传JPG或者PNG图片");
			HttpResponseKit.printJson(request, response, returnMessage, "");
			return;
		}

		// 设置临时文件名
		String tempFileName = userId + "_" + DeclareImageInfoConfig.IMG_TYPE_ONE_INCH + "_"
				+ System.currentTimeMillis();
		String localFilePath = DeclareImageAdminConfig.TEMP_FILE_PATH + tempFileName + subFixx;
		// 保存临时图片
		String baseStr = imgData.split("base64,")[1];
		byte[] img = BASE64.decodeBase64(baseStr);
		// 验证图片大小
		if (img.length > DeclareImageAdminConfig.MAX_SIZE) {
			returnMessage.setMessage("上传图片大小超过限制");
			HttpResponseKit.printJson(request, response, returnMessage, "");
			return;
		}
		FileKit.bytes2File(img, localFilePath);
		UploadResultBean uploadResultBean = null;
		// 检验图片保存结果
		if (new File(localFilePath).exists()) {
			// 上传到阿里云
			PictureUploadClient client = new AliYunUploadClient();
			UploadInfoBean uploadInfoBean = new UploadInfoBean();
			uploadInfoBean.setLocalFile(localFilePath);
			uploadInfoBean.setRemoteFilePath(DeclareImageInfoConfig.IMG_TYPE_ONE_INCH); // 应用目录
			uploadResultBean = client.upload(uploadInfoBean);
		} else {
			returnMessage.setCode(HttpServletResponse.SC_BAD_REQUEST);
			returnMessage.setMessage("文件上传失败");
			HttpResponseKit.printJson(request, response, returnMessage, "");
			return;
		}

		// 判断上传是否成功
		if (uploadResultBean.isResult()) {
			// 成功
			returnMessage.setCode(HttpServletResponse.SC_OK);
			returnMessage.setMessage(uploadResultBean.getResultUrl());
		} else {
			// 不成功
			returnMessage.setCode(HttpServletResponse.SC_BAD_REQUEST);
			returnMessage.setMessage(uploadResultBean.getMessage());
		}

		if (DeclareImageAdminConfig.IS_DELETE_TEMP_FILE) {
			// 删除临时文件
			File tempFile = new File(localFilePath);
			if (tempFile.exists()) {
				tempFile.delete();
			}
		}

		// 返回结果
		HttpResponseKit.printJson(request, response, returnMessage, "");
	}

}
