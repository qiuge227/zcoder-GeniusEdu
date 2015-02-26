package com.geniusedu.utl;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
/**
 * 
 * 
 * 类名称：MyDialog
 * 类描述：
 * 创建人：卢东斌 
 * 修改人：卢东斌 
 * 修改时间：2014-9-18 下午5:21:05
 * 修改备注：
 * @version 1.0.0
 *
 */
public class MyDialog {
	private int style;
	private int layout = 0;
	private Context context;
	private static MyDialog dialog;
	private Dialog selectDialog;
	private View view = null;

	// 工具的构造函数
	public MyDialog(Context context, int style, int layout) {
		this.context = context;
		this.style = style;
		this.layout = layout;

	}
	// 工具的构造函数
	public MyDialog(Context context, int style, View view) {
		this.context = context;
		this.style = style;
		this.view = view;

	}

	// 实例化对象
	public static MyDialog getInstance(Context context, int style, int layout) {
		dialog = new MyDialog(context, style, layout);
		return dialog;

	}

	// 创建和显示对话框
	public void showdialog() {
		/* 初始化普通对话框。并设置样式 */
		selectDialog = new Dialog(context, style);
		selectDialog.setCancelable(true);
		/* 设置普通对话框的布局 */
		if (layout != 0) {
			selectDialog.setContentView(layout);
		} else if (view != null) {
			selectDialog.setContentView(view);
		}

		setdblistener(selectDialog);
		selectDialog.show();// 显示对话框
	}

	public void setdblistener(Dialog selectDialog) {
	}
}
