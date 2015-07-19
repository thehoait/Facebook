package info.androidhive.listviewfeed.Tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import info.androidhive.listviewfeed.MainActivity;
import info.androidhive.listviewfeed.MessageActivity;
import info.androidhive.listviewfeed.R;

/**
 * Created by HOA on 18/06/2015.
 */
public class TabMessage extends Fragment {
    LinearLayout newChat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab3_layout,container,false);
        newChat = (LinearLayout) view.findViewById(R.id.mess);
        newChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getSlidingMenu().showMenu();
//                Intent intent= new Intent(getActivity(), MessageActivity.class);
//                startActivity(intent);
            }
        });
        return view;
    }
}
