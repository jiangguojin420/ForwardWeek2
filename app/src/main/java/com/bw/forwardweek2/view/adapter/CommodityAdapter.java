package com.bw.forwardweek2.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bw.forwardweek2.R;
import com.bw.forwardweek2.model.bean.CommodityBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.MyViewHolder> {
    private List<CommodityBean.ResultBean> commodityList;

    public CommodityAdapter(List<CommodityBean.ResultBean> commodityList) {
        this.commodityList = commodityList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_commodity, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        CommodityBean.ResultBean resultBean = commodityList.get(i);

        myViewHolder.mTv.setText(resultBean.getCommodityName());

        Glide.with(myViewHolder.mImg)
                .load(resultBean.getMasterPic())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher_round)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(myViewHolder.mImg);
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView mImg;
        @BindView(R.id.tv)
        TextView mTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
