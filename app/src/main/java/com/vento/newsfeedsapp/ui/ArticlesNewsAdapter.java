package com.vento.newsfeedsapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vento.newsfeedsapp.R;
import com.vento.newsfeedsapp.databinding.ItemNewsBinding;
import com.vento.newsfeedsapp.ui.model.ArticlesItem;

import java.util.List;

public class ArticlesNewsAdapter extends RecyclerView.Adapter<ArticlesNewsAdapter.ViewHolder> {

    List<ArticlesItem> articlesList;
    OnItemClickListener onItemClickListener;

    public ArticlesNewsAdapter(List<ArticlesItem> articlesList) {
        this.articlesList = articlesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final ArticlesItem articlesItemList = articlesList.get(position);
        viewHolder.binding.tvTitleNews.setText(articlesItemList.getTitle());
        viewHolder.binding.tvAuthorNews.setText("By "+articlesItemList.getAuthor());
        viewHolder.binding.tvPublishedAtNews.setText(articlesItemList.getPublishedAt());

        if (articlesItemList.getUrlToImage().isEmpty()) {
            viewHolder.binding.ivImageNews.setImageResource(R.drawable.no_photo);
        } else{
            Picasso.get()
                    .load(articlesItemList.getUrlToImage())
                    .placeholder(R.drawable.no_photo)
                    .error(android.R.drawable.stat_notify_error)
                    .into(viewHolder.binding.ivImageNews);
        }




        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position, articlesItemList);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (articlesList == null)
            return 0;
        return articlesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemNewsBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (binding != null) {
                binding.unbind();
            }
        }
    }




    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, ArticlesItem articlesItemList);
    }

}
