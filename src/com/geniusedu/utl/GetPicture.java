package com.geniusedu.utl;

import java.io.File;
import com.geniusedu.app.CatchApplication;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

public class GetPicture {

	public static Intent intent;
	public static Drawable drawable;
	public static Bitmap bitmap;

	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	public static void startPhotoZoom(Uri uri) {
		/*
		 * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * ֱ��������Ctrl+F�ѣ�CROP ��֮ǰС��û��ϸ��������ʵ��׿ϵͳ���Ѿ����Դ�ͼƬ�ü�����, ��ֱ�ӵ����ؿ�ģ�С����C C++
		 * ���������ϸ�˽�ȥ�ˣ������Ӿ������ӣ������о���������ô ��������...���
		 */
		intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// �������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
	}

	/**
	 * ���ջ�ȡͼƬ
	 */
	public static void takePhoto() {
		/**
		 * ������仹�������ӣ����ÿ������չ��ܣ�����Ϊʲô�п������գ���ҿ��Բο����¹ٷ�
		 * �ĵ���you_sdk_path/docs/guide/topics/media/camera.html
		 * �Ҹտ���ʱ����Ϊ̫�������濴����ʵ�Ǵ�ģ�����������õ�̫���ˣ����Դ�Ҳ�Ҫ��Ϊ
		 * �ٷ��ĵ�̫���˾Ͳ����ˣ���ʵ�Ǵ�ģ�����ط�С��Ҳ���ˣ��������
		 */
		intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// �������ָ������������պ����Ƭ�洢��·��
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(),
				CatchApplication.stu_name + ".jpg")));
	}

	/***
	 * �������ȡͼƬ
	 */
	public static void pickPhoto() {
		intent = new Intent(Intent.ACTION_PICK, null);

		/**
		 * ������仰����������ʽд��һ����Ч���������
		 * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		 * intent.setType(""image/*");������������
		 * ���������Ҫ�����ϴ�����������ͼƬ����ʱ����ֱ��д�磺"image/jpeg �� image/png�ȵ�����"
		 * ����ط�С���и����ʣ�ϣ�����ֽ���£������������URI������ΪʲôҪ��������ʽ��дѽ����ʲô����
		 */
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
	}

	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param picdata
	 */
	public static void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			bitmap = extras.getParcelable("data");
			drawable = new BitmapDrawable(bitmap);
			/**
			 * ����ע�͵ķ����ǽ��ü�֮���ͼƬ��Base64Coder���ַ���ʽ�� ������������QQͷ���ϴ����õķ������������
			 */

			/*
			 * ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 * photo.compress(Bitmap.CompressFormat.JPEG, 60, stream); byte[] b
			 * = stream.toByteArray(); // ��ͼƬ�����ַ�����ʽ�洢����
			 * 
			 * tp = new String(Base64Coder.encodeLines(b));
			 * ����ط���ҿ���д�¸��������ϴ�ͼƬ��ʵ�֣�ֱ�Ӱ�tpֱ���ϴ��Ϳ����ˣ� ����������ķ����Ƿ������Ǳߵ����ˣ����
			 * 
			 * ������ص��ķ����������ݻ�����Base64Coder����ʽ�Ļ������������·�ʽת�� Ϊ���ǿ����õ�ͼƬ���;�OK��...���
			 * Bitmap dBitmap = BitmapFactory.decodeFile(tp); Drawable drawable
			 * = new BitmapDrawable(dBitmap);
			 */
		}
	}



	/**
	 * ��ȡsd��λ��
	 * getSDPath(������һ�仰�����������������)
	 * (����������������������� �C ��ѡ)
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼
		}
		return sdDir.toString();

	}

}
