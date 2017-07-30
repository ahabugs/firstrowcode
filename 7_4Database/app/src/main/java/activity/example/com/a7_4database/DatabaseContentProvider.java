package activity.example.com.a7_4database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/*import android.database.sqlite.SQLiteOpenHelper;*/
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import static android.content.ContentValues.TAG;

public class DatabaseContentProvider extends ContentProvider {
    private static final String TAG = "DatabaseContentProvider";
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    public static final int BOOK_DIR = 1;
    public static final int BOOK_ITEM = 2;
    public static final int CATEGORY_DIR = 3;
    public static final int CATEGORY_ITEM = 4;
    public static final String AUTHORITY = "activity.example.com.a7_4database.provider";
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("activity.example.com.a7_4database.provider", "MyBook", BOOK_DIR);
        uriMatcher.addURI("activity.example.com.a7_4database.provider", "MyBook/#", BOOK_ITEM);
        uriMatcher.addURI("activity.example.com.a7_4database.provider", "MyCategory",
                CATEGORY_DIR);
        uriMatcher.addURI("activity.example.com.a7_4database.provider", "MyCategory/#",
                CATEGORY_ITEM);
    }

    public DatabaseContentProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        // throw new UnsupportedOperationException("Not yet implemented");
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        int rows = 0;
        List<String> list;

        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                rows = db.delete("MyBook", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                /* get first item */
                String bookId = uri.getPathSegments().get(1);
                list = uri.getPathSegments();
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "getPathSegments book: " + list.get(i));
                }
                rows = db.delete("MyBook", "id = ?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                rows = db.delete("MyCategory", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                /* get first item */
                String categoryId = uri.getPathSegments().get(1);
                list = uri.getPathSegments();
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "getPathSegments category: " + list.get(i));
                }
                rows = db.delete("MyCategory",  "id = ?", new String[]{categoryId});
                break;
            default:
                break;
        }

        return rows;
    }

    @Override
    public String getType(@NonNull Uri uri) {
/* TODO: Implement this to handle requests for the MIME type of the data */
        // at the given URI.
        // throw new UnsupportedOperationException("Not yet implemented");
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return  "vnd.android.cursor.dir/vnd." + AUTHORITY + "MyBook";
            case BOOK_ITEM:
                return  "vnd.android.cursor.item/vnd." + AUTHORITY + "MyBook";
            case CATEGORY_DIR:
                return  "vnd.android.cursor.dir/vnd." + AUTHORITY + "MyCategory";
            case CATEGORY_ITEM:
                return  "vnd.android.cursor.item/vnd." + AUTHORITY + "MyCategory";
            default:
                break;
        }

        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        /*TODO: Implement this to handle requests to insert a new row. */
        // throw new UnsupportedOperationException("Not yet implemented");
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        Uri uriReturn = null;

        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                /* inert one item */
                long bookId = db.insert("MyBook", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/MyBook/" + bookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                /* inert one item */
                long categoryId = db.insert("MyCategory", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/Category/" + categoryId);
                break;
            default:
                break;
        }

        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        // return false;
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext(), "MyBookStore.db", null, 2);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Tthrow new UnsupportedOperationException("Not yet implemented");
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = null;
        List <String> list;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = db.query("MyBook", projection, selection, selectionArgs, null,
                        null, sortOrder);
                break;
            case BOOK_ITEM:
                /* get first item */
                String bookId = uri.getPathSegments().get(1);
                list = uri.getPathSegments();
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "getPathSegments book: " + list.get(i));
                }
                cursor = db.query("MyBook", projection, "id = ?", new String[]{bookId},
                        null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query("MyCategory", projection, selection, selectionArgs, null,
                        null, sortOrder);
                break;
            case CATEGORY_ITEM:
                /* get first item */
                String categoryId = uri.getPathSegments().get(1);
                list = uri.getPathSegments();
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "getPathSegments category: " + list.get(i));
                }
                cursor = db.query("MyCategory", projection, "id = ?", new String[]{categoryId},
                        null, null, sortOrder);
                break;
            default:
                break;
        }

        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // throw new UnsupportedOperationException("Not yet implemented");
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        int rows = 0;

        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                rows = db.update("MyBook", values, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                rows = db.update("MyBook",  values, "id = ?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                rows = db.update("MyCategory", values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                rows = db.update("MyCategory",  values, "id = ?", new String[]{categoryId});
                break;
            default:
                break;
        }

        return rows;
    }
}
