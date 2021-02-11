package com.delet_dis.travelblog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.delet_dis.travelblog.adapter.MainAdapter;
import com.delet_dis.travelblog.http.Blog;
import com.delet_dis.travelblog.http.BlogHttpClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

  private MainAdapter mainAdapter;

  private SwipeRefreshLayout refreshLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	mainAdapter = new MainAdapter();

	RecyclerView recyclerView = findViewById(R.id.recyclerView);
	recyclerView.setLayoutManager(new LinearLayoutManager(this));
	recyclerView.setAdapter(mainAdapter);

	refreshLayout = findViewById(R.id.refreshLayout);
	refreshLayout.setOnRefreshListener(this::loadData);

	loadData();

  }

  private void loadData() {
	refreshLayout.setRefreshing(true);
	BlogHttpClient.INSTANCE.loadBlogArticles(new BlogHttpClient.BlogArticlesCallback() {
	  @Override
	  public void onSuccess(List<Blog> blogList) {
		runOnUiThread(() -> {
		  mainAdapter.submitList(blogList);
		  refreshLayout.setRefreshing(false);
		});
	  }

	  @Override
	  public void onError() {
		runOnUiThread(() -> {
		  showErrorSnackbar(getApplicationContext(), findViewById(android.R.id.content));
		  refreshLayout.setRefreshing(false);
//		  SnackbarHelper.showErrorSnackbar(getApplicationContext(), findViewById(android.R.id.content));
		});
	  }
	});
  }

  private void showErrorSnackbar(Context context, View rootView) {
	Snackbar snackbar = Snackbar.make(rootView, R.string.snackbarLoadingError, Snackbar.LENGTH_INDEFINITE);
	snackbar.setActionTextColor(context.getColor(R.color.orange500));
	snackbar.setAction(R.string.snackbarRetryText, v -> {
	  loadData();
	  snackbar.dismiss();
	});
	snackbar.show();
  }

//  public static class SnackbarCallback implements SnackbarHelper.Callback {
//	@Override
//	public void reloadData() {
//	  loadData();
//	}
//  }
}

