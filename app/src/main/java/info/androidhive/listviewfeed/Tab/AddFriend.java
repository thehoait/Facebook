package info.androidhive.listviewfeed.Tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;
import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.adapter.AddFriendAdapter;

/**
 * Created by HOA on 18/06/2015.
 */
public class AddFriend extends Fragment {
    ListView lvAddFriend;
    List feedItems;
    AddFriendAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.notify_add_friend, container, false);
        feedItems=(List) getArguments().getSerializable("feedItems");
        lvAddFriend=(ListView) view.findViewById(R.id.listaddfriend);
        adapter=new AddFriendAdapter(getActivity(),feedItems);
        lvAddFriend.setAdapter(adapter);
        return view;
    }
}
