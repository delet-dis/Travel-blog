package com.delet_dis.travelblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.delet_dis.travelblog.http.Blog;
import com.delet_dis.travelblog.http.BlogHttpClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	loadData();
  }

  private void loadData() {
	BlogHttpClient.INSTANCE.loadBlogArticles(new BlogHttpClient.BlogArticlesCallback() {
	  @Override
	  public void onSuccess(List<Blog> blogList) {
		runOnUiThread(() -> {
		});
	  }

	  @Override
	  public void onError() {
		runOnUiThread(() -> {
		  showErrorSnackbar();
		});
	  }
	});
  }

  private void showErrorSnackbar() {
	View rootView = findViewById(android.R.id.content);
	Snackbar snackbar = Snackbar.make(rootView, R.string.snackbarLoadingError, Snackbar.LENGTH_INDEFINITE);
	snackbar.setActionTextColor(getColor(R.color.orange500));
	snackbar.setAction(R.string.snackbarRetryText, v -> {
	  loadData();
	  snackbar.dismiss();
	});
	snackbar.show();
  }
}