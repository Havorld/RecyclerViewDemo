package com.havorld.recyclerviewdemo;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.havorld.recyclerviewdemo.fragment.GirdFragment;
import com.havorld.recyclerviewdemo.fragment.ListViewFragment;
import com.havorld.recyclerviewdemo.fragment.WaterFallsFragment;

/**
*	博客地址：http://blog.csdn.net/xiaohao0724/article/details/62888275
*/
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup radioGroup = $(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new ListViewFragment()).commit();
        radioGroup.check(R.id.listView);

    }


    protected <T extends View> T $(int id) {

        //return返回view时,加上泛型T
        return (T) findViewById(id);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        switch (i) {
            case R.id.listView:
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new ListViewFragment()).commit();
                break;
            case R.id.girdView:
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new GirdFragment()).commit();
                break;
            case R.id.wallFalls:
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new WaterFallsFragment()).commit();
                break;
            default:
                break;
        }
    }
}
