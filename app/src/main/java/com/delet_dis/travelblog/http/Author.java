package com.delet_dis.travelblog.http;

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


}
