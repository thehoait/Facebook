package info.androidhive.listviewfeed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class SampleListFragment extends ListFragment {
	String[] name = {
			"Hugh Helbert",
			"Stevev Seo",
			"Dwight Pera",
			"Prancis cripriano",
			"Walter Chavis",
			"Wilbert Rowen",
			"Andrea Gruber",
			"Dario Bennington",
			"Fransico Chill",
			"Hugh Helbert",
			"Stevev Seo",
			"Dwight Pera",
			"Prancis cripriano",
			"Walter Chavis",
			"Wilbert Rowen",
			"Andrea Gruber",
			"Dario Bennington",
			"Fransico Chill"
	};
	int[] profile = {
			R.drawable.ic_profile1,
			R.drawable.ic_profile2,
			R.drawable.ic_profile3,
			R.drawable.ic_profile4,
			R.drawable.ic_profile5,
			R.drawable.ic_profile6,
			R.drawable.ic_profile7,
			R.drawable.ic_profile8,
			R.drawable.ic_profile9,
			R.drawable.ic_profile1,
			R.drawable.ic_profile2,
			R.drawable.ic_profile3,
			R.drawable.ic_profile4,
			R.drawable.ic_profile5,
			R.drawable.ic_profile6,
			R.drawable.ic_profile7,
			R.drawable.ic_profile8,
			R.drawable.ic_profile9
	};
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.friend_online_list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		for (int i = 0; i < name.length; i++) {
			adapter.add(new SampleItem(name[i], profile[i]));
		}
		setListAdapter(adapter);
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent= new Intent(getActivity(), MessageActivity.class);
				intent.putExtra("friendName",((SampleItem)getListView().getItemAtPosition(position)).tag);
				startActivity(intent);
			}
		});
	}

	private class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}
}
