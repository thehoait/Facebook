package info.androidhive.listviewfeed;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import info.androidhive.listviewfeed.SlidingTab.BadgeView;
import info.androidhive.listviewfeed.SlidingTab.SlidingTabLayout;
import info.androidhive.listviewfeed.adapter.ViewpagerAdapter;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;

public class MainActivity extends SlidingFragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private String URL_FEED = "http://api.androidhive.info/feed/feed.json";
	ViewPager viewPager;
    SlidingTabLayout tabs;
    int[] icons={
			R.drawable.first_tab,
			R.drawable.second_tab,
			R.drawable.third_tab,
			R.drawable.fourth_tab,
			R.drawable.fifth_tab
	};
	CharSequence[] titles={
			"News",
			"Request",
			"Messenger",
			"Notification",
			"More"
	};
    ViewpagerAdapter pagerAdapter;
	BadgeView[] notification=new BadgeView[4];
	List<FeedItem> feedItems=new ArrayList<FeedItem>();

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createSlidingMenu();
		// These two lines not needed,
		// just to get the look of facebook (changing background color & hiding the icon)
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setTitle(titles[0]);
        viewPager=(ViewPager) findViewById(R.id.viewPager);
        tabs=(SlidingTabLayout) findViewById(R.id.tabBar);
		getData();
        pagerAdapter=new ViewpagerAdapter(getSupportFragmentManager(),icons,feedItems);
        viewPager.setAdapter(pagerAdapter);
		tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				getActionBar().setTitle(titles[position]);
				if(position!=4&&position!=0){
					notification[position].hide();
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(viewPager);
		for(int i=1;i<notification.length;i++){
			notification[i]=new BadgeView(this,tabs.getTabStrip().getChildAt(i));
			int index=(int)(100*Math.random());
			notification[i].setText("" + index);
			if(index!=0){
				notification[i].toggle();
			}

		}
	}

	public void createSlidingMenu() {
		SlidingMenu menu=getSlidingMenu();
		setTitle(R.string.app_name);
		setBehindContentView(R.layout.menu_frame);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.setMode(SlidingMenu.RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.menu_frame, new SampleListFragment()).commit();
	}

	private void parseJsonFeed(JSONObject response) {
		try {
			JSONArray feedArray = response.getJSONArray("feed");

			for (int i = 0; i < feedArray.length(); i++) {
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				FeedItem item = new FeedItem();
				item.setId(feedObj.getInt("id"));
				item.setName(feedObj.getString("name"));

				// Image might be null sometimes
				String image = feedObj.isNull("image") ? null : feedObj.getString("image");
				item.setImge(image);
				item.setStatus(feedObj.getString("status"));
				item.setProfilePic(feedObj.getString("profilePic"));
				item.setTimeStamp(feedObj.getString("timeStamp"));

				// url might be null sometimes
				String feedUrl = feedObj.isNull("url") ? null : feedObj.getString("url");
				item.setUrl(feedUrl);

				item.setCountLiked((int) (Math.random()*100));
				item.setIsLiked(false);

				feedItems.add(item);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void getData(){
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
		Cache.Entry entry = cache.get(URL_FEED);
		if (entry != null) {
			// fetch the data from cache
			try {
				String data = new String(entry.data, "UTF-8");
				try {
					parseJsonFeed(new JSONObject(data));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		} else {
			// making fresh volley request and getting json
			JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
					URL_FEED, "", new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					VolleyLog.d(TAG, "Response: " + response.toString());
					if (response != null) {
						parseJsonFeed(response);
					}
				}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					VolleyLog.d(TAG, "Error: " + error.getMessage());
				}
			});

			// Adding request to volley request queue
			AppController.getInstance().addToRequestQueue(jsonReq);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_share);
		item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				getSlidingMenu().showMenu();
				return true;
			}
		});
		return true;
	}

}
