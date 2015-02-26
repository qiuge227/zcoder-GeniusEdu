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
 * 类名称：SelectPicActivity 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-17 上午11:06:17 修改备注：
 * 
 * @version 1.0.0
 * 
 */

public class SelectPicActivity extends Activity implements OnClickListener {

	/***
	 * 使用照相机拍照获取图片
	 */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 2;
	/***
	 * 使用相册中的图片
	 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 1;
	/**
	 * 剪切图片
	 */
	public static final int CHANGE_PIC_SIZE = 3;

	private LinearLayout dialogLayout;
	private Button takePhotoBtn, pickPhotoBtn, cancelBtn;
	private String picpath;
	private String flag;// 标示学生信息界面调用还是家长信息界面调用

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_pic_layout);
		initView();
	}

	/**
	 * 初始化加载View
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
			startActivityForResult(GetPicture.intent, SELECT_PIC_BY_TACK_PHOTO);// 拍照2
			break;
		case R.id.btn_pick_photo:
			GetPicture.pickPhoto();
			startActivityForResult(GetPicture.intent, SELECT_PIC_BY_PICK_PHOTO);// 相册1
			break;
		default:
			finish();
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 如果是直接从相册获取
		case 1:
			if (data != null) {
				GetPicture.startPhotoZoom(data.getData());
				startActivityForResult(GetPicture.intent, CHANGE_PIC_SIZE);
			}

			break;
		// 如果是调用相机拍照时
		case 2:
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/" + CatchApplication.stu_name + ".jpg");
			GetPicture.startPhotoZoom(Uri.fromFile(temp));
			startActivityForResult(GetPicture.intent, CHANGE_PIC_SIZE);
			break;
		// 取得裁剪后的图片
		case 3:
			/**
			 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
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

	// /** 保存剪切后的图片到指定文件夹 */
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
	// System.out.println("成功保存了图片");
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
