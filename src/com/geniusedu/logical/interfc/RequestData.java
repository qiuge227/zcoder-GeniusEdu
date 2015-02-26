package com.geniusedu.logical.interfc;


public interface RequestData {
	/**
	 * 
	 * getDataByNet(请求网络数据，返回实体对象)
	 * 
	 * @return BaseEntry
	 * @exception
	 * @since 1.0.0
	 */
	public void getDataByNet(int urlResId, String... param);
}
