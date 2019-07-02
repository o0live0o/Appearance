package com.o0live0o.app.appearance.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.appearance.enums.CheckState;
import com.o0live0o.app.appearance.listener.ExteriorChangeListener;

import java.util.List;


public class ChekItemAdapter extends RecyclerView.Adapter<ChekItemAdapter.ViewHoler> {

    private View mView;
    private List<ExteriorBean> mList;
    private Context mContext;

    private ExteriorChangeListener exteriorChangeListener;
    private CheckUnpassItem checkUnpassItem = null;

    public ChekItemAdapter(Context context, List<ExteriorBean> list){
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.check_item_view,viewGroup,false);
        return new ViewHoler(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoler viewHoler,final int i) {
        final ExteriorBean bean = mList.get(i);
        viewHoler.tvView.setText(bean.getItemName());
        if (bean.getItemState() == CheckState.FAIL){
            viewHoler.rdBtn.check(R.id.chk_fail);
        } else if (bean.getItemState() == CheckState.PASS) {
            viewHoler.rdBtn.check(R.id.chk_pass);
        } else if (bean.getItemState() == CheckState.NOJUDGE) {
            viewHoler.rdBtn.check(R.id.chk_none);
        }



        viewHoler.rdBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                boolean isFail = false;
                if (exteriorChangeListener != null) {
                    switch (checkedId) {
                        case R.id.chk_pass:
                            exteriorChangeListener.onChange(viewHoler.getAdapterPosition(),CheckState.PASS);
                            break;
                        case R.id.chk_fail:

                            exteriorChangeListener.onChange(viewHoler.getAdapterPosition(),CheckState.FAIL);
                            isFail = true;
                            break;
                        case R.id.chk_none:
                            exteriorChangeListener.onChange(viewHoler.getAdapterPosition(),CheckState.NOJUDGE);
                            break;
                    }
                }
                if (checkUnpassItem != null){
                    checkUnpassItem.onCheck(viewHoler.getAdapterPosition(),isFail);
                }
            }
        });
    }

    public void setExteriorChangeListener(ExteriorChangeListener ex){
        this.exteriorChangeListener = ex;
    }

    public void setCheckUnpassItem(CheckUnpassItem checkUnpassItem){
        this.checkUnpassItem = checkUnpassItem;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHoler extends  RecyclerView.ViewHolder{

        View itemView;
        TextView tvView;
        RadioGroup rdBtn;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvView = itemView.findViewById(R.id.check_name);
            rdBtn = itemView.findViewById(R.id.chk_group);
        }
    }
    public interface  CheckUnpassItem{
         void onCheck(int i,boolean isFail);
    }
}

