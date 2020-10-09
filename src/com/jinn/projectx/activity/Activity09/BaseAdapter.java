package com.jinn.projectx.activity.Activity09;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinn.projectx.R;
import com.jinn.projectx.activity.Utils.Logit;

import java.util.ArrayList;

/**
 * Created by jinnlee on 2020/6/20.
 */
public class BaseAdapter extends RecyclerView.Adapter {
    private ArrayList<String> mList;
    private Context mContext;

    public BaseAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList lists) {
        mList = new ArrayList<>(lists);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Logit.d("jinn","onCreateViewHolder,viewType:"+viewType);
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_09_item1,null);
        return new HolderView(view);
    };

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       Logit.d("jinn","onBindViewHolder,pos:"+position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class HolderView extends ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public HolderView(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item1_image);
            textView = itemView.findViewById(R.id.item1_text);
        }
    }
}
