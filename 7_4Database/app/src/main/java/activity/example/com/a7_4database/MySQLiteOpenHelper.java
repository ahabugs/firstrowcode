package activity.example.com.a7_4database;

/**
 * Created by bingo on 2017/7/28.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/*import android.widget.Toast;*/

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String CREATE_BOOK = "create table MyBook (" +
            "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text)";
    private static final String CREATE_CATEGORY = "create table MyCategory ("
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
        /*Toast.makeText(myContext, "create MyBook succeeded", Toast.LENGTH_LONG).show();*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists MyCategory");
        db.execSQL(CREATE_CATEGORY);
        /*Toast.makeText(myContext, "upgrade", Toast.LENGTH_LONG).show();*/
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*super.onDowngrade(db, oldVersion, newVersion);*/
        db.execSQL("drop table if exists MyCategory");
        /*Toast.makeText(myContext, "downgrade", Toast.LENGTH_LONG).show();*/
    }
}
