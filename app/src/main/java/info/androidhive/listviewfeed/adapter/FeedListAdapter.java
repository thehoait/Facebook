package info.androidhive.listviewfeed.adapter;

import info.androidhive.listviewfeed.FeedImageView;
import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.TouchImageView;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

public class FeedListAdapter extends BaseAdapter{
	private Activity activity;
	private LayoutInflater inflater;
	private List<FeedItem> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	Dialog dialog;

	public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
		this.activity = activity;
		this.feedItems = feedItems;
	}

	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public Object getItem(int location) {
		return feedItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.feed_item, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView timestamp = (TextView) convertView
				.findViewById(R.id.timestamp);
		TextView statusMsg = (TextView) convertView
				.findViewById(R.id.txtStatusMsg);
		TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
		NetworkImageView profilePic = (NetworkImageView) convertView
				.findViewById(R.id.profilePic);
		final FeedImageView feedImageView = (FeedImageView) convertView
				.findViewById(R.id.feedImage1);

		final FeedItem item = feedItems.get(position);

		name.setText(item.getName());

		// Converting timestamp into x ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		timestamp.setText(timeAgo);

		// Chcek for empty status message
		if (!TextUtils.isEmpty(item.getStatus())) {
			statusMsg.setText(item.getStatus());
			statusMsg.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			statusMsg.setVisibility(View.GONE);
		}

		// Checking for null feed url
		if (item.getUrl() != null) {
			url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
					+ item.getUrl() + "</a> "));

			// Making url clickable
			url.setMovementMethod(LinkMovementMethod.getInstance());
			url.setVisibility(View.VISIBLE);
		} else {
			// url is null, remove from the view
			url.setVisibility(View.GONE);
		}

		// user profile pic
		profilePic.setImageUrl(item.getProfilePic(), imageLoader);

		// Feed image
		if (item.getImge() != null) {
			feedImageView.setImageUrl(item.getImge(), imageLoader);
			feedImageView.setVisibility(View.VISIBLE);
			feedImageView
					.setResponseObserver(new FeedImageView.ResponseObserver() {
						@Override
						public void onError() {
						}

						@Override
						public void onSuccess() {
						}
					});
		} else {
			feedImageView.setVisibility(View.GONE);
		}

		final TextView btn_like = (TextView) convertView.findViewById(R.id.btn_like);
		btn_like.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!item.isLiked()) {
					btn_like.setText("Liked");
					btn_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dislike, 0, 0, 0);
					item.setCountLiked(item.getCountLiked() + 1);
					item.setIsLiked(true);
					btn_like.setTextColor(Color.parseColor("#5890ff"));
					notifyDataSetChanged();
				} else {
					btn_like.setText("Like");
					btn_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
					item.setCountLiked(item.getCountLiked() - 1);
					item.setIsLiked(false);
					btn_like.setTextColor(Color.parseColor("#000000"));
					notifyDataSetChanged();
				}
			}
		});
		if (item.isLiked()) {
			btn_like.setText("Liked");
			btn_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dislike, 0, 0, 0);
			btn_like.setTextColor(Color.parseColor("#5890ff"));
		} else {
			btn_like.setText("Like");
			btn_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
			btn_like.setTextColor(Color.parseColor("#000000"));
		}

		TextView countLike = (TextView) convertView.findViewById(R.id.count_like);
		if (item.getCountLiked() == 0) {
			countLike.setVisibility(View.GONE);
		} else {
			countLike.setText(item.getCountLiked() + " liked");
			countLike.setVisibility(View.VISIBLE);
		}
		feedImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog=new Dialog(activity,R.style.FullHeightDialog);
				dialog.setContentView(R.layout.expand_image);
				TouchImageView imageView=(TouchImageView) dialog.findViewById(R.id.expand_image);
				Picasso.with(activity).load(item.getImge()).into(imageView);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
				imageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});

		return convertView;
	}
}
