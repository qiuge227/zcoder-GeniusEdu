package com.geniusedu.logical.interfc;

import java.util.ArrayList;

import com.geniusedu.basebeans.BaseEntry;

public interface ParserInter {
	
	/**
	 * 
	 * parserschool(解析所在市学校)
	 * 
	 * @param response
	 * @return ArrayList<BaseEntry>
	 * @exception
	 * @since 1.0.0
	 */
	public ArrayList<BaseEntry> parserschool(String response);
	
	/**
	 * 
	 * parserschool(解析所在市学校)
	 * 
	 * @param response
	 * @return ArrayList<BaseEntry>
	 * @exception
	 * @since 1.0.0
	 */
	public ArrayList<BaseEntry> parserclass(String response);

	/**
	 * 
	 * parserstuinfo(解析学生信息)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */
	public BaseEntry parserstuinfo(String response);

	/**
	 * 
	 * parserguardian(获取监护人信息)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */

	public BaseEntry parserguardian(String response);

	/**
	 * 
	 * parserloginbyname(获取登陆相同的名字)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */
	public ArrayList<BaseEntry> parserloginbyname(String response);

	/**
	 * 
	 * parserrequest(解析请求修改的返回数据)
	 * 
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */

	public String parserrequest(String response);
}
