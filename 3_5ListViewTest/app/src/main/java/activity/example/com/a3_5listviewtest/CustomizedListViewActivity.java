package activity.example.com.a3_5listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomizedListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customized_list_view);

        initFruitList();
        FruitAdapter adapter = new FruitAdapter(CustomizedListViewActivity.this,
                R.layout.fruit_item, fruitList);
        ListView listView = (ListView) findViewById(R.id.listview_customized);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void addFruit(String name, int imageId) {
        Fruit fruit = new Fruit(name, imageId);
        this.fruitList.add(fruit);
    }

    private void initFruitList() {
        for (int i = 0; i < 4; i++) {
            /*
            Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
            fruitList.add(apple);
            */
            addFruit("Apple", R.drawable.apple_pic);
            addFruit("Banana", R.drawable.banana_pic);
            addFruit("Orange", R.drawable.orange_pic);
            addFruit("Watermelon", R.drawable.watermelon_pic);
            addFruit("Pear", R.drawable.pear_pic);
            addFruit("Grape", R.drawable.grape_pic);
            addFruit("Pineapple", R.drawable.pineapple_pic);
            addFruit("Strawberry", R.drawable.strawberry_pic);
            addFruit("Cherry", R.drawable.cherry_pic);
            addFruit("Mango", R.drawable.mango_pic);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        /**
         * An AdapterView is a view whose children are determined by an Adapter.
         *
         * My understanding: AdapterView is a view set of all item in ListView
         */
        /**
         * Fruit fruit = fruitList.get(position);
         * Fruit fruit = (Fruit) parent.getItemAtPosition(position);
         * the two kind of calling  work the same
         */
        /* Fruit fruit = fruitList.get(position); */

        Fruit fruit = (Fruit) parent.getItemAtPosition(position);
        Toast.makeText(CustomizedListViewActivity.this, fruit.getName(),
                Toast.LENGTH_LONG).show();
    }
}
