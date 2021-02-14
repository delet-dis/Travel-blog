package com.delet_dis.travelblog;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.delet_dis.travelblog.adapter.MainAdapter;
import com.delet_dis.travelblog.http.Blog;
import com.delet_dis.travelblog.repository.BlogRepository;
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

  private MaterialToolbar toolbar;

  private BlogRepository repository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	toolbar = findViewById(R.id.toolbar);

	repository = new BlogRepository(getApplicationContext());

	setupToolbarOnMenuItemClickListener();

	setupToolbarSearch();

	setupRecyclerView();

	setupRefreshLayout();

	loadDataFromDatabase();
	loadDataFromNetwork();

	sortData();
  }

  private void setupRefreshLayout() {
	refreshLayout = findViewById(R.id.refreshLayout);
	refreshLayout.setOnRefreshListener(this::loadDataFromNetwork);
  }

  private void setupRecyclerView() {
	mainAdapter = new MainAdapter(blog ->
			BlogDetailsActivity.startBlogDetailsActivity(this, blog));

	RecyclerView recyclerView = findViewById(R.id.recyclerView);
	recyclerView.setLayoutManager(new LinearLayoutManager(this));
	recyclerView.setAdapter(mainAdapter);
  }

  private void setupToolbarOnMenuItemClickListener() {
	toolbar.setOnMenuItemClickListener(item -> {
	  if (item.getItemId() == R.id.sort) {
		onSortClicked();
	  }
	  return false;
	});
  }

  private void setupToolbarSearch() {
	MenuItem searchItem = toolbar.getMenu().findItem(R.id.search);
	SearchView searchView = (SearchView) searchItem.getActionView();
	searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
	  @Override
	  public boolean onQueryTextSubmit(String query) {
		return false;
	  }

	  @Override
	  public boolean onQueryTextChange(String newText) {
		mainAdapter.filter(newText);
		return true;
	  }
	});
  }

  private void loadDataFromDatabase() {
	repository.loadDataFromDatabase(blogList -> runOnUiThread(() -> {
	  mainAdapter.setData(blogList);
	  sortData();
	}));
  }

  private void loadDataFromNetwork() {
	refreshLayout.setRefreshing(true);

	repository.loadDataFromNetwork(new BlogRepository.DataFromNetworkCallback() {
	  @Override
	  public void onSuccess(List<Blog> blogList) {
		runOnUiThread(() -> {
		  mainAdapter.setData(blogList);
		  refreshLayout.setRefreshing(false);
		});
	  }

	  @Override
	  public void onError() {
		runOnUiThread(() -> {
		  refreshLayout.setRefreshing(false);
		  showErrorSnackbar(getApplicationContext(), findViewById(android.R.id.content));
		});
	  }
	});
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


  private void showErrorSnackbar(Context context, View rootView) {
	Snackbar snackbar = Snackbar.make(rootView, R.string.snackbarLoadingError, Snackbar.LENGTH_INDEFINITE);
	snackbar.setActionTextColor(context.getColor(R.color.orange500));
	snackbar.setAction(R.string.snackbarRetryText, v -> {
	  loadDataFromNetwork();
	  snackbar.dismiss();
	});
	snackbar.show();
  }

}

