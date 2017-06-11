package activity.example.com.a3_4uicustomviews;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by bingo on 2017/6/10.
 */

/* method 1 */
/*public class TitleLayout extends LinearLayout { */

/* method 2 */
public class TitleLayout extends LinearLayout implements View.OnClickListener {

    public TitleLayout(Context context, AttributeSet atts) {
        super(context, atts);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        /* method 1*/
        /*
        Button button1 = (Button) findViewById(R.id.button_TitleLayout_Back);
        Button button2 = (Button) findViewById(R.id.button_TitleLayout_Edit);
        */

        /* 为什么只能在构造函数内定义setOnclickListener，在它外面定义报不能解析的错误*/
        /* method 1 */
        /*
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You clicked Edit Button in ThirdActivity",
                        Toast.LENGTH_LONG).show();
            }
        });
        */

        /* method 2 */
        Button button1 = (Button) findViewById(R.id.button_TitleLayout_Back);
        Button button2 = (Button) findViewById(R.id.button_TitleLayout_Edit);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    /* method 2*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_TitleLayout_Back:
                ((Activity)getContext()).finish();
                break;
            case R.id.button_TitleLayout_Edit:
                Toast.makeText(getContext(),"You clicked Edit Button in ThirdActivity",
                        Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
