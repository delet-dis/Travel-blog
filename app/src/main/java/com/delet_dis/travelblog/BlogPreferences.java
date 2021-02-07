package com.delet_dis.travelblog;

import android.content.Context;
import android.content.SharedPreferences;

public class BlogPreferences {
  public static final String KEY_LOGIN_STATE = "key_login_state";

  private final SharedPreferences preferences;

  BlogPreferences(Context context) {
	preferences =
			context.getSharedPreferences("travel-blog", Context.MODE_PRIVATE);
  }

  public boolean isLoggedIn() {
	return preferences.getBoolean(KEY_LOGIN_STATE, false);
  }

  public void setLoginState(boolean loggedIn) {
	preferences.edit().putBoolean(KEY_LOGIN_STATE, loggedIn).apply();
  }
}
