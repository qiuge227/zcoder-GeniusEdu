package com.geniusedu.utl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.geniusedu.activitys.R;

/**
 * 
 * 
 * 类名称：ParametersUtils 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-9 下午10:04:38 修改备注：
 * 
 * @version 1.0.0
 * 
 */
public class ParametersUtils {
	/**
	 * getRequestParams 获取请求数据 (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param urlResId
	 * @return HashMap<String,String>
	 * @exception
	 * @since 1.0.0
	 */
	public static HashMap<String, String> getRequestParams(int urlResId,
			String... param) {
		HashMap<String, String> map = new HashMap<String, String>();
		switch (urlResId) {
		case R.string.interface_upload_logininfo:
			// 学号登陆接口
			map.put("stu_number", param[0]);
			map.put("stu_password", param[1]);
			map.put("dataBaseName", param[2]);
			break;
		case R.string.interface_upload_loginbynameinfo:
			map.put("stu_name", param[0]);
			map.put("dataBaseName", param[2]);
			// 姓名登陆接口
			break;
		case R.string.interface_upload_password:
			// 修改密码接口
			map.put("stu_number", param[0]);
			map.put("stu_password", param[1]);
			map.put("dataBaseName", param[2]);
			break;
		case R.string.interface_download_stuinfo:
			// 获取学生信息接口
			map.put("stu_number", param[0]);
			map.put("dataBaseName", param[1]);
			break;
		case R.string.interface_download_guardianinfo:
			// 获取监护人信息接口
			map.put("stu_number", param[0]);
			break;
		case R.string.interface_upload_guardianinfo:
			// 修改监护人信息接口
			map.put("father_name", param[0]);
			map.put("mother_name", param[1]);
			map.put("mother_phone", param[2]);
			map.put("father_phone", param[3]);
			map.put("address", param[4]);
			map.put("stu_number", param[5]);
			map.put("schoolName", param[6]);
			map.put("stu_name", param[7]);
			map.put("dataBaseName", param[8]);
			break;
		case R.string.interface_download_school:
			// 获取学校
			map.put("city", param[0]);
			map.put("province", param[1]);
			break;
		case R.string.interface_download_class:
			// 获取班级
			map.put("dataBaseName", param[0]);
			break;
		case R.string.interface_upload_stuinfo:
			// 获取班级
			map.put("stu_number", param[0]);
			map.put("dataBaseName", param[1]);
			map.put("toDataBaseName", param[2]);
			map.put("stu_class", param[3]);
			break;
		case R.string.interface_upload_integral:
			// 修改积分接口
			map.put("stu_number", param[0]);
			map.put("points", param[1]);
			map.put("id", param[2]);
			map.put("dataBaseName", param[3]);
			break;
		case R.string.interface_upload_picture:
			map.put("stu_number", param[0]);
			map.put("photo", param[1]);
			break;
		}
		return map;
	}

	/**
	 * 
	 * urlJoint 拼接获取请求连接
	 * 
	 * @param url
	 *            连接不变部分
	 * @param map
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public static String urlJoint(String url, Map<String, String> map) {
		StringBuffer strBuffer = new StringBuffer(url);
		Set<Entry<String, String>> entrySet = map.entrySet();
		int i = 0;
		for (Entry<String, String> entry : entrySet) {
			if (i == 0 && !url.contains("?")) {
				strBuffer.append("?");
			} else {
				strBuffer.append("&");
			}
			strBuffer.append(entry.getKey());
			strBuffer.append("=");
			strBuffer.append(entry.getValue());
			i++;
		}
		System.out.println(strBuffer.toString());
		return strBuffer.toString();
	}
}
