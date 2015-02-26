package com.geniusedu.utl;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
/**
 * 
 * 
 * �����ƣ�MyDialog
 * ��������
 * �����ˣ�¬���� 
 * �޸��ˣ�¬���� 
 * �޸�ʱ�䣺2014-9-18 ����5:21:05
 * �޸ı�ע��
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

	// ���ߵĹ��캯��
	public MyDialog(Context context, int style, int layout) {
		this.context = context;
		this.style = style;
		this.layout = layout;

	}
	// ���ߵĹ��캯��
	public MyDialog(Context context, int style, View view) {
		this.context = context;
		this.style = style;
		this.view = view;

	}

	// ʵ��������
	public static MyDialog getInstance(Context context, int style, int layout) {
		dialog = new MyDialog(context, style, layout);
		return dialog;

	}

	// ��������ʾ�Ի���
	public void showdialog() {
		/* ��ʼ����ͨ�Ի��򡣲�������ʽ */
		selectDialog = new Dialog(context, style);
		selectDialog.setCancelable(true);
		/* ������ͨ�Ի���Ĳ��� */
		if (layout != 0) {
			selectDialog.setContentView(layout);
		} else if (view != null) {
			selectDialog.setContentView(view);
		}

		setdblistener(selectDialog);
		selectDialog.show();// ��ʾ�Ի���
	}

	public void setdblistener(Dialog selectDialog) {
	}
}
