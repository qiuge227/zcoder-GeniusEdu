package com.geniusedu.beans;

import com.geniusedu.basebeans.BaseEntry;
/**
 * 
 * 
 * �����ƣ�ScanintegralInfo
 * ��������
 * �����ˣ�¬���� 
 * �޸��ˣ�¬���� 
 * �޸�ʱ�䣺2014-9-17 ����12:55:53
 * �޸ı�ע��
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class ScanintegralInfo extends BaseEntry {
	private String scanintegral;// ��ά��ɨ���ȡ�Ļ���

	public String getScanintegral() {
		if (scanintegral == null) {
			scanintegral = "";
		}
		return scanintegral;
	}

	public void setScanintegral(String scanintegral) {
		if (scanintegral == null) {
			scanintegral = "";
		}
		this.scanintegral = scanintegral;
	}
}
