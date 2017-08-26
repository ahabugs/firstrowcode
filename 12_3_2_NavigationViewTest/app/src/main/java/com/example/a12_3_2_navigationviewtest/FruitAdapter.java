package com.example.a12_3_2_navigationviewtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 17-8-18.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.MyViewHolder> {
    private List<Fruit> mFruitList;
    private Context mContext = null;

    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    // 构造一个子项装载器
    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public MyViewHolder(View v) {
            super(v);
            cardView = (CardView)v;
            imageView = (ImageView)v.findViewById(R.id.image_view_fruit_item);
            textView = (TextView)v.findViewById(R.id.text_view_fruit_item);
        }
    }

    // 创建装载器时被调用
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,
                parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = myViewHolder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Intent intent = new Intent(mContext, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                mContext.startActivity(intent);
            }
        });
        return myViewHolder;
    }

    // 准备显示指定位置的子项时被调用
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        super.onBindViewHolder(holder, position);
        Fruit fruit = mFruitList.get(position);
        /*
        * 加入图片过大，会用大量的内存，一旦内存不足就可能会溢出。所以这
        * 种方法不好。用Glide去处理图片，可以避免这种情况。Glide有剪切与压缩功能。
        * */
        // holder.imageView.setImageResource(fruit.getImageId());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.imageView);
        holder.textView.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
