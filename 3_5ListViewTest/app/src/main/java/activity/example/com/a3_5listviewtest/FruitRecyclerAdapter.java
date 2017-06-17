package activity.example.com.a3_5listviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by bingo on 2017/6/11.
 */

public class FruitRecyclerAdapter extends RecyclerView.Adapter<FruitRecyclerAdapter.ViewHolder> {

    private List<Fruit> mFruitList;
    /*
    * @warning
    *  I  should not have used static type of fruitItemResource here, But I don't know
    *  how to get a control id when having given the layout id of a xml file which
    *  contains this control. use static type, because class ViewHolder is static, this inner
    *  can only access a static type of a non-static method and member in outer class,
    *  it must not access a non-static method or member in outer class.
    * */
    private static FruitItemResource fruitItemResource;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        View fruitView;

        public ViewHolder(View view) {
            super(view);
            /**
             * imageView  = (ImageView)view.findViewById(R.id.fruit_image);
             * textView = (TextView) view.findViewById(R.id.fruit_name);
             *
             */
            /**
             * imageView  = (ImageView)view.findViewById(R.id.fruit_image2);
             * textView = (TextView) view.findViewById(R.id.fruit_name2);
             */
            if (fruitItemResource == null)
                return;
            imageView  = (ImageView)view.findViewById(fruitItemResource.getImageViewId());
            textView = (TextView) view.findViewById(fruitItemResource.getTextViewId());
            fruitView = view;
        }
    }

    /*public FruitRecyclerAdapter (List<Fruit> fruitList, FruitItemResource fruitItemResource) {*/
    public FruitRecyclerAdapter (List<Fruit> fruitList, FruitItemResource fruitItemResource) {
        this.mFruitList = fruitList;
        this.fruitItemResource = fruitItemResource;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  The ViewGroup into which the new View will be added after it is bound to
         *  an adapter position
         *  parent到底是谁？是RecyclerView.Adapter视图还是FruitRecyclerAdpter这个活动的视图？
         *  view是一个子项
         */
        /**
         *         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item,
         *         parent);
         *         原先没有带false，导致程序跑飞了。
         */
        /**
         * View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item,
                parent, false);
         */

        /**
         * View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item_hori,
                parent, false);
        */


        final View view = LayoutInflater.from(parent.getContext()).inflate(fruitItemResource.getLayoutId(),
                parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        /**
         *
         * why use viewHolder.fruitView ?
         * viewHolder.fruitView.setOnClickListener(new View.OnClickListener() {
         *
         * */
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(), "you clicked view " + fruit.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(), "you clicked image " + fruit.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.imageView.setImageResource(fruit.getImageId());
        holder.textView.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {

        return mFruitList.size();
    }
}
