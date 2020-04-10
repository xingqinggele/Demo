package com.example.demo;

import android.net.Uri;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class Adapter extends BaseQuickAdapter<Bean, BaseViewHolder> {


    public Adapter(@Nullable List<Bean> data) {
        super(R.layout.item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bean item) {
        SimpleDraweeView logoview = helper.getView(R.id.simp_img);
        helper.setText(R.id.tv_name,item.getTitle());
        logoview.setImageURI("http://news.cnjiwang.com/jlxwdt/sn/201912/W020191220715167325794.jpg");

    }
}
