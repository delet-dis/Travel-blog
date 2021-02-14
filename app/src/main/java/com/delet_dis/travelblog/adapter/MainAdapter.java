package com.delet_dis.travelblog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.delet_dis.travelblog.R;
import com.delet_dis.travelblog.http.Blog;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends ListAdapter<Blog, MainAdapter.MainViewHolder> {

  @NonNull
  @Override
  public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
	LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	View view = inflater.inflate(R.layout.item_main, parent, false);

	return new MainViewHolder(view, onItemClickListener);
  }

  @Override
  public void onBindViewHolder(MainViewHolder holder, int position) {
	holder.bindTo(getItem(position));
  }

  public interface OnItemClickListener {
	void onItemClicked(Blog blog);
  }

  private final OnItemClickListener onItemClickListener;

  public MainAdapter(OnItemClickListener clickListener) {
	super(DIFF_CALLBACK);

	this.onItemClickListener = clickListener;
  }

  public void sortByTitle() {
	List<Blog> currentList = new ArrayList<>(getCurrentList());
	currentList.sort((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));

	submitList(currentList);
  }

  public void sortByDate() {
	List<Blog> currentList = new ArrayList<>(getCurrentList());
	currentList.sort((o1, o2) -> o2.getDateMillis().compareTo(o1.getDateMillis()));

	submitList(currentList);
  }

  private static final DiffUtil.ItemCallback<Blog> DIFF_CALLBACK = new DiffUtil.ItemCallback<Blog>() {
	@Override
	public boolean areItemsTheSame(@NonNull Blog oldData,
								   @NonNull Blog newData) {
	  return oldData.getId() == newData.getId();
	}

	@Override
	public boolean areContentsTheSame(@NonNull Blog oldData,
									  @NonNull Blog newData) {
	  return oldData.equals(newData);
	}


  };

  static class MainViewHolder extends RecyclerView.ViewHolder {
	private final TextView textTitle;
	private final TextView textDate;

	private final ImageView imageAvatar;

	private Blog blog;


	MainViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
	  super(itemView);

	  itemView.setOnClickListener(v -> onItemClickListener.onItemClicked(blog));
	  textTitle = itemView.findViewById(R.id.textTitle);
	  textDate = itemView.findViewById(R.id.textDate);
	  imageAvatar = itemView.findViewById(R.id.imageAvatar);
	}

	void bindTo(Blog blog) {
	  this.blog = blog;

	  textTitle.setText(blog.getTitle());
	  textDate.setText(blog.getDate());

	  Glide.with(itemView)
			  .load(blog.getAuthor().getAvatarURL())
			  .transform(new CircleCrop())
			  .transition(DrawableTransitionOptions.withCrossFade())
			  .into(imageAvatar);
	}

  }

  private List<Blog> originalList = new ArrayList<>();

  public void setData(@Nullable List<Blog> list) {
	originalList = list;
	super.submitList(list);
  }

  public void filter(String query) {
	List<Blog> filteredList = new ArrayList<>();
	for (Blog blog : originalList) {
	  if (blog.getTitle().toLowerCase().contains(query.toLowerCase())) {
		filteredList.add(blog);
	  }
	}
	submitList(filteredList);
  }
}
