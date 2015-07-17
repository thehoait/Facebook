package info.androidhive.listviewfeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;

import info.androidhive.listviewfeed.app.AppController;

/**
 * Created by HOA on 08/07/2015.
 */
public class ViewImage extends Activity {
    ImageLoader imageLoader= AppController.getInstance().getImageLoader();
    FeedImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expand_image);
        imageView=(FeedImageView) findViewById(R.id.expand_image);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        imageView.setImageUrl(bundle.getString("image"),imageLoader);
    }
}
