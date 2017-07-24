package activity.example.com.a6_4_databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by bingo on 2017/7/23.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String CREATE_BOOK = "create table Book (" +
            "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text)";
    private static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";
    private Context myContext;

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
        super(context, name, factory, version);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        Toast.makeText(myContext, "create book succeeded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Category");
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(myContext, "upgrade", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*super.onDowngrade(db, oldVersion, newVersion);*/
        db.execSQL("drop table if exists Category");
        Toast.makeText(myContext, "downgrade", Toast.LENGTH_LONG).show();
    }
}
