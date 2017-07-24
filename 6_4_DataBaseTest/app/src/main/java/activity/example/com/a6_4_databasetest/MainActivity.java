package activity.example.com.a6_4_databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MySQLiteOpenHelper dbHelper;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_create_db = (Button)findViewById(R.id.button_create_database);
        Button button_delete_db = (Button)findViewById(R.id.button_delete_database);
        Button button_upgrade = (Button)findViewById(R.id.button_upgrade);
        Button button_downgrade = (Button)findViewById(R.id.button_downgrade);

        Button button_insert = (Button)findViewById(R.id.button_insert);
        Button button_update = (Button)findViewById(R.id.button_update);
        Button button_query = (Button)findViewById(R.id.button_query);
        Button button_delete = (Button)findViewById(R.id.button_delete);

        button_create_db.setOnClickListener(this);
        button_delete_db.setOnClickListener(this);
        button_upgrade.setOnClickListener(this);
        button_downgrade.setOnClickListener(this);

        button_insert.setOnClickListener(this);
        button_update.setOnClickListener(this);
        button_query.setOnClickListener(this);
        button_delete.setOnClickListener(this);

        dbHelper = new MySQLiteOpenHelper(this, "BookStore.db", null, 1);
        /*dbHelper = new MySQLiteOpenHelper(this, "BookStore.db", null, 2);*/
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase db;
        ContentValues values = new ContentValues();

        switch (v.getId()) {
            case R.id.button_create_database:
                dbHelper.getWritableDatabase();
                break;
            case R.id.button_delete_database:
                deleteDatabase("BookStore.db");
                Toast.makeText(this, "delete book succeeded", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_upgrade:
                dbHelper = new MySQLiteOpenHelper(this, "BookStore.db", null, 2);
                dbHelper.getWritableDatabase();
                break;
            case R.id.button_downgrade:
                dbHelper = new MySQLiteOpenHelper(this, "BookStore.db", null, 1);
                dbHelper.getWritableDatabase();
                break;

            case R.id.button_insert:
                db = dbHelper.getWritableDatabase();
                /*ContentValues values = new ContentValues();*/
                values.clear();
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 45.46);
                db.insert("Book", null, values);
                values.clear();
                db.execSQL("insert into Book (name, author, pages, price) values (?, ? , ?, ?)",
                        new String[] {"The Lost Symbol", "Dan Brown", "513", "51.35"});

                db.execSQL("insert into Book (name, author, pages, price) values (?, ? , ?, ?)",
                        new String[] {"The Lost Symbol", "Dan Brown", "112", "11.20"});
                break;
            case R.id.button_update:
                db = dbHelper.getWritableDatabase();
                values.clear();
                values.put("price", 10.99);
                db.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});
                values.clear();

                db.execSQL("update Book set price = ? where name = ?",
                        new String[] {"20.99", "The Lost Symbol"});
                break;
            case R.id.button_query:
                db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(TAG, "query book name is " + name);
                        Log.d(TAG, "query book author is " + author);
                        Log.d(TAG, "query book pages is " + pages);
                        Log.d(TAG, "query book price is " + price);
                    } while (cursor.moveToNext());
                }
                cursor.close();

                db.rawQuery("select * from Book", null);
                cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(TAG, "rawQuery book name is " + name);
                        Log.d(TAG, "rawQuery book author is " + author);
                        Log.d(TAG, "rawQuery book pages is " + pages);
                        Log.d(TAG, "rawQuery book price is " + price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                break;
            case R.id.button_delete:
                db = dbHelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[] {"500"});
                db.execSQL("delete from Book where pages < ?", new String[] {"300"});
                break;
            default:
                break;
        }
    }
}
