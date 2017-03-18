package com.havorld.recyclerviewdemo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.havorld.recyclerviewdemo.adapter.MyAdapter;
import com.havorld.recyclerviewdemo.adapter.WallFallsAdapter;
import com.havorld.recyclerviewdemo.base.BaseFragment;

public class WaterFallsFragment extends BaseFragment {

    @Override
    public void initAdapter() {

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        //LinearLayoutManager.VERTICAL 垂直滑动，LinearLayoutManager.HORIZONTAL 水平滑动
        staggeredGridLayoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        WallFallsAdapter wallFallsAdapter = new WallFallsAdapter(getActivity(), getWallFallsData());
        recyclerView.setAdapter(wallFallsAdapter);

        wallFallsAdapter.setOnItemClickListener(new WallFallsAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "点击了Item：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
