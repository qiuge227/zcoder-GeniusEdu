package com.geniusedu.logical.implement;

import android.content.Context;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.geniusedu.activitys.R;
import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.logical.interfc.RequestData;
import com.geniusedu.utl.ParametersUtils;
/**
 * 
 * 
 * 类名称：ImRequestData
 * 类描述：
 * 创建人：卢东斌 
 * 修改人：卢东斌 
 * 修改时间：2014-9-17 上午12:49:22
 * 修改备注：
 * @version 1.0.0
 *
 */
public abstract class ImRequestData implements RequestData {
	public Context context;
	public String url;// 连接不变部分
	public BaseEntry baseentry;
	public String result;

	public ImRequestData(Context context) {
		this.context = context;
	}

	/**
	 * 
	 * getDataByNet(请求网络数据，返回实体对象)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */
	@Override
	public void getDataByNet(final int urlResId, String... param) {
		baseentry = null;// 对象制空
		RequestQueue requestQueue = Volley.getQueueInstance(context);
		StringRequest request = new StringRequest(Method.GET,
				ParametersUtils.urlJoint(context.getString(urlResId),
						ParametersUtils.getRequestParams(urlResId, param)),
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						System.out.println("getDataByNet" + response);
						ParserImp parser = new ParserImp();

						switch (urlResId) {

						case R.string.interface_upload_loginbynameinfo:
							// 姓名登陆获取所有相同姓名
							baseentry = new BaseEntry();
							baseentry.beanslist = parser
									.parserloginbyname(response);
							getBaseentry(baseentry);
							break;
						case R.string.interface_download_stuinfo:
							// 获取学生信息
							baseentry = parser.parserstuinfo(response);
							getBaseentry(baseentry);
							break;
						case R.string.interface_download_guardianinfo:
							// 获取监护人信息
							baseentry = parser.parserguardian(response);
							getBaseentry(baseentry);
							break;
						case R.string.interface_download_school:
							// 获取所在市学校
							baseentry = new BaseEntry();
							baseentry.beanslist = parser.parserschool(response);
							getBaseentry(baseentry);
							break;
						case R.string.interface_download_class:	
							baseentry = new BaseEntry();
							baseentry.beanslist = parser.parserclass(response);
							getBaseentry(baseentry);
							break;
						default:
							// 服务器返回操作状态，解析状态值
							result = parser.parserrequest(response);
							getResult(result);
							break;

						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub

					}

				});
		requestQueue.add(request);
	}

	// /**
	// *
	// * setDataByNet(请求设置服务端数据，返回修改状态值或者登陆状态值)
	// *
	// * @param urlResId
	// * @param param
	// * @return String
	// * @exception
	// * @since 1.0.0
	// */
	// @Override
	// public void setDataByNet(int urlResId, String... param) {
	//
	// RequestQueue requestQueue = Volley.getQueueInstance(context);
	// StringRequest request = new StringRequest(Method.GET,
	// ParametersUtils.urlJoint(context.getString(urlResId),
	// ParametersUtils.getRequestParams(urlResId, param)),
	// new Listener<String>() {
	//
	// @Override
	// public void onResponse(String response) {
	//
	// }
	// }, new ErrorListener() {
	//
	// @Override
	// public void onErrorResponse(VolleyError arg0) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// });
	// requestQueue.add(request);
	//
	// }

	/**
	 * 
	 * getResult(修改或者登陆是返回的数据获取方法)
	 * 
	 * @param result
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public abstract void getResult(String result);

	/**
	 * 
	 * getBaseentry(查询学生或者家长信息返回的信息对象获取方法)
	 * 
	 * @param baseentry
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public abstract void getBaseentry(BaseEntry baseentry);

}
