package com.geniusedu.activitys;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.geniusedu.app.CatchApplication;
import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.beans.GuardianInfor;
import com.geniusedu.logical.implement.ImRequestData;
import com.geniusedu.utl.Base64Coder;
import com.geniusedu.utl.GetNetPicture;
import com.geniusedu.utl.NetWorkUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 
 * 
 * 类名称：GuardianActivity 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-14 下午4:33:49 修改备注：
 * 
 * @version 1.0.0
 * 
 */
@SuppressLint("ShowToast")
public class GuardianActivity extends Activity implements OnClickListener {

	private EditText dadname, dadphone, momname, momphone, flyaddress;
	private ImageView picture, back;
	private Button saveinfo_bt;
	private String fathername;
	private String mothername;
	private String fatherphone;
	private String motherphone;
	private String famaddress;// 家庭住址
	private String studnumber;// 学生学号
	private String studname = "";// 学生姓名
	private String schoolname;// 学校名字
	private String schoolid;// 学校编号
	private String pictureurl;// 家长图像链接
	public String sch_name;// 转码后的学校名字
	public String stu_name;// 转码后的学生名字
	private Context context;
	public DisplayImageOptions options;// 图像缓存
	public static final int SELECTEPIC = 1;
	public String picpath;
	public ImageLoader image;
	public BroadcastReceiver broadcastreceiver;
	/**
	 * 设置图像
	 */
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			picture.setBackgroundDrawable((BitmapDrawable) msg.obj);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parents_layout);
		studnumber = CatchApplication.stuid;
		schoolname = CatchApplication.schoolname;
		context = getParent();
		findView();
		setListener();
		getBroadcast();
		getGuardianInfo(CatchApplication.stuid);
	}

	/**
	 * 
	 * findView(绑定控件)
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void findView() {
		back = (ImageView) findViewById(R.id.back);
		saveinfo_bt = (Button) findViewById(R.id.saveinfo_bt);
		dadname = (EditText) findViewById(R.id.dadname);
		dadphone = (EditText) findViewById(R.id.dadphone);
		momname = (EditText) findViewById(R.id.momname);
		momphone = (EditText) findViewById(R.id.momphone);
		flyaddress = (EditText) findViewById(R.id.fly_address);
		picture = (ImageView) findViewById(R.id.picture);
		image = ImageLoader.getInstance();
		picpath = getApplicationContext().getPackageResourcePath()
				+ CatchApplication.stu_name + ".png";

		File file = new File(picpath);
		if (file.exists()) {
			System.out.println("picpath" + picpath);
			Bitmap bm = BitmapFactory.decodeFile(picpath);
			BitmapDrawable drawable = new BitmapDrawable(bm);
			// 将图片显示到ImageView中
			picture.setBackgroundDrawable(drawable);
		}
		/**
		 * url获取图片缓存机制
		 */
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.tutu)
				.showImageForEmptyUri(R.drawable.tutu)
				.showImageOnFail(R.drawable.tutu).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
	}

	/**
	 * 
	 * setListener(设置监听) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void setListener() {
		saveinfo_bt.setOnClickListener(this);
		back.setOnClickListener(this);
		picture.setOnClickListener(this);
	}

	/**
	 * 
	 * getBroadcast(接受来自学校选择界面的广播) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void getBroadcast() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("mainactivity");
		intentFilter.addAction("pictureselect");
		broadcastreceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals("mainactivity")) {
					studname = intent.getStringExtra("studname");
				}
				if (intent.getAction().equals("pictureselect")) {

					if (intent.getStringExtra("flag").equals("parents")) {
						Bitmap bit = intent.getParcelableExtra("bitmap");
						picture.setBackgroundDrawable(new BitmapDrawable(bit));
						picpath = intent.getStringExtra("picpath");
						setPhoto(bit);
					}

				}
			}
		};
		context.registerReceiver(broadcastreceiver, intentFilter);
	}

	/**
	 * 
	 * setPhoto(设置图像上传图像)
	 * 
	 * @param v
	 * @exception
	 * @since 1.0.0
	 */
	private void setPhoto(Bitmap bit) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bit.compress(Bitmap.CompressFormat.JPEG, 80, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buffer = out.toByteArray();
		final String picStr = new String(Base64Coder.encodeLines(buffer));
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("stu_number",
						CatchApplication.stuid));
				params.add(new BasicNameValuePair("photo", picStr));
				final String result = NetWorkUtil.httpPost(
						getString(R.string.interface_upload_picture), params);
				runOnUiThread(new Runnable() {
					public void run() {
						// Toast.makeText(GuardianActivity.this, result,
						// Toast.LENGTH_SHORT).show();
					}
				});
			}
		}).start();
	}

	/**
	 * 
	 * canEdit(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void canEdit() {
	}

	/**
	 * 
	 * cannotEdit(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void cannotEdit() {
	}

	/**
	 * 
	 * getGuardianInfo(获取家长信息) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void getGuardianInfo(String stuid) {
		new ImRequestData(GuardianActivity.this) {

			@Override
			public void getResult(String result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void getBaseentry(BaseEntry baseentry) {
				// TODO Auto-generated method stub
				if (baseentry != null) {
					GuardianInfor guardianinfo = (GuardianInfor) baseentry;
					dadname.setText(guardianinfo.getDadname());
					dadphone.setText(guardianinfo.getDadphone());
					momname.setText(guardianinfo.getMomname());
					momphone.setText(guardianinfo.getMomphone());
					flyaddress.setText(guardianinfo.getFlyaddress());
					pictureurl = guardianinfo.getPictureurl();
					new Thread() {
						@SuppressWarnings({ "deprecation", "unused" })
						public void run() {

							// image.displayImage(pictureurl, picture, options);
							BitmapDrawable bit;
							try {
								bit = new BitmapDrawable(
										GetNetPicture.getBitmap(pictureurl));
								if (bit == null) {
									picture.setBackgroundResource(R.drawable.tutu);
								} else {
									Message msg = Message.obtain();
									msg.obj = bit;
									handler.sendMessage(msg);
								}

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						};
					}.start();

				}

			}
		}.getDataByNet(R.string.interface_download_guardianinfo, stuid);
	}

	/**
	 * 
	 * setGuardianInfo(修改家长信息) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void setGuardianInfo(String... param) {
		new ImRequestData(GuardianActivity.this) {

			@Override
			public void getResult(String result) {
				// TODO Auto-generated method stub
				if (result != null) {
					if (result.equals("1")) {
						Toast.makeText(GuardianActivity.this, "修改成功", 1000)
								.show();
					} else if (result.equals("2")) {
						Toast.makeText(GuardianActivity.this, "修改失败", 1000)
								.show();

					} else {
						Toast.makeText(GuardianActivity.this, "服务器数据连接异常", 1000)
								.show();
					}
				} else {
					Toast.makeText(GuardianActivity.this, "解析异常", 1000).show();
				}
			}

			@Override
			public void getBaseentry(BaseEntry baseentry) {
				// TODO Auto-generated method stub

			}
		}.getDataByNet(R.string.interface_upload_guardianinfo, param);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.saveinfo_bt:
			try {
				fathername = URLEncoder.encode(dadname.getText().toString(),
						"UTF-8");
				mothername = URLEncoder.encode(momname.getText().toString(),
						"UTF-8");
				famaddress = URLEncoder.encode(flyaddress.getText().toString(),
						"UTF-8");
				motherphone = String.valueOf(momphone.getText());
				fatherphone = dadphone.getText().toString();
				sch_name = URLEncoder.encode(schoolname, "UTF-8");
				stu_name = URLEncoder.encode(studname, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setGuardianInfo(fathername, mothername, motherphone, fatherphone,
					famaddress, studnumber, sch_name, stu_name, schoolid);
			break;
		case R.id.back:
			Intent intent = new Intent();
			intent.setAction("Guardian");
			sendBroadcast(intent);
			break;
		case R.id.picture:
			Intent intent2 = new Intent(GuardianActivity.this,
					SelectPicActivity.class);
			intent2.putExtra("flag", "parents");
			startActivityForResult(intent2, SELECTEPIC);
			break;
		default:
			break;
		}

	}

	/**
	 * 获取sd卡位置 getSDPath(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (broadcastreceiver != null) {
			context.unregisterReceiver(broadcastreceiver);
		}

		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
