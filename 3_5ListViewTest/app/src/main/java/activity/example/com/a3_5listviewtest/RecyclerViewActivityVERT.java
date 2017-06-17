package activity.example.com.a3_5listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivityVERT extends AppCompatActivity {

    /**
     *  private List<Fruit> fruitList;
     *  原先程序没有使用new ArrayList<>()来创建对象，导致程序跑飞了。
     *
     * */
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_vert);

        initFruitList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        /**
        * FruitRecyclerAdapter fruitRecyclerAdapter = new FruitRecyclerAdapter(fruitList, R.layout.fruit_item);
        recyclerView.setAdapter(fruitRecyclerAdapter);
        */
        FruitItemResource fruitItemResource = new FruitItemResource(R.layout.fruit_item);
        fruitItemResource.setImageViewId(R.id.fruit_image);
        fruitItemResource.setTextViewId(R.id.fruit_name);
        FruitRecyclerAdapter fruitRecyclerAdapter = new FruitRecyclerAdapter(fruitList, fruitItemResource);
        recyclerView.setAdapter(fruitRecyclerAdapter);

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
}
