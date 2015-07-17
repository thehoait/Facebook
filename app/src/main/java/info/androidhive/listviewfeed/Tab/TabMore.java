package info.androidhive.listviewfeed.Tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.List;
import info.androidhive.listviewfeed.ProfileActivity;
import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.data.FeedItem;

/**
 * Created by HOA on 01/07/2015.
 */
public class TabMore extends Fragment {
    private List data;
    LinearLayout btn_profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_more_layout,container,false);
        data=(List<FeedItem>)getArguments().getSerializable("feedItems");
        btn_profile=(LinearLayout) view.findViewById(R.id.btn_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("data", (Serializable) data);
                startActivity(intent);
            }
        });
        return view;
    }
}
