package com.geniusedu.activitys;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.geniusedu.app.CatchApplication;
import com.geniusedu.utl.GetPicture;

/**
 * 
 * 
 * �����ƣ�SelectPicActivity �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-17 ����11:06:17 �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */

public class SelectPicActivity extends Activity implements OnClickListener {

	/***
	 * ʹ����������ջ�ȡͼƬ
	 */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 2;
	/***
	 * ʹ������е�ͼƬ
	 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 1;
	/**
	 * ����ͼƬ
	 */
	public static final int CHANGE_PIC_SIZE = 3;

	private LinearLayout dialogLayout;
	private Button takePhotoBtn, pickPhotoBtn, cancelBtn;
	private String picpath;
	private String flag;// ��ʾѧ����Ϣ������û��Ǽҳ���Ϣ�������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_pic_layout);
		initView();
	}

	/**
	 * ��ʼ������View
	 */
	private void initView() {
		dialogLayout = (LinearLayout) findViewById(R.id.dialog_layout);
		dialogLayout.setOnClickListener(this);
		takePhotoBtn = (Button) findViewById(R.id.btn_take_photo);
		takePhotoBtn.setOnClickListener(this);
		pickPhotoBtn = (Button) findViewById(R.id.btn_pick_photo);
		pickPhotoBtn.setOnClickListener(this);
		cancelBtn = (Button) findViewById(R.id.btn_cancel);
		cancelBtn.setOnClickListener(this);
		flag = getIntent().getStringExtra("flag");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_layout:
			finish();
			break;
		case R.id.btn_take_photo:
			GetPicture.takePhoto();
			startActivityForResult(GetPicture.intent, SELECT_PIC_BY_TACK_PHOTO);// ����2
			break;
		case R.id.btn_pick_photo:
			GetPicture.pickPhoto();
			startActivityForResult(GetPicture.intent, SELECT_PIC_BY_PICK_PHOTO);// ���1
			break;
		default:
			finish();
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// �����ֱ�Ӵ�����ȡ
		case 1:
			if (data != null) {
				GetPicture.startPhotoZoom(data.getData());
				startActivityForResult(GetPicture.intent, CHANGE_PIC_SIZE);
			}

			break;
		// ����ǵ����������ʱ
		case 2:
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/" + CatchApplication.stu_name + ".jpg");
			GetPicture.startPhotoZoom(Uri.fromFile(temp));
			startActivityForResult(GetPicture.intent, CHANGE_PIC_SIZE);
			break;
		// ȡ�òü����ͼƬ
		case 3:
			/**
			 * �ǿ��жϴ��һ��Ҫ��֤���������֤�Ļ��� �ڼ���֮��������ֲ����⣬Ҫ���²ü�������
			 * 
			 * 
			 */
			if (data != null) {
				GetPicture.setPicToView(data);
				if (GetPicture.drawable != null) {
					Intent intent = new Intent();
					intent.setAction("pictureselect");
					intent.putExtra("bitmap", GetPicture.bitmap);
					intent.putExtra("picpath",
							Environment.getExternalStorageDirectory() + "/"
									+ CatchApplication.stu_name + ".jpg");
					intent.putExtra("flag", flag);
					sendBroadcast(intent);
					// saveBitmap(GetPicture.bitmap,CatchApplication.stu_name+".png");
					// setPhoto(GetPicture.bitmap);
					// picpath = Environment.getExternalStorageDirectory() + "/"
					// + CatchApplication.stu_name + ".jpg";
					// UploadUtil uploadUtil = UploadUtil.getInstance();
					// Map<String, String> params = new HashMap<String,
					// String>();
					// params.put("stu_number", CatchApplication.stuid);
					// uploadUtil.uploadFile(picpath,
					// getString(R.string.interface_upload_picture),
					// "pic", params);
					finish();
				}
			}
			break;
		default:

			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// /** ������к��ͼƬ��ָ���ļ��� */
	// public void saveBitmap(Bitmap bit, String picname) {
	//
	// File f = new File(getApplicationContext().getPackageResourcePath(),
	// picname);
	// if (f.exists()) {
	// f.delete();
	// }
	// try {
	// FileOutputStream out = new FileOutputStream(f);
	// bit.compress(Bitmap.CompressFormat.PNG, 80, out);
	// out.flush();
	// out.close();
	// System.out.println("�ɹ�������ͼƬ");
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.overridePendingTransition(R.anim.flip_horizontal_in,
				R.anim.flip_horizontal_out);
		super.onPause();
	}

}
