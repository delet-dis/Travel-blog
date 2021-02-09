package com.delet_dis.travelblog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.delet_dis.travelblog.http.Blog;
import com.delet_dis.travelblog.http.BlogHttpClient;

import java.util.List;
import java.util.Locale;

public class BlogDetailsActivity extends AppCompatActivity {

  private ImageView imageMain;
  private ImageView imageAvatar;
  private ImageView imageBack;


  private TextView textTitle;
  private TextView textDate;
  private TextView textRating;
  private TextView textViews;
  private TextView textDescription;
  private TextView textAuthor;

  private RatingBar ratingBar;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_blog_details);

	findViewElements();

	imageBack.setOnClickListener(v -> finish());

	loadData();
  }

  private void findViewElements() {
	imageMain = findViewById(R.id.imageMain);
	imageAvatar = findViewById(R.id.imageAvatar);
	imageBack = findViewById(R.id.imageBack);

	textTitle = findViewById(R.id.textTitle);
	textDate = findViewById(R.id.textDate);
	textRating = findViewById(R.id.textRating);
	textViews = findViewById(R.id.textViews);
	textDescription = findViewById(R.id.textDescription);
	textAuthor = findViewById(R.id.textAuthor);

	ratingBar = findViewById(R.id.ratingBar);
  }

  private void loadData() {
	BlogHttpClient.INSTANCE.loadBlogArticles(new BlogHttpClient.BlogArticlesCallback() {
	  @Override
	  public void onSuccess(List<Blog> blogList) {
		runOnUiThread(() -> showData(blogList.get(0)));
	  }

	  @Override
	  public void onError() {
	  }
	});
  }

  private void showData(Blog blog) {
	textTitle.setText(blog.getTitle());
	textDate.setText(blog.getDate());
	textAuthor.setText(blog.getAuthor().getName());
	textRating.setText(String.valueOf(blog.getRating()));
	textViews.setText(String.format(Locale.getDefault(), "(%d views)", blog.getViews()));
	textDescription.setText(blog.getDescription());
	ratingBar.setRating(blog.getRating());

	Glide.with(this)
			.load(blog.getImage())
			.transition(DrawableTransitionOptions.withCrossFade())
			.into(imageMain);

	Glide.with(this)
			.load(blog.getAuthor().getAvatar())
			.transform(new CircleCrop())
			.transition(DrawableTransitionOptions.withCrossFade())
			.into(imageAvatar);
  }
}