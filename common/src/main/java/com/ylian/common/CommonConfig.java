package com.ylian.common;

import android.content.Context;

/**
 * Created by dafan on 16-7-28.
 */

public class CommonConfig {
	public static final String POINT = " ‧ ";

	private Context mContext;
	private String clientid = "";
	private String loginFragment = "";
	private String base_url = "";

	private boolean islogin = false;
	private static CommonConfig cc;

	protected CommonConfig() {
	}

	public static CommonConfig instance() {
		if (cc == null)
			synchronized (CommonConfig.class) {
				if (cc == null)
					cc = new CommonConfig();
			}
		return cc;
	}

	public void init(Context context) {
		mContext = context;
	}

	public Context getAppCxt() {
		return mContext;
	}

	public boolean islogin() {
		return islogin;
	}

	public void setIslogin(boolean islogin) {
		this.islogin = islogin;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getLoginFragment() {
		return loginFragment;
	}

	public void setLoginFragment(String loginFragment) {
		this.loginFragment = loginFragment;
	}

	public String getBase_url() {
		return base_url;
	}

	public void setBase_url(String base_url) {
		this.base_url = base_url;
	}
}
