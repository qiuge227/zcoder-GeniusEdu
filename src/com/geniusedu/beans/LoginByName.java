package com.geniusedu.beans;

import com.geniusedu.basebeans.BaseEntry;
/**
 * 
 * 
 * 类名称：LoginByName
 * 类描述：
 * 创建人：卢东斌 
 * 修改人：卢东斌 
 * 修改时间：2014-9-17 上午12:55:33
 * 修改备注：
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class LoginByName extends BaseEntry {
	private String stuclass;// 班级

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
