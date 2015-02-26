package com.geniusedu.beans;

import android.graphics.Bitmap;

public class UserRecord {
	private String userName;
	private String userId;
	private Bitmap userPic;
	private String passWord;
	private int isSave;//1.±£¥Ê√‹¬Î£ª2.≤ª±£¥Ê√‹¬Î

	public String getUserName() {
		return userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Bitmap getUserPic() {
		return userPic;
	}

	public void setUserPic(Bitmap userPic) {
		this.userPic = userPic;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getIsSave() {
		return isSave;
	}

	public void setIsSave(int isSave) {
		this.isSave = isSave;
	}

	
}
