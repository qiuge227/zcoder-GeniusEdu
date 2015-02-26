package com.geniusedu.beans;

import com.geniusedu.basebeans.BaseEntry;
/**
 * 
 * 
 * 类名称：LoginInfor
 * 类描述：
 * 创建人：卢东斌 
 * 修改人：卢东斌 
 * 修改时间：2014-9-17 上午12:55:40
 * 修改备注：
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class LoginInfor extends BaseEntry {
	private String stupassword;// 学生登陆密码

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
