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
 * �����ƣ�ImRequestData
 * ��������
 * �����ˣ�¬���� 
 * �޸��ˣ�¬���� 
 * �޸�ʱ�䣺2014-9-17 ����12:49:22
 * �޸ı�ע��
 * @version 1.0.0
 *
 */
public abstract class ImRequestData implements RequestData {
	public Context context;
	public String url;// ���Ӳ��䲿��
	public BaseEntry baseentry;
	public String result;

	public ImRequestData(Context context) {
		this.context = context;
	}

	/**
	 * 
	 * getDataByNet(�����������ݣ�����ʵ�����)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */
	@Override
	public void getDataByNet(final int urlResId, String... param) {
		baseentry = null;// �����ƿ�
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
							// ������½��ȡ������ͬ����
							baseentry = new BaseEntry();
							baseentry.beanslist = parser
									.parserloginbyname(response);
							getBaseentry(baseentry);
							break;
						case R.string.interface_download_stuinfo:
							// ��ȡѧ����Ϣ
							baseentry = parser.parserstuinfo(response);
							getBaseentry(baseentry);
							break;
						case R.string.interface_download_guardianinfo:
							// ��ȡ�໤����Ϣ
							baseentry = parser.parserguardian(response);
							getBaseentry(baseentry);
							break;
						case R.string.interface_download_school:
							// ��ȡ������ѧУ
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
							// ���������ز���״̬������״ֵ̬
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
	// * setDataByNet(�������÷�������ݣ������޸�״ֵ̬���ߵ�½״ֵ̬)
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
	 * getResult(�޸Ļ��ߵ�½�Ƿ��ص����ݻ�ȡ����)
	 * 
	 * @param result
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public abstract void getResult(String result);

	/**
	 * 
	 * getBaseentry(��ѯѧ�����߼ҳ���Ϣ���ص���Ϣ�����ȡ����)
	 * 
	 * @param baseentry
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public abstract void getBaseentry(BaseEntry baseentry);

}
