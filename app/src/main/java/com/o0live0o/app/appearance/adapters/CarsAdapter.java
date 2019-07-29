package com.o0live0o.app.appearance.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.bean.CarBean;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {


    private Context mContext;
    private List<CarBean> mCarList;

    private View mItemView;

    private OnCheckItem onCheckItem = null;

    public CarsAdapter(Context context,List<CarBean> list){
        this.mContext = context;
        this.mCarList = list;
    }

    @NonNull
    @Override
    public CarsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_car,viewGroup,false);
        return new CarsAdapter.ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.ViewHolder viewHolder,final int i) {
        final CarBean car = mCarList.get(i);
        viewHolder.tvPlateNo.setText(car.getPlateNo());
        viewHolder.tvPlateType.setText(car.getPlateType());
        viewHolder.tvTestId.setText(car.getTestId());
        viewHolder.tvVIN.setText(car.getVin());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onCheckItem != null){
                    onCheckItem.onCheck(car,i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCarList!=null) return mCarList.size();
        return 0;
    }


    public void remove(int position) {
        mCarList.remove(position);
        notifyItemRemoved(position);
        //刷新下标，不然下标就重复
        notifyItemRangeChanged(position, mCarList.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        ImageView imageView;
        TextView tvPlateNo;
        TextView tvPlateType;
        TextView tvVIN;
        TextView tvTestId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            imageView = itemView.findViewById(R.id.car_item_logo);
            tvPlateType = itemView.findViewById(R.id.car_item_plateType);
            tvPlateNo = itemView.findViewById(R.id.car_item_plateNo);
            tvVIN = itemView.findViewById(R.id.car_item_vin);
            tvTestId = itemView.findViewById(R.id.car_item_testId);
        }
    }

    public void setOnCheckItem(OnCheckItem onCheckItem){
        this.onCheckItem = onCheckItem;
    }

    public interface OnCheckItem{
        void onCheck(CarBean car,int i);
    }
}
