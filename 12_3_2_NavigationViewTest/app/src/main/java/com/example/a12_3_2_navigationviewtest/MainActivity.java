package com.example.a12_3_2_navigationviewtest;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private List<Fruit> fruitList = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private FruitAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//        new DrawerLayout.DrawerListener()
//        drawerLayout.addDrawerListener();
//        ViewHelper

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        // 默认选中
        navigationView.setCheckedItem(R.id.item_nav_call);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 显示HomeAsUp按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 设置HomeAsUp按钮的图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        initFruitList();
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
        // 网络刷新失败时，点击刷新进度条再次刷新
        // swipeRefreshLayout.setOnClickListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "You clicked the Backup", Toast.LENGTH_LONG).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked the Delete", Toast.LENGTH_LONG).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked the Settings", Toast.LENGTH_LONG).show();
                break;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_nav_call:
                Toast.makeText(this, "You clicked the Call", Toast.LENGTH_LONG).show();
                break;
            case R.id.item_nav_friends:
                Toast.makeText(this, "You clicked the Friends", Toast.LENGTH_LONG).show();
                break;
            case R.id.item_nav_location:
                Toast.makeText(this, "You clicked the Location", Toast.LENGTH_LONG).show();
                break;
            case R.id.item_nav_mail:
                Toast.makeText(this, "You clicked the Mail", Toast.LENGTH_LONG).show();
                break;
            case R.id.item_nav_tasks:
                Toast.makeText(this, "You clicked the Tasks", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawers();
                // drawerLayout.closeDrawer(GravityCompat.START);
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
//                Toast.makeText(this, "You clicked FAB", Toast.LENGTH_LONG).show();
//
                 Snackbar.make(v, "Data deleted", Snackbar.LENGTH_LONG)
                // Snackbar.make(drawerLayout, "Data deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Data Restored",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }


    private void addFruit(String name, int imageId) {
        Fruit fruit = new Fruit(name, imageId);
        fruitList.add(fruit);
    }

    private void initFruitList() {

        /*this.fruitItemResource = new FruitItemResource(R.layout.fruit_item_hori, 0, 0);*/

        for (int i = 0; i < 2; i++) {
            /*
            Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
            fruitList.add(apple);
            */
            addFruit("Apple", R.drawable.apple);
            addFruit("Banana", R.drawable.banana);
            addFruit("Orange", R.drawable.orange);
            addFruit("Watermelon", R.drawable.watermelon);
            addFruit("Pear", R.drawable.pear);
            addFruit("Grape", R.drawable.grape);
            addFruit("Pineapple", R.drawable.pineapple);
            addFruit("Strawberry", R.drawable.strawberry);
            addFruit("Cherry", R.drawable.cherry);
            addFruit("Mango", R.drawable.mango);
        }
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruitList();
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

}
