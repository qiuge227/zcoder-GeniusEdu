package com.geniusedu.beans;

import com.geniusedu.basebeans.BaseEntry;
/**
 * 
 * 
 * �����ƣ�MyListItem
 * ��������
 * �����ˣ�¬���� 
 * �޸��ˣ�¬���� 
 * �޸�ʱ�䣺2014-9-17 ����12:55:47
 * �޸ı�ע��
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
