package activity.example.com.a3_5listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FruitStaggerLayoutActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_stagger_layout);

        initFruitList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view3);
        FruitItemResource fruitItemResource = new
                FruitItemResource(R.layout.fruit_item_stagger);
        fruitItemResource.setImageViewId(R.id.image_view_stagger);
        fruitItemResource.setTextViewId(R.id.text_view_stagger);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        FruitRecyclerAdapter fruitRecyclerAdapter = new FruitRecyclerAdapter(fruitList,
                fruitItemResource);
        recyclerView.setAdapter(fruitRecyclerAdapter);
    }

    private void addFruit(String name, int imageId) {
        Fruit fruit = new Fruit(name, imageId);
        this.fruitList.add(fruit);
    }

    private void initFruitList() {

        /*this.fruitItemResource = new FruitItemResource(R.layout.fruit_item_hori, 0, 0);*/

        for (int i = 0; i < 2; i++) {
            /*
            Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
            fruitList.add(apple);
            */
            addFruit(getRandomLengthName("Apple"), R.drawable.apple_pic);
            addFruit(getRandomLengthName("Banana"), R.drawable.banana_pic);
            addFruit(getRandomLengthName("Orange"), R.drawable.orange_pic);
            addFruit(getRandomLengthName("Watermelon"), R.drawable.watermelon_pic);
            addFruit(getRandomLengthName("Pear"), R.drawable.pear_pic);
            addFruit(getRandomLengthName("Grape"), R.drawable.grape_pic);
            addFruit(getRandomLengthName("Pineapple"), R.drawable.pineapple_pic);
            addFruit(getRandomLengthName("Strawberry"), R.drawable.strawberry_pic);
            addFruit(getRandomLengthName("Cherry"), R.drawable.cherry_pic);
            addFruit(getRandomLengthName("Mango"), R.drawable.mango_pic);
        }
    }

    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(name);
        }
        return stringBuilder.toString();
    }
}
