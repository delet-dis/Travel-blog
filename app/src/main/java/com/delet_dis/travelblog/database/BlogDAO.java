package com.delet_dis.travelblog.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.delet_dis.travelblog.http.Blog;

import java.util.List;

@Dao
public interface BlogDAO {
  @Query("SELECT * FROM blog")
  List<Blog> getAll();

  @Insert
  void insertAll(List<Blog> blogList);

  @Query("DELETE FROM blog")
  void deleteAll();
}
