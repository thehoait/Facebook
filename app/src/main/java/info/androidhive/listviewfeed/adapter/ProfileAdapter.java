package info.androidhive.listviewfeed.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import info.androidhive.listviewfeed.FeedImageView;
import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;

/**
 * Created by HOA on 10/07/2015.
 */

public class ProfileAdapter extends BaseAdapter {
    Activity activity;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ProfileAdapter(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int position) {
        return feedItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final FeedItem item = feedItems.get(position);
        final ViewHolder holder;
        if (convertView == null){
            holder=new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_video_layout, parent, false);

            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();

            holder.name = (TextView) convertView.findViewById(R.id.name_item_video);
            holder.timestamp = (TextView) convertView
                    .findViewById(R.id.timestamp_item_video);
            holder.statusMsg = (TextView) convertView
                    .findViewById(R.id.txtStatusMsg_item_video);
            holder.url = (TextView) convertView.findViewById(R.id.txtUrl_item_video);
            holder.profilePic = (NetworkImageView) convertView
                    .findViewById(R.id.profilePic_item_video);
            holder.myVideo = (VideoView) convertView
                    .findViewById(R.id.video_view);
            holder.btn_like = (TextView) convertView.findViewById(R.id.btn_like_item_video);
            holder.countLike = (TextView) convertView.findViewById(R.id.count_like_item_video);
            holder.btn_play = (ImageButton) convertView.findViewById(R.id.btn_play);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }


        holder.name.setText(item.getName());

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.timestamp.setText(timeAgo);

        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getStatus())) {
            holder.statusMsg.setText(item.getStatus());
            holder.statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            holder.statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (item.getUrl() != null) {
            holder.url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));

            // Making url clickable
            holder.url.setMovementMethod(LinkMovementMethod.getInstance());
            holder.url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            holder.url.setVisibility(View.GONE);
        }

        // user profile pic
        holder.profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        // Video View

        holder.btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.myVideo.setVideoPath(item.getImge());
                MediaController mediaController = new MediaController(activity);
                mediaController.setAnchorView(holder.myVideo);
                holder.myVideo.setMediaController(mediaController);
                holder.btn_play.setVisibility(View.GONE);
                holder.myVideo.start();
            }
        });

        holder.btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!item.isLiked()) {
                    holder.btn_like.setText("Liked");
                    holder.btn_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dislike, 0, 0, 0);
                    item.setCountLiked(item.getCountLiked() + 1);
                    item.setIsLiked(true);
                    holder.btn_like.setTextColor(Color.parseColor("#5890ff"));
                    notifyDataSetChanged();
                } else {
                    holder.btn_like.setText("Like");
                    holder.btn_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
                    item.setCountLiked(item.getCountLiked() - 1);
                    item.setIsLiked(false);
                    holder.btn_like.setTextColor(Color.parseColor("#000000"));
                    notifyDataSetChanged();
                }
            }
        });
        if (item.isLiked()) {
            holder.btn_like.setText("Liked");
            holder.btn_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dislike, 0, 0, 0);
            holder.btn_like.setTextColor(Color.parseColor("#5890ff"));
        } else {
            holder.btn_like.setText("Like");
            holder.btn_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
            holder.btn_like.setTextColor(Color.parseColor("#000000"));
        }

        if (item.getCountLiked() == 0) {
            holder.countLike.setVisibility(View.GONE);
        } else {
            holder.countLike.setText(item.getCountLiked() + " liked");
            holder.countLike.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView name,timestamp,statusMsg,url,btn_like,countLike;
        NetworkImageView profilePic;
        VideoView myVideo;
        ImageButton btn_play;
    }

}
