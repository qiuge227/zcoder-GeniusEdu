package com.geniusedu.beans;

import com.geniusedu.basebeans.BaseEntry;
/**
 * 
 * 
 * 类名称：ScanintegralInfo
 * 类描述：
 * 创建人：卢东斌 
 * 修改人：卢东斌 
 * 修改时间：2014-9-17 上午12:55:53
 * 修改备注：
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class ScanintegralInfo extends BaseEntry {
	private String scanintegral;// 二维码扫描获取的积分

	public String getScanintegral() {
		if (scanintegral == null) {
			scanintegral = "";
		}
		return scanintegral;
	}

	public void setScanintegral(String scanintegral) {
		if (scanintegral == null) {
			scanintegral = "";
		}
		this.scanintegral = scanintegral;
	}
}
