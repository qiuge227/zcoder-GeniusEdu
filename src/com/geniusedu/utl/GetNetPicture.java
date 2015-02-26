package com.geniusedu.utl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GetNetPicture {

	/**
	 * 
	 * getBitmap(������һ�仰�����������������) (����������������������� �C ��ѡ)
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 *             Bitmap
	 * @exception
	 * @since 1.0.0
	 */
	public static Bitmap getBitmap(String path) throws IOException {

		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			return bitmap;
		}
		return null;
	}
}
