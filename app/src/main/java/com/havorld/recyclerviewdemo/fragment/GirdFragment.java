package com.havorld.recyclerviewdemo.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.havorld.recyclerviewdemo.adapter.MyAdapter;
import com.havorld.recyclerviewdemo.base.BaseFragment;


public class GirdFragment extends BaseFragment {

    @Override
    public void initAdapter() {

        GridLayoutManager gridLayoutManager =  new GridLayoutManager(getActivity(),4);
        //LinearLayoutManager.VERTICAL 垂直滑动，LinearLayoutManager.HORIZONTAL 水平滑动
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        //也可在创建GridLayoutManager的时候直接设置滑动方向,最后一个参数 reverseLayout表示是否逆序排列展示
       // GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
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
