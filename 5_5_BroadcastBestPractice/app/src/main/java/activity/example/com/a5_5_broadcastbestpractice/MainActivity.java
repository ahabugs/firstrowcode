package activity.example.com.a5_5_broadcastbestpractice;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private EditText editAccount;
    private EditText editPassword;
    private Button button_login;
    private String account;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editAccount = (EditText)findViewById(R.id.edit_text_account);
        editPassword = (EditText)findViewById(R.id.edit_text_password);
        button_login = (Button)findViewById(R.id.button_login);
        button_login.setOnClickListener(this);
        /*
        if (savedInstanceState != null) {
            editAccount.setText(savedInstanceState.getString("key_account"));
            editPassword.setText(savedInstanceState.getString("key_password"));
        }
        */
    }

    @Override
    public void onClick(View v) {
        account = editAccount.getText().toString();
        password = editPassword.getText().toString();

        if (account.equals("admin") && password.equals("123456")) {
            Intent intent1 = new Intent(MainActivity.this, FirstActivity.class);
            startActivity(intent1);
            finish();
        } else {
            Toast.makeText(MainActivity.this, "account or password is invalid",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*
        if (!account.equals(""))
            outState.putString("key_account", account);
        if (!password.equals(""))
            outState.putString("key_password", password);
         */
    }
}
