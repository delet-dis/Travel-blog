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
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final int SORT_TITLE = 0;
  private static final int SORT_DATE = 1;

  private int currentSort = SORT_DATE;

  private MainAdapter mainAdapter;

  private SwipeRefreshLayout refreshLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	MaterialToolbar toolbar = findViewById(R.id.toolbar);
	toolbar.setOnMenuItemClickListener(item -> {
	  if (item.getItemId() == R.id.sort) {
		onSortClicked();
	  }
	  return false;
	});

	mainAdapter = new MainAdapter(blog ->
			BlogDetailsActivity.startBlogDetailsActivity(this, blog));

	RecyclerView recyclerView = findViewById(R.id.recyclerView);
	recyclerView.setLayoutManager(new LinearLayoutManager(this));
	recyclerView.setAdapter(mainAdapter);

	refreshLayout = findViewById(R.id.refreshLayout);
	refreshLayout.setOnRefreshListener(this::loadData);

	loadData();

  }

  private void onSortClicked() {
	String[] items = {"Title", "Date"};
	new MaterialAlertDialogBuilder(this)
			.setTitle("Sort order")
			.setSingleChoiceItems(items, currentSort, (dialog, which) -> {
			  dialog.dismiss();
			  currentSort = which;
			  sortData();
			}).show();
  }

  private void sortData() {
	if (currentSort == SORT_TITLE) {
	  mainAdapter.sortByTitle();
	} else if (currentSort == SORT_DATE) {
	  mainAdapter.sortByDate();
	}
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

