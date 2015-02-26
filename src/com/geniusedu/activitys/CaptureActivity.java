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
 * �����ƣ�CaptureActivity �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-17 ����12:54:19 �޸ı�ע��
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
	private CustomProgressDialog progressDialog; // ������
	Parameters parameter;
	Camera camera;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanning_layout);
		// ��ʼ�� CameraManager
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
	 * addScore(��������Ƿ�ɹ�����Ϣ)
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
						Toast.makeText(CaptureActivity.this, "��ӳɹ�", 1000)
								.show();
						Intent intent2 = new Intent(CaptureActivity.this,
								SuccessActivity.class);
						intent2.putExtra("time", score);
						startActivity(intent2);
					} else if (result.equals("2")) {
						startActivity(new Intent(CaptureActivity.this,
								FailActivity.class));
						Toast.makeText(CaptureActivity.this, "���ʧ��,������ɨɨ", 1000)
								.show();
					} else if (result.equals("3")) {
						startActivity(new Intent(CaptureActivity.this,
								FailActivity.class));
						Toast.makeText(CaptureActivity.this, "��Ч��Ƭ����ʹ�ù�", 1000)
								.show();
					} else {
						startActivity(new Intent(CaptureActivity.this,
								FailActivity.class));
						Toast.makeText(CaptureActivity.this, "���������������쳣", 1000)
								.show();
					}
				} else {
					Toast.makeText(CaptureActivity.this, "�����쳣", 1000).show();
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
	 * initCamera(������һ�仰�����������������) (����������������������� �C ��ѡ)
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
	 * handleDecode(������һ�仰�����������������) (����������������������� �C ��ѡ)
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
				continuePreview();// ���Լ���ɨ��
				Toast.makeText(CaptureActivity.this, "��źͻ���ɨ���ȡʧ��", 1000)
						.show();
			}
			// showMyDialog(cardnumber, cardpoints);
		} else {
			continuePreview();// ���Լ���ɨ��
			Toast.makeText(CaptureActivity.this, "��Чɨ��,�Ҳ���������Ϣ", 1000).show();
		}
	}

	/**
	 * 
	 * initBeepSound(ɨ����ɵ��𶯺���Ч)
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
	 * @Description: ɨ�����Ի�����ͣ�˹��ܣ�
	 * @param @param id �趨�ļ�
	 * @return void ��������
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
					title.setText("���Ῠ��Ϣ");
					cardnumber.setText("��Ƭ���:" + number);
					cardpoints.setText("�������:(10~" + points + ")��");
				} else {
					title.setText("��ͨ����Ϣ");
					cardnumber.setText("��Ƭ���:" + number);
					cardpoints.setText("��Ƭ����:" + points + "��");
				}

				// ȷ���ĵ���¼�
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
							Toast.makeText(CaptureActivity.this, "��źͻ���ɨ���ȡʧ��",
									1000).show();
						}
					}
				});

				no.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						selectDialog.dismiss();
						continuePreview();// ���Լ���ɨ��
					}
				});
			};

		}.showdialog();
	}

	/**
	 * 
	 * continuePreview(����ɨ��) 
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