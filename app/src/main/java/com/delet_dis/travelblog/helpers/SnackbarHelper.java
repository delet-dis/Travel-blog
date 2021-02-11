package com.delet_dis.travelblog.helpers;

import android.content.Context;
import android.view.View;

import com.delet_dis.travelblog.MainActivity;
import com.delet_dis.travelblog.R;
import com.google.android.material.snackbar.Snackbar;

public enum SnackbarHelper {

  INSTANCE;


//  MainActivity.SnackbarCallback snackbarCallback = new MainActivity.SnackbarCallback();

  public static void showErrorSnackbar(Context context, View rootView) {
	Snackbar snackbar = Snackbar.make(rootView, R.string.snackbarLoadingError, Snackbar.LENGTH_INDEFINITE);
	snackbar.setActionTextColor(context.getColor(R.color.orange500));
	snackbar.setAction(R.string.snackbarRetryText, v -> {
//	  callback.reloadData();
	  snackbar.dismiss();
	});
	snackbar.show();
  }


}
