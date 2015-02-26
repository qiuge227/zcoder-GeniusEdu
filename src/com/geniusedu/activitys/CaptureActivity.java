package com.geniusedu.activitys;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusedu.app.CatchApplication;
import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.camera.CameraManager;
import com.geniusedu.decoding.CaptureActivityHandler;
import com.geniusedu.decoding.InactivityTimer;
import com.geniusedu.logical.implement.ImRequestData;
import com.geniusedu.utl.CustomProgressDialog;
import com.geniusedu.utl.MyDialog;
import com.geniusedu.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

/**
 * 
 * 
 * 类名称：CaptureActivity 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-17 上午12:54:19 修改备注：
 * 
 * @version 1.0.0
 * 
 */
public class CaptureActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private ImageView openlight, closelight,back;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private String stuid;
	private CustomProgressDialog progressDialog; // 弹出框
	Parameters parameter;
	Camera camera;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanning_layout);
		// 初始化 CameraManager
		stuid = getIntent().getStringExtra("stuid");
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		openlight = (ImageView) findViewById(R.id.openlight);
		closelight = (ImageView) findViewById(R.id.closelight);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		openlight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				closelight.setVisibility(View.VISIBLE);
				openlight.setVisibility(View.GONE);
				CameraManager.get().openF();
			}
		});
		closelight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openlight.setVisibility(View.VISIBLE);
				closelight.setVisibility(View.GONE);
				CameraManager.get().stopF();
			}
		});
		
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		// stopProgressDialog();
	}

	/**
	 * 
	 * addScore(接受添加是否成功的信息)
	 * 
	 * @param number
	 * @param score
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void addScore(String number, final String score) {
		new ImRequestData(CaptureActivity.this) {

			@Override
			public void getResult(String result) {
				// TODO Auto-generated method stub
				if (result != null) {
					if (result.equals("1")) {
						Toast.makeText(CaptureActivity.this, "添加成功", 1000)
								.show();
						Intent intent2 = new Intent(CaptureActivity.this,
								SuccessActivity.class);
						intent2.putExtra("time", score);
						startActivity(intent2);
					} else if (result.equals("2")) {
						startActivity(new Intent(CaptureActivity.this,
								FailActivity.class));
						Toast.makeText(CaptureActivity.this, "添加失败,请重新扫扫", 1000)
								.show();
					} else if (result.equals("3")) {
						startActivity(new Intent(CaptureActivity.this,
								FailActivity.class));
						Toast.makeText(CaptureActivity.this, "无效卡片或已使用过", 1000)
								.show();
					} else {
						startActivity(new Intent(CaptureActivity.this,
								FailActivity.class));
						Toast.makeText(CaptureActivity.this, "服务器数据连接异常", 1000)
								.show();
					}
				} else {
					Toast.makeText(CaptureActivity.this, "解析异常", 1000).show();
				}
				finish();
			}

			@Override
			public void getBaseentry(BaseEntry baseentry) {
				// TODO Auto-generated method stub

			}
		}.getDataByNet(R.string.interface_upload_integral, stuid, score,
				number, CatchApplication.schoolid);

	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.overridePendingTransition(R.anim.flip_horizontal_in,
				R.anim.flip_horizontal_out);
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * 
	 * initCamera(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param surfaceHolder
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	/**
	 * 
	 * handleDecode(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param obj
	 * @param barcode
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void handleDecode(final Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String cardinfo = obj.getText().toString();
		if (cardinfo.contains("-") && cardinfo.length() < 20) {

			int idstar = cardinfo.indexOf("-");
			final String cardnumber = cardinfo.substring(0, idstar);
			final String cardpoints = cardinfo.substring(idstar + 1,
					cardinfo.length());
			if (!cardnumber.equals("") && !cardpoints.equals("")) {
				if (cardnumber.startsWith("A")) {
					int randompoints = (int) (Math.random() * Integer
							.parseInt(cardpoints)) + 1;
					if (randompoints < 10) {
						randompoints = 10;
					}
					addScore(cardnumber, String.valueOf(randompoints));
				} else {
					addScore(cardnumber, cardpoints);
				}

			} else {
				continuePreview();// 可以继续扫描
				Toast.makeText(CaptureActivity.this, "编号和积分扫描获取失败", 1000)
						.show();
			}
			// showMyDialog(cardnumber, cardpoints);
		} else {
			continuePreview();// 可以继续扫描
			Toast.makeText(CaptureActivity.this, "无效扫描,找不到积分信息", 1000).show();
		}
	}

	/**
	 * 
	 * initBeepSound(扫描完成的震动和音效)
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	/**
	 * 
	 * @Title: showMyDialog
	 * @Description: 扫描结果对话框（暂停此功能）
	 * @param @param id 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void showMyDialog(final String number, final String points) {
		new MyDialog(CaptureActivity.this, R.style.dialog,
				R.layout.capdialog_layout) {

			public void setdblistener(final Dialog selectDialog) {
				Button yes = (Button) selectDialog
						.findViewById(R.id.dialog_yes);
				Button no = (Button) selectDialog.findViewById(R.id.dialog_no);
				TextView cardnumber = (TextView) selectDialog
						.findViewById(R.id.cardnumber);
				TextView cardpoints = (TextView) selectDialog
						.findViewById(R.id.cardpoints);
				TextView title = (TextView) selectDialog
						.findViewById(R.id.dialog_title);
				if (number.startsWith("A")) {
					title.setText("机会卡信息");
					cardnumber.setText("卡片编号:" + number);
					cardpoints.setText("随机积分:(10~" + points + ")分");
				} else {
					title.setText("普通卡信息");
					cardnumber.setText("卡片编号:" + number);
					cardpoints.setText("卡片积分:" + points + "分");
				}

				// 确定的点击事件
				yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						selectDialog.dismiss();
						if (!number.equals("") && !points.equals("")) {
							if (number.startsWith("A")) {

							} else {
								addScore(number, points);
							}

						} else {
							Toast.makeText(CaptureActivity.this, "编号和积分扫描获取失败",
									1000).show();
						}
					}
				});

				no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						selectDialog.dismiss();
						continuePreview();// 可以继续扫描
					}
				});
			};

		}.showdialog();
	}

	/**
	 * 
	 * continuePreview(继续扫描) 
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void continuePreview() {
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		initCamera(surfaceHolder);
		if (handler != null)
			handler.restartPreviewAndDecode();
	}

}