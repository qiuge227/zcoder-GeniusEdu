package com.geniusedu.logical.implement;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.beans.GuardianInfor;
import com.geniusedu.beans.LoginByName;
import com.geniusedu.beans.MyListItem;
import com.geniusedu.beans.StudentInfo;
import com.geniusedu.logical.interfc.ParserInter;

/**
 * 
 * 
 * 类名称：ParserImp 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-13 下午10:53:38 修改备注：
 * 
 * @version 1.0.0
 * 
 */
public class ParserImp implements ParserInter {

	/**
	 * 
	 * parserschool(解析所在市学校)
	 * 
	 * @param response
	 * @return ArrayList<BaseEntry>
	 * @exception
	 * @since 1.0.0
	 */
	@Override
	public ArrayList<BaseEntry> parserschool(String response) {
		// TODO Auto-generated method stub
		System.out.println(response);
		MyListItem schoolname = null;
		ArrayList<BaseEntry> list = null;
		try {
			JSONObject json = new JSONObject(response);
			System.out.println("parserschool" + response);
			int status = json.getInt("status");
			if (status == 1) {
				JSONArray jsonArray = json.getJSONArray("result");
				System.out.println(jsonArray.length());
				list = new ArrayList<BaseEntry>();
				for (int i = 0; i < jsonArray.length(); i++) {
					System.out.println("firdud++++");
					JSONObject jsonItem = jsonArray.getJSONObject(i);
					JSONObject obj = jsonItem.getJSONObject("school");
					schoolname = new MyListItem();
					schoolname.setName(obj.getString("schoolName"));
					schoolname.setPcode(obj.getString("schoolId"));
					list.add(schoolname);
					System.out.println("listpar" + list.toString());
				}

			} else {
				return null;
			}
		} catch (JSONException e) {
			return null;
		}

		return list;

	}

	/**
	 * 
	 * parserschool(解析所在市学校)
	 * 
	 * @param response
	 * @return ArrayList<BaseEntry>
	 * @exception
	 * @since 1.0.0
	 */
	@Override
	public ArrayList<BaseEntry> parserclass(String response) {
		// TODO Auto-generated method stub
		System.out.println(response);
		MyListItem schoolname = null;
		ArrayList<BaseEntry> list = null;
		try {
			JSONObject json = new JSONObject(response);
			System.out.println("parserschool" + response);
			int status = json.getInt("status");
			if (status == 1) {
				JSONArray jsonArray = json.getJSONArray("result");
				System.out.println(jsonArray.length());
				list = new ArrayList<BaseEntry>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonItem = jsonArray.getJSONObject(i);
					JSONObject obj = jsonItem.getJSONObject("className");
					schoolname = new MyListItem();
					schoolname.setName(obj.getString("className"));
					list.add(schoolname);
					System.out.println("listpar" + list.toString());
				}
			} else {
				return null;
			}
		} catch (JSONException e) {
			return null;
		}

		return list;

	}

	/**
	 * parserstuinfo(解析学生信息)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */
	@Override
	public BaseEntry parserstuinfo(String response) {
		// TODO Auto-generated method stub
		StudentInfo studentinfo = null;
		String res = null;
		try {
			// try {
			// res = new String(response.getBytes("iso-8859-1"), "UTF-8");
			//
			// } catch (UnsupportedEncodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			JSONObject json = new JSONObject(response);
			System.out.println(response);
			int status = json.getInt("status");
			if (status == 1) {
				JSONObject dataJson = json.getJSONObject("result");
				studentinfo = new StudentInfo();
				studentinfo.stuid = dataJson.getString("stu_number");
				studentinfo.stuname = dataJson.getString("stu_name");
				studentinfo.setStusex(dataJson.getString("stu_gender"));
				studentinfo.setStuidcard(dataJson.getString("stu_idCardNo"));
				studentinfo.setStunation(dataJson.getString("stu_nation"));
				studentinfo.setStuclass(dataJson.getString("stu_class"));
				studentinfo.setStubirth(dataJson.getString("stu_birth"));
				studentinfo.setStuintegral(dataJson.getString("stu_points"));
				studentinfo.setStupicture(dataJson.getString("photo"));
			} else {

				return null;
			}
		} catch (JSONException e) {
			return null;
		}

		return studentinfo;
	}

	/**
	 * 
	 * parserguardian(获取监护人信息)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */

	@Override
	public BaseEntry parserguardian(String response) {
		// TODO Auto-generated method stub
		GuardianInfor guardianinfor = null;
		String res = null;
		try {
			// try {
			// res = new String(response.getBytes("iso-8859-1"), "UTF-8");
			//
			// } catch (UnsupportedEncodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			JSONObject json = new JSONObject(response);
			int status = json.getInt("status");
			if (status == 1) {
				JSONObject dataJson = json.getJSONObject("result");
				guardianinfor = new GuardianInfor();
				guardianinfor.setDadname(dataJson.getString("father_name"));
				guardianinfor.setMomname(dataJson.getString("mother_name"));
				guardianinfor.setMomphone(dataJson.getString("mother_phone"));
				guardianinfor.setDadphone(dataJson.getString("father_phone"));
				guardianinfor.setFlyaddress(dataJson.getString("address"));
				guardianinfor.setPictureurl(dataJson.getString("photo"));
			} else {
				return null;
			}
		} catch (JSONException e) {
			return null;
		}

		return guardianinfor;
	}

	/**
	 * 
	 * parserloginbyname(获取登陆相同的名字)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */
	@Override
	public ArrayList<BaseEntry> parserloginbyname(String response) {
		// TODO Auto-generated method stub
		LoginByName loginbyname = null;
		ArrayList<BaseEntry> list = null;
		try {
			JSONObject json = new JSONObject(response);
			System.out.println(response);
			int status = json.getInt("status");
			if (status == 1) {
				JSONArray jsonArray = json.getJSONArray("result");
				System.out.println(jsonArray.length());
				list = new ArrayList<BaseEntry>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonItem = jsonArray.getJSONObject(i);
					JSONObject obj = jsonItem.getJSONObject("student");
					loginbyname = new LoginByName();
					loginbyname.setStuclass(obj.getString("stu_class"));
					loginbyname.stuid = obj.getString("stu_number");
					loginbyname.stuname = obj.getString("stu_name");
					list.add(loginbyname);
				}
			} else {
				return null;
			}
		} catch (JSONException e) {
			return null;
		}

		return list;

	}

	/**
	 * 
	 * parserrequest(解析请求修改的返回数据)
	 * 
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */

	@Override
	public String parserrequest(String response) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String result = null;
		try {
			JSONObject json = new JSONObject(response);
			int status = json.getInt("status");
			if (status == 1) {
				JSONObject dataJson = json.getJSONObject("result");
				result = dataJson.getString("success");
			} else if (status == 2) {
				// 服务器错误
				return "3";
			}
		} catch (JSONException e) {
			return null;
		}

		return result;

	}

}
