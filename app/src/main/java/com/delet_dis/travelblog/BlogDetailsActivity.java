package com.delet_dis.travelblog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.delet_dis.travelblog.helpers.ConstantsHelper;
import com.delet_dis.travelblog.http.Blog;

import java.util.Locale;

public class BlogDetailsActivity extends AppCompatActivity {


  private TextView textTitle;
  private TextView textDate;
  private TextView textAuthor;
  private TextView textRating;
  private TextView textDescription;
  private TextView textViews;
  private RatingBar ratingBar;
  private ImageView imageAvatar;
  private ImageView imageMain;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_blog_details);

	setWindowTransparency();

	findViewElements();

	setImageBackOnClickListener();

	showData(getIntent().getExtras().getParcelable(ConstantsHelper.EXTRAS_BLOG));
  }

  private void setImageBackOnClickListener() {
	ImageView imageBack = findViewById(R.id.imageBack);
	imageBack.setOnClickListener(v -> finish());
  }

  private void findViewElements() {
	imageMain = findViewById(R.id.imageMain);
	imageAvatar = findViewById(R.id.imageAvatar);

	textTitle = findViewById(R.id.textTitle);
	textDate = findViewById(R.id.textDate);
	textAuthor = findViewById(R.id.textAuthor);
	textRating = findViewById(R.id.textRating);
	textViews = findViewById(R.id.textViews);
	textDescription = findViewById(R.id.textDescription);
	ratingBar = findViewById(R.id.ratingBar);
	progressBar = findViewById(R.id.progressBar);
  }

  private void setWindowTransparency() {
	Window window = getWindow();
	window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
  }

  private void showData(Blog blog) {
	progressBar.setVisibility(View.GONE);
	textTitle.setText(blog.getTitle());
	textDate.setText(blog.getDate());
	textAuthor.setText(blog.getAuthor().getName());
	textRating.setText(String.valueOf(blog.getRating()));
	textViews.setText(String.format(Locale.getDefault(), "(%d views)", blog.getViews()));
	textDescription.setText(HtmlCompat.fromHtml(blog.getDescription(), HtmlCompat.FROM_HTML_MODE_COMPACT));
	ratingBar.setRating(blog.getRating());
	ratingBar.setVisibility(View.VISIBLE);

	Glide.with(this)
			.load(blog.getImageURL())
			.transition(DrawableTransitionOptions.withCrossFade())
			.into(imageMain);

	Glide.with(this)
			.load(blog.getAuthor().getAvatarURL())
			.transform(new CircleCrop())
			.transition(DrawableTransitionOptions.withCrossFade())
			.into(imageAvatar);
  }

  public static void startBlogDetailsActivity(Activity activity, Blog blog) {
	Intent intent = new Intent(activity, BlogDetailsActivity.class);
	intent.putExtra(ConstantsHelper.EXTRAS_BLOG, blog);
	activity.startActivity(intent);
  }

}