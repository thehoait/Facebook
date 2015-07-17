package info.androidhive.listviewfeed.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;
import java.util.Random;

import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;

/**
 * Created by HOA on 26/06/2015.
 */
public class AddFriendAdapter extends BaseAdapter {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    List<FeedItem> feedItems;
    Activity activity;

    public AddFriendAdapter(Activity activity, List feedItems) {
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
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        final ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(activity).inflate(R.layout.item_list_add_friend,parent,false);
            holder.icProfile=(NetworkImageView) convertView.findViewById(R.id.icProfile);
            holder.name=(TextView) convertView.findViewById(R.id.txtName);
            holder.banChung=(TextView) convertView.findViewById(R.id.txtBanChung);
            holder.btnBanBe=(Button) convertView.findViewById(R.id.btnBanBe);
            holder.btnChapNhan=(Button) convertView.findViewById(R.id.btnChapNhan);
            holder.btnXoa=(Button) convertView.findViewById(R.id.btnXoa);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        holder.name.setText(feedItems.get(position).getName());
        holder.banChung.setText((new Random().nextInt(100))+" Bạn Chung");
        holder.icProfile.setImageUrl(feedItems.get(position).getProfilePic(), imageLoader);
        holder.btnChapNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnChapNhan.setVisibility(View.GONE);
                holder.btnXoa.setVisibility(View.GONE);
                holder.btnBanBe.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedItems.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    static class ViewHolder{
        NetworkImageView icProfile;
        TextView name;
        TextView banChung;
        Button btnChapNhan;
        Button btnXoa;
        Button btnBanBe;
    }
}
