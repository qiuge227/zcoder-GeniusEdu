package com.geniusedu.beans;

import com.geniusedu.basebeans.BaseEntry;
/**
 * 
 * 
 * �����ƣ�GuardianInfor
 * ��������
 * �����ˣ�¬���� 
 * �޸��ˣ�¬���� 
 * �޸�ʱ�䣺2014-9-17 ����12:55:28
 * �޸ı�ע��
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class GuardianInfor extends BaseEntry {
	private String dadname;// �ְ�����
	private String dadphone;// �ְֵ绰
	private String flyaddress;// ��ͥסַ
	private String momname;// ��������
	private String momphone;// ����绰
	private String pictureurl;// �ҳ�ͼ��

	public String getPictureurl() {
		return pictureurl;
	}

	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
	}

	public String getDadname() {
		return dadname;
	}

	public void setDadname(String dadname) {
		this.dadname = dadname;
	}

	public String getDadphone() {
		return dadphone;
	}

	public void setDadphone(String dadphone) {
		this.dadphone = dadphone;
	}

	public String getFlyaddress() {
		return flyaddress;
	}

	public void setFlyaddress(String flyaddress) {
		this.flyaddress = flyaddress;
	}

	public String getMomname() {
		return momname;
	}

	public void setMomname(String momname) {
		this.momname = momname;
	}

	public String getMomphone() {
		return momphone;
	}

	public void setMomphone(String momphone) {
		this.momphone = momphone;
	}

}
