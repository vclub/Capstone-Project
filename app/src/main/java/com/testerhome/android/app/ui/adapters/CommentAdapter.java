package com.testerhome.android.app.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.testerhome.android.app.Config;
import com.testerhome.android.app.R;
import com.testerhome.android.app.models.TopicReplyEntity;
import com.testerhome.android.app.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bin Li on 2016/7/3.
 */

public class CommentAdapter extends BaseRecyclerAdapter<TopicReplyEntity> {


    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TopicReplyEntity topicReplyEntity = mItems.get(position);
        CommentViewHolder viewHolder = (CommentViewHolder)holder;

        viewHolder.userAvatar.setImageURI(Uri.parse(Config.getImageUrl(topicReplyEntity.getUser().getAvatar_url())));
        viewHolder.topicItemAuthor.setText(topicReplyEntity.getUser().getName());
        viewHolder.topicTime.setText(StringUtils.formatPublishDateTime(topicReplyEntity.getCreated_at()));
        viewHolder.topicItemBody.setText(Html.fromHtml(topicReplyEntity.getBody_html()));

        if (position == mItems.size() - 1 && mListener != null) {
            mListener.onEndOfList();
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.id_user_avatar)
        SimpleDraweeView userAvatar;
        @BindView(R.id.id_topic_item_author)
        TextView topicItemAuthor;
        @BindView(R.id.id_topic_time)
        TextView topicTime;

        @BindView(R.id.id_topic_item_content)
        TextView topicItemBody;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
