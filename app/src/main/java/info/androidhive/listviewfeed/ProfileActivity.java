package info.androidhive.listviewfeed;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.listviewfeed.adapter.FeedListAdapter;
import info.androidhive.listviewfeed.adapter.ProfileAdapter;
import info.androidhive.listviewfeed.data.FeedItem;
import info.androidhive.listviewfeed.data.VideoModel;

/**
 * Created by HOA on 01/07/2015.
 */
public class ProfileActivity extends Activity {
    ListView listView;
    ProfileAdapter profileAdapter;
    String url="https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
    String path="/sdcard/Download/big_buck_bunny.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        getActionBar().hide();
        Intent intent=getIntent();
        List<FeedItem> data= (List) intent.getSerializableExtra("data");
        for(int i=0;i<data.size();i++){
            data.get(i).setImge(path);
        }
        listView=(ListView) findViewById(R.id.lvProfile);
        profileAdapter=new ProfileAdapter(ProfileActivity.this,data);
        View header=getLayoutInflater().inflate(R.layout.header_profile1,null);
        listView.addHeaderView(header);
        listView.setAdapter(profileAdapter);
    }
}
