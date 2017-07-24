package activity.example.com.a6_5litepaltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private Book book = new Book();
    private Book book0 = new Book();
    private Book book1 = new Book();
    private Book book2 = new Book();

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
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.button_create_database:
                LitePal.getDatabase();
                break;
            case R.id.button_delete_database:
                deleteDatabase("BookStore.db");
                Toast.makeText(this, "delete book succeeded", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_upgrade:

                break;
            case R.id.button_downgrade:

                break;
            case R.id.button_insert:

                book0.setName("The Da Vinci Code");
                book0.setAuthor("Dan Brown");
                book0.setPages(454);
                book0.setPrice(45.40);
                book0.setPress("unknown");
                book0.save();

                book1.setName("The Lost Symbol");
                book1.setAuthor("Dan Brown");
                book1.setPages(510);
                book1.setPrice(51.05);
                book1.setPress("unknown");
                book1.save();
                book1.setPrice(0.5105);
                book1.save();

                book2.setName("Hello world");
                book2.setAuthor("Wade");
                book2.setPages(310);
                book2.setPrice(51.05);
                book2.setPress("Anchor");
                book2.save();
                book2.setPrice(0.5105);
                book2.save();
                break;
            case R.id.button_update:
                book.setPrice(31.13);
                book.setPress("dennis");
                book.updateAll("name = ? and author = ?", "Hello world", "Wade");
                /*
                * book.setToDefault("pages");
                * book.updateAll();
                * */

                break;
            case R.id.button_query:
                /*List<Book> books = DataSupport.findAll(Book.class);*/
                List<Book> books = DataSupport.select("name", "author", "pages")
                        .where("pages > ?", "100")
                        .order("pages desc")
                        .limit(10)
                        .offset(1)
                        .find(Book.class);
                for (Book book3: books) {
                    Log.d(TAG, "book name is " + book3.getName());
                    Log.d(TAG, "book author is " + book3.getAuthor());
                    Log.d(TAG, "book pages is " + book3.getPages());
                }
                break;
            case R.id.button_delete:
                /*book.delete(modeClass, condition)*/
                DataSupport.deleteAll(Book.class, "price > ?", "50");
                break;
            default:
                break;
        }
    }
}
