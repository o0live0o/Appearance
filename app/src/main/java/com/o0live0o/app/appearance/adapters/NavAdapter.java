package com.o0live0o.app.appearance.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.activitys.MainActivity;
import com.o0live0o.app.appearance.bean.NavBean;

import java.util.List;

public class NavAdapter extends  RecyclerView.Adapter<NavAdapter.ViewHolder> {

    private Context mContex;
    private List<NavBean> list;

    private View mItemView;

    public NavAdapter(Context context,List<NavBean> list){
        this.mContex = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mItemView = LayoutInflater.from(mContex).inflate(R.layout.nav_item_view,viewGroup,false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final NavBean nav = list.get(i);
        viewHolder.tvTitle.setText(nav.getMenuName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String className = "com.o0live0o.app.appearance.activitys."+nav.getActivity();
                try {
                    Class activity = Class.forName(className);
                    Intent intent = new Intent(mContex, activity);
                    intent.putExtra("CheckType",nav.getCheckType());
                    mContex.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private View itemView;
        private TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvTitle = itemView.findViewById(R.id.nav_item_title);
        }
    }
}
