package com.geniusedu.logical.interfc;

import java.util.ArrayList;

import com.geniusedu.basebeans.BaseEntry;

public interface ParserInter {
	
	/**
	 * 
	 * parserschool(����������ѧУ)
	 * 
	 * @param response
	 * @return ArrayList<BaseEntry>
	 * @exception
	 * @since 1.0.0
	 */
	public ArrayList<BaseEntry> parserschool(String response);
	
	/**
	 * 
	 * parserschool(����������ѧУ)
	 * 
	 * @param response
	 * @return ArrayList<BaseEntry>
	 * @exception
	 * @since 1.0.0
	 */
	public ArrayList<BaseEntry> parserclass(String response);

	/**
	 * 
	 * parserstuinfo(����ѧ����Ϣ)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */
	public BaseEntry parserstuinfo(String response);

	/**
	 * 
	 * parserguardian(��ȡ�໤����Ϣ)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */

	public BaseEntry parserguardian(String response);

	/**
	 * 
	 * parserloginbyname(��ȡ��½��ͬ������)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */
	public ArrayList<BaseEntry> parserloginbyname(String response);

	/**
	 * 
	 * parserrequest(���������޸ĵķ�������)
	 * 
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */

	public String parserrequest(String response);
}
