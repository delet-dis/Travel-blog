package com.delet_dis.travelblog.http;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Entity
public class Blog implements Parcelable {
  @PrimaryKey
  private final int id;
  @Embedded
  private final Author author;
  private final String title;
  private final String date;
  private final String image;
  private final String description;
  private final int views;
  private final float rating;

  protected Blog(Parcel in) {
	id = in.readInt();
	title = in.readString();
	date = in.readString();
	image = in.readString();
	description = in.readString();
	views = in.readInt();
	rating = in.readFloat();
	author = in.readParcelable(Author.class.getClassLoader());
  }

  public Blog(int id, Author author, String title, String date, String image,
			  String description, int views, float rating) { // 4
	this.id = id;
	this.author = author;
	this.title = title;
	this.date = date;
	this.image = image;
	this.description = description;
	this.views = views;
	this.rating = rating;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
	dest.writeInt(id);
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


  public String getTitle() {
	return title;
  }

  public String getDate() {
	return date;
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

  public int getId() {
	return id;
  }


  private static final SimpleDateFormat dateFormat =
		  new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

  public Long getDateMillis() {
	try {
	  Date date = dateFormat.parse(getDate());
	  return date != null ? date.getTime() : null;
	} catch (ParseException e) {
	  e.printStackTrace();
	}

	return null;
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