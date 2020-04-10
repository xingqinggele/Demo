package com.example.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends baseactivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private String downurl = "http://47.92.151.250:8081/profile/download/GZZM_SJV1.1.3.apk";
    private DownloadBuilder builder;
    private Button upa_btn;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe_refresh_layout;
    private Adapter mAdapter;
    private List<Bean> mData = new ArrayList<>();
    private Bean bean;
    private int mCount = 0;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        upa_btn = findViewById(R.id.upa_btn);
        upa_btn.setOnClickListener(this);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        recyclerView = findViewById(R.id.listview);
        img = findViewById(R.id.img);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.renwu_banner_qgl, null);
        img.setImageBitmap(ImageUtil.drawTextToRightBottom(this, bitmap,"青格乐",16, Color.parseColor("#FF0000"),15,10));

    }

    @Override
    protected void initData() {
        swipe_refresh_layout.setOnRefreshListener(this);
        mAdapter = new Adapter(mData);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_LONG).show();
            }
        });
        getData();


    }

    private void sendRequest() {
        builder = AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestUrl("https://www.baidu.com")
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(DownloadBuilder downloadBuilder, String result) {
                        return crateUIData();
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
                        Toast.makeText(MainActivity.this, "request failed", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setDownloadAPKPath(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath());
        builder.setShowDownloadingDialog(false);
        builder.setShowNotification(true);
        builder.executeMission(this);
    }

    private UIData crateUIData() {
        UIData uiData = UIData.create();
        uiData.setTitle(getAppName(this) + "版本 1.1.0 强势来袭");
        uiData.setDownloadUrl(downurl);
        uiData.setContent("1.1111111111\n2.2222222222\n3.3333333333");
        return uiData;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //合适的地方关闭
        AllenVersionChecker.getInstance().cancelAllMission(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upa_btn:
                Log.d("版本号", getLocalVersion(this) + "");
                Log.d("版本名称", getLocalVersionName(this) + "");
                sendRequest();
                break;
        }
    }

    @Override
    public void onRefresh() {
        swipe_refresh_layout.setRefreshing(true);
        setRefresh();
        getData();
    }

    private void setRefresh() {
        mData.clear();
        mCount = 1;
    }

    @Override
    public void onLoadMoreRequested() {
        mCount = mCount + 1;
        getData();
    }

    private void getData() {
        swipe_refresh_layout.setRefreshing(false);
        List<Bean> memberList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            bean = new Bean();
            bean.setTitle("我是第" + i + "条标题");
            memberList.add(bean);
        }
        mData.addAll(memberList);
        if (memberList.size() < 20) {
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.loadMoreComplete();
        }
        mAdapter.notifyDataSetChanged();
    }
}
