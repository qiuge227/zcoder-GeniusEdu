package com.geniusedu.basebeans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * 
 * �����ƣ�BaseEntry �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-17 ����12:55:13 �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */
@SuppressWarnings("serial")
public class BaseEntry implements Serializable {
	public String stuid;// ѧ��id
	public String stuname;// ѧ������
	public ArrayList<BaseEntry> beanslist = new ArrayList<BaseEntry>();// ����ʵ����
	// public static String stupassword;//ѧ����½����

}
