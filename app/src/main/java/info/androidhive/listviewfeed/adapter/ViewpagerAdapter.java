package info.androidhive.listviewfeed.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.listviewfeed.Tab.AddFriend;
import info.androidhive.listviewfeed.Tab.NewsFew;
import info.androidhive.listviewfeed.Tab.TabMessage;
import info.androidhive.listviewfeed.Tab.Tab4;
import info.androidhive.listviewfeed.Tab.TabMore;
import info.androidhive.listviewfeed.data.FeedItem;

/**
 * Created by HOA on 18/06/2015.
 */
public class ViewpagerAdapter extends FragmentStatePagerAdapter  {
    int[] icons;
    List<FeedItem> feedItems=new ArrayList<FeedItem>();
    public ViewpagerAdapter(FragmentManager fm,int[] icons,List<FeedItem> feedItems) {
        super(fm);
        this.icons=icons;
        this.feedItems=feedItems;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("feedItems", (Serializable) feedItems);
        switch (position){
            case 0:
                NewsFew newsFew=new NewsFew();
                newsFew.setArguments(bundle);
                return newsFew;
            case 1:
                AddFriend addFriend=new AddFriend();
                addFriend.setArguments(bundle);
                return addFriend;
            case 2:
                return new TabMessage();
            case 3:
                return new Tab4();
            case 4:
                TabMore tabMore=new TabMore();
                tabMore.setArguments(bundle);
                return tabMore;
        }
        return null;
    }

    @Override
    public int getCount() {
        return icons.length;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        Drawable image= context.getResources().getDrawable(titles[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        SpannableString sb = new SpannableString(" ");
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return sb;
//    }
    public int getDrawableId(int positon){
        return icons[positon];
    }
}
