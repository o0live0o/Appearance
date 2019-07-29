package com.o0live0o.app.appearance.log;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.o0live0o.app.appearance.R;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

    private List<String> logs;

    public LogAdapter(List<String> list) {
        logs = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.log_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String s = logs.get(i);
        viewHolder.tvLog.setText(s);
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLog = itemView.findViewById(R.id.tv_log_item);
        }
    }
}
