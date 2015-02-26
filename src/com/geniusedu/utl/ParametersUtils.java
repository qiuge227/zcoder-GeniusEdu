package com.geniusedu.utl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.geniusedu.activitys.R;

/**
 * 
 * 
 * �����ƣ�ParametersUtils �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-9 ����10:04:38 �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */
public class ParametersUtils {
	/**
	 * getRequestParams ��ȡ�������� (����������������������� �C ��ѡ)
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
			// ѧ�ŵ�½�ӿ�
			map.put("stu_number", param[0]);
			map.put("stu_password", param[1]);
			map.put("dataBaseName", param[2]);
			break;
		case R.string.interface_upload_loginbynameinfo:
			map.put("stu_name", param[0]);
			map.put("dataBaseName", param[2]);
			// ������½�ӿ�
			break;
		case R.string.interface_upload_password:
			// �޸�����ӿ�
			map.put("stu_number", param[0]);
			map.put("stu_password", param[1]);
			map.put("dataBaseName", param[2]);
			break;
		case R.string.interface_download_stuinfo:
			// ��ȡѧ����Ϣ�ӿ�
			map.put("stu_number", param[0]);
			map.put("dataBaseName", param[1]);
			break;
		case R.string.interface_download_guardianinfo:
			// ��ȡ�໤����Ϣ�ӿ�
			map.put("stu_number", param[0]);
			break;
		case R.string.interface_upload_guardianinfo:
			// �޸ļ໤����Ϣ�ӿ�
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
			// ��ȡѧУ
			map.put("city", param[0]);
			map.put("province", param[1]);
			break;
		case R.string.interface_download_class:
			// ��ȡ�༶
			map.put("dataBaseName", param[0]);
			break;
		case R.string.interface_upload_stuinfo:
			// ��ȡ�༶
			map.put("stu_number", param[0]);
			map.put("dataBaseName", param[1]);
			map.put("toDataBaseName", param[2]);
			map.put("stu_class", param[3]);
			break;
		case R.string.interface_upload_integral:
			// �޸Ļ��ֽӿ�
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
	 * urlJoint ƴ�ӻ�ȡ��������
	 * 
	 * @param url
	 *            ���Ӳ��䲿��
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
