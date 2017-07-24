package activity.example.com.a6_3_3_preferencemanagerrememberpassword;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private EditText editTextAccount;
    private EditText editTextPassword;
    private CheckBox checkBox;
    private LinearLayout layoutRememberPasswor;

    private Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAccount = (EditText)findViewById(R.id.edit_text_account);
        editTextPassword = (EditText)findViewById(R.id.edit_text_password);
        checkBox = (CheckBox)findViewById(R.id.checkbox_remember);

        layoutRememberPasswor = (LinearLayout)findViewById(
                R.id.liner_layout_view_remember_password);
        layoutRememberPasswor.setOnClickListener(this);

        button_login = (Button)findViewById(R.id.button_login);
        button_login.setOnClickListener(this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            editTextAccount.setText(account);
            editTextPassword.setText(password);
            checkBox.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                String account = editTextAccount.getText().toString();
                String password = editTextPassword.getText().toString();
                if (account.equals("admin") && password.equals("123456")) {
                    if (checkBox.isChecked()) {
                        /*
                        * .SharedPreferences$Editor.clear()' on a null object reference
                        * 如果在这里获取editor将导致checked=false时，editor等于null
                        * if get editor in this branch, then in the other branch, the editor will be
                        * equal to null. this will result in application crashing.(android.content.
                        * SharedPreferences$Editor.clear()' on a null object reference)
                        * */
                        /*this.editor = pref.edit();*/
                        editor.putString("account", account);
                        editor.putString("password", password);
                        editor.putBoolean("remember_password", true);
                    } else {
                        editor.clear();
                    }
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "account or password is invalid",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.liner_layout_view_remember_password:
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                } else {
                    checkBox.setChecked(true);
                }
                break;

            default:
                break;
        }
    }
}
