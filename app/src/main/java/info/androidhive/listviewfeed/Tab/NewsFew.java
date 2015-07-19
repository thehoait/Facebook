package info.androidhive.listviewfeed.Tab;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import java.util.List;
import info.androidhive.listviewfeed.PullToRefreshListView;
import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.adapter.FeedListAdapter;


/**
 * Created by HOA on 18/06/2015.
 */
public class NewsFew extends Fragment {
    private PullToRefreshListView listView;
    private FeedListAdapter listAdapter;
    private List feedItems;
    private LinearLayout mQuickReturnView;
    private int mQuickReturnHeight;
    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private int mState = STATE_ONSCREEN;
    private int mScrollY;
    private int mMinRawY = 0;

    private TranslateAnimation anim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newfeed_layout, container, false);
        listView = (PullToRefreshListView) view.findViewById(R.id.list);
        feedItems=(List) getArguments().getSerializable("feedItems");
        listAdapter = new FeedListAdapter(getActivity(), feedItems);
        mQuickReturnView = (LinearLayout) view.findViewById(R.id.footer); // lay gia tri footer
        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetDataTask().execute();
            }
        });
        createFooter();
        listView.setAdapter(listAdapter);
        return view;
    }

    private class GetDataTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }

    public void createFooter() {
        listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mQuickReturnHeight = mQuickReturnView.getHeight();
                listView.computeScrollY();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @SuppressLint("NewApi")
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                mScrollY = 0;
                int translationY = 0;

                if (listView.scrollYIsComputed()) {
                    mScrollY = listView.getComputedScrollY();
                }

                int rawY = mScrollY;

                switch (mState) {
                    case STATE_OFFSCREEN:
                        if (rawY >= mMinRawY) {
                            mMinRawY = rawY;
                        } else {
                            mState = STATE_RETURNING;
                        }
                        translationY = rawY;
                        break;

                    case STATE_ONSCREEN:
                        if (rawY > mQuickReturnHeight) {
                            mState = STATE_OFFSCREEN;
                            mMinRawY = rawY;
                        }
                        translationY = rawY;
                        break;

                    case STATE_RETURNING:

                        translationY = (rawY - mMinRawY) + mQuickReturnHeight;

                        System.out.println(translationY);
                        if (translationY < 0) {
                            translationY = 0;
                            mMinRawY = rawY + mQuickReturnHeight;
                        }

                        if (rawY == 0) {
                            mState = STATE_ONSCREEN;
                            translationY = 0;
                        }

                        if (translationY > mQuickReturnHeight) {
                            mState = STATE_OFFSCREEN;
                            mMinRawY = rawY;
                        }
                        break;
                }


                if ( Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                    anim = new TranslateAnimation(0, 0, translationY,
                            translationY);
                    anim.setFillAfter(true);
                    anim.setDuration(0);
                    mQuickReturnView.startAnimation(anim);
                } else {
                    mQuickReturnView.setTranslationY(translationY);
                }

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });
    }

}
