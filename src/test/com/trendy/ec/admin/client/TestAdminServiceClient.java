package test.com.trendy.ec.admin.client;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.lpmas.admin.bean.AdminGroupInfoBean;
import com.lpmas.admin.bean.AdminUserInfoBean;
import com.lpmas.admin.client.AdminServiceClient;
import com.lpmas.framework.util.JsonKit;

public class TestAdminServiceClient {

	@Test
	public void testGetAdminUserInfoByKey() {
		AdminServiceClient client = new AdminServiceClient();
		AdminUserInfoBean bean = client.getAdminUserInfoByKey(2);
		System.out.println(bean.getAdminUserName());
	}

	@Test
	public void testGetAdminGroupValidSetByUserId() {
		AdminServiceClient client = new AdminServiceClient();
		HashSet<Integer> set = client.getAdminGroupValidSetByUserId(2);
		System.out.println(set);
	}

	@Test
	public void testGetAdminPrivilegeCodeSetByUserId() {
		AdminServiceClient client = new AdminServiceClient();
		HashSet<String> set = client.getAdminPrivilegeCodeSetByUserId(2);
		System.out.println(set);
	}

	// @Test
	public void testGetAdminGroupInfoValidList() {
		AdminServiceClient client = new AdminServiceClient();
		List<AdminGroupInfoBean> list = client.getAdminGroupInfoValidList();
		System.out.println(JsonKit.toJson(list));
	}

	// @Test
	public void testGetAdminGroupInfoByKey() {
		long startTime = new Date().getTime();
		int count = 0;
		for (int i = 0; i < 2000; i++) {
			try {
				AdminServiceClient client = new AdminServiceClient();
				AdminGroupInfoBean bean = client.getAdminGroupInfoByKey(1);
				// System.out.println(bean.getGroupName());
			} catch (Exception e) {
				count++;
				System.out.println(i);
				e.printStackTrace();
			}
		}
		long endTime = new Date().getTime();
		System.out.println(endTime - startTime);
		System.out.println(count);
	}

}
