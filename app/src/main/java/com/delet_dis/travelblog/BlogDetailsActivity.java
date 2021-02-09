package com.delet_dis.travelblog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class BlogDetailsActivity extends AppCompatActivity {

  ImageView imageMain;
  ImageView imageAvatar;
  ImageView imageBack;


  TextView textTitle;
  TextView textDate;
  TextView textRating;
  TextView textViews;
  TextView textDescription;

  RatingBar ratingBar;


  public static final String IMAGE_URL =
		  "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/" +
				  "3436e16367c8ec2312a0644bebd2694d484eb047/images/sydney_image.jpg";
  public static final String AVATAR_URL =
		  "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/" +
				  "3436e16367c8ec2312a0644bebd2694d484eb047/avatars/avatar1.jpg";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_blog_details);

	findViewElements();

	imageBack.setOnClickListener(v -> finish());

	Glide.with(this)
			.load(IMAGE_URL)
			.transition(DrawableTransitionOptions.withCrossFade())
			.into(imageMain);

	Glide.with(this)
			.load(AVATAR_URL)
			.transform(new CircleCrop())
			.transition(DrawableTransitionOptions.withCrossFade())
			.into(imageAvatar);
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

	ratingBar = findViewById(R.id.ratingBar);
  }
}