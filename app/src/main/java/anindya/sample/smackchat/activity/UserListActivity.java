package anindya.sample.smackchat.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import anindya.sample.smackchat.R;
import anindya.sample.smackchat.adapter.UserListAdapter;
import anindya.sample.smackchat.model.Users;
import anindya.sample.smackchat.utils.MyXMPP;

public class UserListActivity extends AppCompatActivity {

    ArrayList<Users> userListArrayList = new ArrayList<Users>();
    RecyclerView mRecyclerView;
    UserListAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    RelativeLayout mLoadingProgress;
    MyXMPP xmpp = new MyXMPP(this);
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.user_list);
        mLoadingProgress = (RelativeLayout) findViewById(R.id.loading_progress);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserListAdapter(this, userListArrayList);
        mRecyclerView.setAdapter(adapter);

        if(!isLoading)startLoadUsers();

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isLoading)startLoadUsers();
            }
        });

    }

    private void startLoadUsers() {
        if(mLoadingProgress.getVisibility()==View.GONE)mLoadingProgress.setVisibility(View.VISIBLE);
        isLoading = true;
        userListArrayList = (ArrayList<Users>) xmpp.getAllUserList();
        adapter.setItem(userListArrayList);
        adapter.notifyDataSetChanged();
        isLoading = false;
        refreshLayout.setRefreshing(false);
        if(mLoadingProgress.getVisibility()==View.VISIBLE)mLoadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}