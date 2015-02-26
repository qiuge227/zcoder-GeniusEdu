package com.geniusedu.basebeans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * 
 * 类名称：BaseEntry 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-17 上午12:55:13 修改备注：
 * 
 * @version 1.0.0
 * 
 */
@SuppressWarnings("serial")
public class BaseEntry implements Serializable {
	public String stuid;// 学生id
	public String stuname;// 学生姓名
	public ArrayList<BaseEntry> beanslist = new ArrayList<BaseEntry>();// 保存实体类
	// public static String stupassword;//学生登陆密码

}
