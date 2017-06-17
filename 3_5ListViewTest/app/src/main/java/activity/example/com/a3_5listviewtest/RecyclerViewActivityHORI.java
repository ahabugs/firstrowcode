package activity.example.com.a3_5listviewtest;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivityHORI extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();
    private FruitItemResource fruitItemResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_hori);

        initFruitList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);

        /* RecyclerView layout: how to place an item in recycler view */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        FruitItemResource fruitItemResource = new FruitItemResource(R.layout.fruit_item_hori);
        fruitItemResource.setImageViewId(R.id.fruit_image2);
        fruitItemResource.setTextViewId(R.id.fruit_name2);
        FruitRecyclerAdapter fruitRecyclerAdapter = new FruitRecyclerAdapter(fruitList, fruitItemResource);
        recyclerView.setAdapter(fruitRecyclerAdapter);
    }

    private void addFruit(String name, int imageId) {
        Fruit fruit = new Fruit(name, imageId);
        this.fruitList.add(fruit);
    }

    private void initFruitList() {

        /*this.fruitItemResource = new FruitItemResource(R.layout.fruit_item_hori, 0, 0);*/

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
