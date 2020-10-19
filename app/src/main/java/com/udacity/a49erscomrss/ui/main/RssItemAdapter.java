package com.udacity.a49erscomrss.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.a49erscomrss.R;
import com.udacity.a49erscomrss.databinding.FragmentArticlesListItemBinding;
import com.udacity.a49erscomrss.model.RssItem;

import java.util.List;

public class RssItemAdapter extends RecyclerView.Adapter<RssItemAdapter.ViewHolder> {

    private final List<RssItem> mRssItems;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public RssItemAdapter(List<RssItem> items, ListItemClickListener onClickListener) {
        mRssItems = items;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_articles_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (mRssItems != null) ? mRssItems.size() : 0;
    }

    public void addRssItemData(List<RssItem> rssItems) {
        mRssItems.clear();
        mRssItems.addAll(rssItems);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final FragmentArticlesListItemBinding fragmentArticlesListItemBinding;

        public ViewHolder(View view) {
            super(view);
            fragmentArticlesListItemBinding = DataBindingUtil.bind(view);
            if (fragmentArticlesListItemBinding != null) {
                fragmentArticlesListItemBinding.cvRssItem.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(adapterPosition);
        }

        public void bind(int listIndex) {
            if (listIndex <= mRssItems.size()) {
                RssItem rssItem = mRssItems.get(listIndex);
                fragmentArticlesListItemBinding.tvTitle.setText(rssItem.getTitle());
                fragmentArticlesListItemBinding.tvDescription.setText(rssItem.getDescription());
            }
        }
    }
}