package com.geniusedu.beans;

import com.geniusedu.basebeans.BaseEntry;
/**
 * 
 * 
 * 类名称：GuardianInfor
 * 类描述：
 * 创建人：卢东斌 
 * 修改人：卢东斌 
 * 修改时间：2014-9-17 上午12:55:28
 * 修改备注：
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class GuardianInfor extends BaseEntry {
	private String dadname;// 爸爸姓名
	private String dadphone;// 爸爸电话
	private String flyaddress;// 家庭住址
	private String momname;// 妈妈姓名
	private String momphone;// 妈妈电话
	private String pictureurl;// 家长图像

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
