package com.havorld.recyclerviewdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.havorld.recyclerviewdemo.Constant;
import com.havorld.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BaseFragment extends Fragment {

    protected RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        initAdapter();
        return view;
    }

    public abstract void initAdapter();

    protected List<String> getData() {

        List<String> list = new ArrayList<String>();
        String[] data = Constant.data;
        for (int i = 0; i < 50; i++) {
            int random = new Random().nextInt(data.length);
            list.add(data[random]);
        }
        return list;
    }

    protected List<String> getWallFallsData() {

        List<String> list = new ArrayList<String>();
        String[] data = Constant.data;
        StringBuilder builder;
        for (int i = 0; i < 50; i++) {
            builder = new StringBuilder();
            int random = new Random().nextInt(data.length);
            for (int j = 0; j < new Random().nextInt(15)+5; j++) {
                builder.append(data[random] + "\n");
            }
            list.add(builder.toString());
        }
        return list;
    }
}
