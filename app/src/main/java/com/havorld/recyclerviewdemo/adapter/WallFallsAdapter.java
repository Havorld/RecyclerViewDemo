package com.havorld.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.havorld.recyclerviewdemo.R;

import java.util.List;

public class WallFallsAdapter extends  RecyclerView.Adapter<WallFallsAdapter.MyViewHolder>{
    private Context context;
    private List<String> list;
    private OnItemClickLitener onItemClickLitener;
    public WallFallsAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //注意瀑布流的布局与ListView和GirdView的不同
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallfalls,
                parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                onItemClickLitener.onItemClick(holder.itemView, position);
            }
        });
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Toast.makeText(context, "点击了TextView：" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends ViewHolder {

        private TextView textView;

        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }

    public interface OnItemClickLitener {

        void onItemClick(View view, int position);
    }

    /**
     * 点击Item回调
     * @param onItemClickLitener
     */
    public void setOnItemClickListener(OnItemClickLitener onItemClickLitener) {

        this.onItemClickLitener = onItemClickLitener;
    }

}
