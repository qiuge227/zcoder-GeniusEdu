package com.geniusedu.logical.implement;

import android.view.View;

import com.geniusedu.logical.interfc.FindView;

/**
 * 类名称：InitView 类描述：绑定控件的实现类 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-5 下午9:44:01 修改备注：
 * 
 * @version 1.0.0
 */
public class InitView implements FindView {
	public View v;// 父布局
	public int resouce;// 控件id

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
