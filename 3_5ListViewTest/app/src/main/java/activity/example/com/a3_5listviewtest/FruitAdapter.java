package activity.example.com.a3_5listviewtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bingo on 2017/6/11.
 * customized ArrayAdapter
 */

public class FruitAdapter extends ArrayAdapter<Fruit> {

    private int resourceLayoutId;

    public FruitAdapter(Context context, int listView_LayoutId, List<Fruit> objects) {
        /* super(context, R.layout.fruit_item, objects); */
        /**
         * @param listView_LayoutId
         * The resource ID for a layout file to use when instantiating views
         * @param objects
         * The objects to represent in the ListView.
         */
        super(context, listView_LayoutId, objects);
        resourceLayoutId = listView_LayoutId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder viewHolder;

        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceLayoutId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.fruit_image);
            viewHolder.textView = (TextView) view.findViewById(R.id.fruit_name);
            view.setTag(viewHolder);
        }
        /*
        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
        TextView fruitName = (TextView) view.findViewById(R.id.fruit_name);
        Fruit fruit = getItem(position);
        fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getName());
        */
        Fruit fruit = getItem(position);
        viewHolder.imageView.setImageResource(fruit.getImageId());
        viewHolder.textView.setText(fruit.getName());

        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
