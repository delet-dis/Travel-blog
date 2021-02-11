package com.delet_dis.travelblog.http;

import java.util.Objects;

public class Author {
  public String getName() {
	return name;
  }

  public String getAvatar() {
	return avatar;
  }

  public String getAvatarURL() {
	return BlogHttpClient.BASE_URL + BlogHttpClient.PATH + getAvatar();
  }

  private String name;
  private String avatar;

  @Override
  public boolean equals(Object o) {
	if (this == o) return true;
	if (o == null || getClass() != o.getClass()) return false;
	Author author = (Author) o;
	return Objects.equals(name, author.name) &&
			Objects.equals(avatar, author.avatar);
  }

  @Override
  public int hashCode() {
	return Objects.hash(name, avatar);
  }
}
