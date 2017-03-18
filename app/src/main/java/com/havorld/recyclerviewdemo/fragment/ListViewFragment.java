package com.havorld.recyclerviewdemo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.havorld.recyclerviewdemo.Constant;
import com.havorld.recyclerviewdemo.adapter.MyAdapter;
import com.havorld.recyclerviewdemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListViewFragment extends BaseFragment {

    @Override
    public void initAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //LinearLayoutManager.VERTICAL 垂直滑动，LinearLayoutManager.HORIZONTAL 水平滑动
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //也可在创建LinearLayoutManager的时候直接设置滑动方向,最后一个参数 reverseLayout表示是否逆序排列展示
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter myAdapter = new MyAdapter(getActivity(), getData());
        recyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "点击了Item：" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
