package com.geniusedu.beans;

import com.geniusedu.basebeans.BaseEntry;
/**
 * 
 * 
 * �����ƣ�LoginByName
 * ��������
 * �����ˣ�¬���� 
 * �޸��ˣ�¬���� 
 * �޸�ʱ�䣺2014-9-17 ����12:55:33
 * �޸ı�ע��
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class LoginByName extends BaseEntry {
	private String stuclass;// �༶

	public String getStuclass() {
		if (stuclass == null) {
			stuclass = "";
		}
		return stuclass;
	}

	public void setStuclass(String stuclass) {
		if (stuclass == null) {
			stuclass = "";
		}
		this.stuclass = stuclass;
	}

}
