package com.delet_dis.travelblog.http;

public class Blog {

  public String getId() {
	return id;
  }

  public Author getAuthor() {
	return author;
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

  private String id;
  private Author author;
  private String title;
  private String date;
  private String image;
  private String description;
  private int views;
  private float rating;
}
