package com.delet_dis.travelblog.http;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Blog implements Parcelable {

  private final String id;
  private final Author author;
  private final String title;
  private final String date;
  private final String image;
  private final String description;
  private final int views;
  private final float rating;

  private static final SimpleDateFormat dateFormat =
		  new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

  protected Blog(Parcel in) {
	id = in.readString();
	title = in.readString();
	date = in.readString();
	image = in.readString();
	description = in.readString();
	views = in.readInt();
	rating = in.readFloat();
	author = in.readParcelable(Author.class.getClassLoader());
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
	dest.writeString(id);
	dest.writeString(title);
	dest.writeString(date);
	dest.writeString(image);
	dest.writeString(description);
	dest.writeInt(views);
	dest.writeFloat(rating);
	dest.writeParcelable(author, 0);
  }

  @Override
  public int describeContents() {
	return 0;
  }

  public static final Creator<Blog> CREATOR = new Creator<Blog>() {
	@Override
	public Blog createFromParcel(Parcel in) {
	  return new Blog(in);
	}

	@Override
	public Blog[] newArray(int size) {
	  return new Blog[size];
	}
  };

  public String getTitle() {
	return title;
  }

  public String getDate() {
	return date;
  }

  public Long getDateMillis() {
	try {
	  Date date = dateFormat.parse(getDate());
	  return date != null ? date.getTime() : null;
	} catch (ParseException e) {
	  e.printStackTrace();
	}

	return null;
  }

  public String getImage() {
	return image;
  }

  public String getImageURL() {
	return BlogHttpClient.BASE_URL + BlogHttpClient.PATH + getImage();
  }

  public String getDescription() {
	return description;
  }

  public int getViews() {
	return views;
  }

  public float getRating() {
	return rating;
  }

  public Author getAuthor() {
	return author;
  }

  public String getId() {
	return id;
  }

  @Override
  public boolean equals(Object o) {
	if (this == o) return true;
	if (o == null || getClass() != o.getClass()) return false;
	Blog blog = (Blog) o;
	return views == blog.views &&
			Float.compare(blog.rating, rating) == 0 &&
			Objects.equals(id, blog.id) &&
			Objects.equals(author, blog.author) &&
			Objects.equals(title, blog.title) &&
			Objects.equals(date, blog.date) &&
			Objects.equals(image, blog.image) &&
			Objects.equals(description, blog.description);
  }

  @Override
  public int hashCode() {
	return Objects.hash(id, author, title, date, image, description, views, rating);
  }
}