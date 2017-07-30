package activity.example.com.a7_4contentprovidertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private String bookId = null;
    private String categoryId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button_insert = (Button)findViewById(R.id.button_insert);
        Button button_update = (Button)findViewById(R.id.button_update);
        Button button_query = (Button)findViewById(R.id.button_query);
        Button button_delete = (Button)findViewById(R.id.button_delete);


        button_insert.setOnClickListener(this);
        button_update.setOnClickListener(this);
        button_query.setOnClickListener(this);
        button_delete.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Uri uri;
        Uri uriItem;

        /*
        * dual to getPathSegment() return a List<String>, get(index) get a element in the list,
        * we use type String here
        * */

        ContentValues values = new ContentValues();

        /*must do insert before delete a record */
        switch (v.getId()) {
            case R.id.button_insert:
                uri = Uri.parse("content://activity.example.com.a7_4database.provider/MyBook");
                /*ContentValues values = new ContentValues();*/
                values.clear();
                values.put("name", "The Wade Code");
                values.put("author", "Daniel");
                values.put("pages", 454);
                values.put("price", 45.46);
                uriItem = getContentResolver().insert(uri, values);
                /* use this id later as the index of a record that will be deleted  */
                bookId = uriItem.getPathSegments().get(1);

                uri = Uri.parse("content://activity.example.com.a7_4database.provider/MyCategory");
                values.clear();
                values.put("category_name", "Science");
                values.put("category_code", "0001");
                getContentResolver().insert(uri, values);

                values.clear();
                values.put("category_name", "Mathematics");
                values.put("category_code", "0002");
                uriItem = getContentResolver().insert(uri, values);
                /* use this id later as the index of a record that will be deleted  */
                categoryId = uriItem.getPathSegments().get(1);
                break;

            case R.id.button_update:
                uri = Uri.parse("content://activity.example.com.a7_4database.provider/MyBook");
                values.clear();
                values.put("price", 10.99);
                getContentResolver().update(uri, values, "author = ?", new String[]{"Daniel"});
                values.clear();

                uri = Uri.parse("content://activity.example.com.a7_4database.provider/MyCategory");
                values.put("category_name", "Mathematics II");
                values.put("category_code", "1000");
                getContentResolver().update(uri, values, "category_code = ?",
                        new String[]{"0002"});
                break;
            case R.id.button_query:
                uri = Uri.parse("content://activity.example.com.a7_4database.provider/MyBook");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(TAG, "query MyBook name is " + name);
                        Log.d(TAG, "query MyBook author is " + author);
                        Log.d(TAG, "query MyBook pages is " + pages);
                        Log.d(TAG, "query MyBook price is " + price);
                    } while (cursor.moveToNext());
                    cursor.close();
                }


                uri = Uri.parse("content://activity.example.com.a7_4database.provider/MyCategory");
                cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String categoryName = cursor.getString(
                                cursor.getColumnIndex("category_name"));
                        String categoryCode = cursor.getString(
                                cursor.getColumnIndex("category_code"));
                        Log.d(TAG, "rawQuery MyCategory category name is : " + categoryName);
                        Log.d(TAG, "rawQuery MyCategory category code is : " + categoryCode);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

                break;
            case R.id.button_delete:
                uri = Uri.parse("content://activity.example.com.a7_4database.provider/MyBook/"
                        + bookId);
                /*getContentResolver().delete(uri, "id = ?", new String[]{bookId.toString()})*/
                getContentResolver().delete(uri, null, null);

                uri = Uri.parse("content://activity.example.com.a7_4database.provider/MyCategory/"
                        + categoryId);
                getContentResolver().delete(uri, null, null);
                break;
            default:
                break;
        }
    }
}
