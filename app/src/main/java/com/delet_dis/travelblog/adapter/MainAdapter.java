package com.delet_dis.travelblog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.delet_dis.travelblog.R;
import com.delet_dis.travelblog.http.Blog;

public class MainAdapter extends
		ListAdapter<Blog, MainAdapter.MainViewHolder> {

  public MainAdapter() {
	super(DIFF_CALLBACK);
  }

  @NonNull
  @Override
  public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
										   int viewType) {
	LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	View view = inflater.inflate(R.layout.item_main, parent, false);
	return new MainViewHolder(view);
  }

  @Override
  public void onBindViewHolder(MainViewHolder holder, int position) {
	holder.bindTo(getItem(position));
  }

  static class MainViewHolder extends RecyclerView.ViewHolder {
	private TextView textTitle;
	private TextView textDate;

	private ImageView imageAvatar;

	MainViewHolder(@NonNull View itemView) {
	  super(itemView);

	  textTitle = itemView.findViewById(R.id.textTitle);
	  textDate = itemView.findViewById(R.id.textDate);
	  imageAvatar = itemView.findViewById(R.id.imageAvatar);
	}

	void bindTo(Blog blog) {
	  textTitle.setText(blog.getTitle());
	  textDate.setText(blog.getDate());

	  Glide.with(itemView)
			  .load(blog.getAuthor().getAvatarURL())
			  .transform(new CircleCrop())
			  .transition(DrawableTransitionOptions.withCrossFade())
			  .into(imageAvatar);
	}
  }

  private static final DiffUtil.ItemCallback<Blog> DIFF_CALLBACK =
		  new DiffUtil.ItemCallback<Blog>() {
			@Override
			public boolean areItemsTheSame(@NonNull Blog oldData,
										   @NonNull Blog newData) {
			  return oldData.getId().equals(newData.getId());
			}

			@Override
			public boolean areContentsTheSame(@NonNull Blog oldData,
											  @NonNull Blog newData) {
			  return oldData.equals(newData);
			}
		  };
}
