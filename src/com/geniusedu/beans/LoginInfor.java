package com.geniusedu.beans;

import com.geniusedu.basebeans.BaseEntry;
/**
 * 
 * 
 * �����ƣ�LoginInfor
 * ��������
 * �����ˣ�¬���� 
 * �޸��ˣ�¬���� 
 * �޸�ʱ�䣺2014-9-17 ����12:55:40
 * �޸ı�ע��
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class LoginInfor extends BaseEntry {
	private String stupassword;// ѧ����½����

	public String getStupassword() {
		if (stupassword == null) {
			stupassword = "";
		}
		return stupassword;
	}

	public void setStupassword(String stupassword) {
		if (stupassword == null) {
			stupassword = "";
		}
		this.stupassword = stupassword;
	}
}
