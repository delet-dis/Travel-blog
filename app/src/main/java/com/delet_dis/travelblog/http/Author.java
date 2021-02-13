package com.delet_dis.travelblog.http;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Author implements Parcelable {

  private final String name;
  private final String avatar;

  protected Author(Parcel in) {
	name = in.readString();
	avatar = in.readString();
  }

  @Override
  public int describeContents() {
	return 0;
  }

  public String getName() {
	return name;
  }

  public String getAvatar() {
	return avatar;
  }

  public String getAvatarURL() {
	return BlogHttpClient.BASE_URL + BlogHttpClient.PATH + getAvatar();
  }

  public static final Creator<Author> CREATOR = new Creator<Author>() {
	@Override
	public Author createFromParcel(Parcel in) {
	  return new Author(in);
	}

	@Override
	public Author[] newArray(int size) {
	  return new Author[size];
	}
  };

  @Override
  public int hashCode() {
	return Objects.hash(name, avatar);
  }

  @Override
  public boolean equals(Object o) {
	if (this == o) return true;
	if (o == null || getClass() != o.getClass()) return false;
	Author author = (Author) o;
	return Objects.equals(name, author.name) &&
			Objects.equals(avatar, author.avatar);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
	dest.writeString(name);
	dest.writeString(avatar);
  }
}
