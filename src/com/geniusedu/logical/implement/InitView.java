package com.geniusedu.logical.implement;

import android.view.View;

import com.geniusedu.logical.interfc.FindView;

/**
 * �����ƣ�InitView ���������󶨿ؼ���ʵ���� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-5 ����9:44:01 �޸ı�ע��
 * 
 * @version 1.0.0
 */
public class InitView implements FindView {
	public View v;// ������
	public int resouce;// �ؼ�id

	public InitView(View v, int resouce) {
		this.v = v;
		this.resouce = resouce;
	}

	public InitView getInstance(View v, int resouce) {

		return new InitView(v, resouce);
	}

	@Override
	public View findview() {
		View view = v.findViewById(resouce);
		return view;
	}

}
