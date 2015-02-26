package com.geniusedu.beans;

import com.geniusedu.basebeans.BaseEntry;
/**
 * 
 * 
 * 类名称：MyListItem
 * 类描述：
 * 创建人：卢东斌 
 * 修改人：卢东斌 
 * 修改时间：2014-9-17 上午12:55:47
 * 修改备注：
 * @version 1.0.0
 *
 */
public class MyListItem extends BaseEntry{
	private String name;
	private String pcode;
	public String getName(){
		return name;
	}
	public String getPcode(){
		return pcode;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setPcode(String pcode){
		this.pcode=pcode;
	}
}
