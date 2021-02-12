package com.delet_dis.travelblog.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BlogHttpClient {
  public static final BlogHttpClient INSTANCE = new BlogHttpClient();

  public static final String BASE_URL =
		  "https://raw.githubusercontent.com/delet-dis/travel-blog-resources/";
  public static final String PATH =
		  "113a748aed8a4c17b2b77788ae4ecdd1675f396b";
  private static final String BLOG_ARTICLES_URL =
		  BASE_URL + PATH + "/blog_articles.json";

  private final Executor executor;
  private final OkHttpClient client;
  private final Gson gson;

  private BlogHttpClient() {
	executor = Executors.newFixedThreadPool(4);
	client = new OkHttpClient();
	gson = new Gson();
  }

  public void loadBlogArticles(BlogArticlesCallback callback) {
	Request request = new Request.Builder()
			.get()
			.url(BLOG_ARTICLES_URL)
			.build();

	executor.execute(() -> {
	  try {
		Response response = client.newCall(request).execute();
		ResponseBody responseBody = response.body();
		if (responseBody != null) {
		  String json = responseBody.string();
		  BlogData blogData = gson.fromJson(json, BlogData.class);
		  if (blogData != null) {
			callback.onSuccess(blogData.getData());
			return;
		  }
		}
	  } catch (IOException e) {
		Log.e("BlogHttpClient", "Error loading blog articles", e);
	  }
	  callback.onError();
	});
  }

  public interface BlogArticlesCallback {
	void onSuccess(List<Blog> blogList);

	void onError();
  }
}
