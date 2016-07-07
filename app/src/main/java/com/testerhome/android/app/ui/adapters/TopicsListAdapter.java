package com.testerhome.android.app.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.testerhome.android.app.R;
import com.testerhome.android.app.models.TopicEntity;
import com.testerhome.android.app.ui.MainActivity;
import com.testerhome.android.app.ui.TopicDetailActivity;
import com.testerhome.android.app.util.ImageUrlConvert;
import com.testerhome.android.app.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bin Li on 2016/6/14.
 */

public class TopicsListAdapter extends BaseRecyclerAdapter<TopicEntity> {


    public TopicsListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_topic, parent, false);
        return new TopicItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TopicItemViewHolder holder = (TopicItemViewHolder) viewHolder;

        TopicEntity topic = getItems().get(position);

        holder.topicUserAvatar.setImageURI(Uri.parse(ImageUrlConvert.getImageUrl(topic.getUser().getAvatar_url())));
        holder.textViewTopicTitle.setText(topic.getTitle());
        holder.topicUsername.setText(TextUtils.isEmpty(topic.getUser().getName()) ? topic.getUser().getLogin() : topic.getUser().getName());

        holder.topicPublishDate.setText(StringUtils.formatPublishDateTime(topic.getCreated_at()));

        holder.topicName.setText(topic.getNode_name());

        holder.topicItem.setTag(topic);
        holder.topicItem.setOnClickListener(v -> {
            TopicEntity info = (TopicEntity) v.getTag();
            Intent intent = new Intent(mContext, TopicDetailActivity.class);
            intent.putExtra("topicInfo", info);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity)mContext, v, "avatar");
            mContext.startActivity(intent, optionsCompat.toBundle());
        });

        if (position == mItems.size() - 1 && mListener != null) {
            mListener.onEndOfList();
        }
    }

    public class TopicItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sdv_topic_user_avatar)
        SimpleDraweeView topicUserAvatar;

        @BindView(R.id.tv_topic_title)
        TextView textViewTopicTitle;

        @BindView(R.id.tv_topic_username)
        TextView topicUsername;

        @BindView(R.id.tv_topic_publish_date)
        TextView topicPublishDate;

        @BindView(R.id.tv_topic_name)
        TextView topicName;

        @BindView(R.id.tv_topic_replies_count)
        TextView topicRepliesCount;

        @BindView(R.id.rl_topic_item)
        View topicItem;

        public TopicItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
