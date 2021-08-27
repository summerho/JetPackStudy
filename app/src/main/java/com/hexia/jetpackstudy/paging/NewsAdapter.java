package com.hexia.jetpackstudy.paging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hexia.jetpackstudy.R;
import com.hexia.jetpackstudy.model.NewsBean;
import com.squareup.picasso.Picasso;

public class NewsAdapter extends PagedListAdapter<NewsBean, NewsAdapter.NewsViewHolder> {

    private Context mContext;

    public NewsAdapter(Context context) {
        super(DIFF_CALLBACK);
        mContext = context;
    }

    private static final DiffUtil.ItemCallback<NewsBean> DIFF_CALLBACK = new DiffUtil.ItemCallback<NewsBean>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewsBean oldItem, @NonNull NewsBean newItem) {
            return oldItem.postid.equals(newItem.postid);
        }

        @Override
        public boolean areContentsTheSame(@NonNull NewsBean oldItem, @NonNull NewsBean newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news_normal, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsBean bean = getItem(position);
        if (bean != null) {
            Picasso.get().load(bean.imgsrc)
                    .placeholder(R.mipmap.defaultbg)
                    .error(R.mipmap.defaultbg)
                    .into(holder.picIv);
            holder.titleTv.setText(bean.title);
            holder.digestTv.setText(bean.digest);
            holder.pubTimeTv.setText(bean.ptime);
            holder.sourceTv.setText(bean.source);
        }
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTv;
        public TextView digestTv;
        public ImageView picIv;
        public TextView pubTimeTv;
        public TextView sourceTv;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.item_news_tv_title);
            digestTv = itemView.findViewById(R.id.item_news_tv_digest);
            picIv = itemView.findViewById(R.id.item_news_tv_img);
            pubTimeTv = itemView.findViewById(R.id.item_news_tv_time);
            sourceTv = itemView.findViewById(R.id.item_news_tv_source);
        }
    }
}
