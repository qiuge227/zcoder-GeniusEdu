package com.geniusedu.beans;

import android.graphics.Bitmap;

import com.geniusedu.basebeans.BaseEntry;

/**
 * 
 * 
 * 类名称：StudentInfo 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-17 上午12:56:04 修改备注：
 * 
 * @version 1.0.0
 * 
 */
@SuppressWarnings("serial")
public class StudentInfo extends BaseEntry {
	private String stuintegral;// 学生奖励积分
	private String stuclass;// 学生班级
	private String stuidcard;// 学生身份证号
	private String stusex;// 学生性别
	private String stubirth;// 学生出生日期
	private String stunation;// 族
	private String stupicture;// 学生图像字符形式
	private Bitmap stubitpic;//学生图像

	public Bitmap getStubitpic() {
		return stubitpic;
	}

	public void setStubitpic(Bitmap stubitpic) {
		this.stubitpic = stubitpic;
	}

	public String getStupicture() {
		return stupicture;
	}

	public void setStupicture(String stupicture) {
		this.stupicture = stupicture;
	}

	public String getStuintegral() {
		if (stuintegral == null) {
			stuintegral = "";
		}
		return stuintegral;
	}

	@Override
	public String toString() {
		return "StudentInfo [stuintegral=" + stuintegral + ", stuclass="
				+ stuclass + ", stuidcard=" + stuidcard + ", stusex=" + stusex
				+ ", stubirth=" + stubirth + ", stunation=" + stunation
				+ ", stupicture=" + stupicture + "]";
	}

	public void setStuintegral(String stuintegral) {
		if (stuintegral == null) {
			stuintegral = "";
		}
		this.stuintegral = stuintegral;
	}

	public String getStuclass() {
		if (stuclass == null) {
			stuclass = "";
		}
		return stuclass;
	}

	public void setStuclass(String stuclass) {
		if (stuclass == null) {
			stuclass = "";
		}
		this.stuclass = stuclass;
	}

	public String getStuidcard() {
		if (stuidcard == null) {
			stuidcard = "";
		}
		return stuidcard;
	}

	public void setStuidcard(String stuidcard) {
		if (stuidcard == null) {
			stuidcard = "";
		}
		this.stuidcard = stuidcard;
	}

	public String getStusex() {
		if (stusex == null) {
			stusex = "";
		}
		return stusex;
	}

	public void setStusex(String stusex) {
		if (stusex == null) {
			stusex = "";
		}
		this.stusex = stusex;
	}

	public String getStubirth() {
		if (stubirth == null) {
			stubirth = "";
		}
		return stubirth;
	}

	public void setStubirth(String stubirth) {
		if (stubirth == null) {
			stubirth = "";
		}
		this.stubirth = stubirth;
	}

	public String getStunation() {
		if (stunation == null) {
			stunation = "";
		}
		return stunation;
	}

	public void setStunation(String stunation) {
		if (stunation == null) {
			stunation = "";
		}
		this.stunation = stunation;
	}

}
