package com.example.a12_3_2_navigationviewtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by Administrator on 17-8-19.
 */

public class FruitComment implements View.OnClickListener, View.OnTouchListener {
    private View commentView;
    private EditText commentText;
    private Activity mContext;
    private PopupWindow popupWindow;

    public FruitComment(Activity activity) {
        mContext = activity;
//        group = (ViewGroup)activity.getWindow().getDecorView();
        commentView = LayoutInflater.from(mContext).inflate(R.layout.fruit_comment, null);
        popupWindow = new PopupWindow(commentView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setAnimationStyle(R.style.popCommentViewStyle);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        commentText = (EditText)commentView.findViewById(R.id.edit_text_fruit_comment);
        Button button_commit = (Button)commentView.findViewById(R.id.button_commit);
        button_commit.setOnClickListener(this);
//        commentView.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_commit:
                Toast.makeText(mContext, "You commit your comment", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int height = commentView.findViewById(R.id.pop_layout).getTop();
        int y = (int)event.getY();
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(y < height){
                popupWindow. dismiss();
            }
        }
        return true;
    }

    public void show() {
//        FruitActivity activity = (FruitActivity) mContext;
//        activity.findViewById(R.layout.activity_fruit);
//        View root = group.getChildAt(0);
////        View root = ((ViewGroup)commentView.findViewById(android.R.id.content)).getChildAt(0);
//        popupWindow.showAtLocation(root, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

        // 在底部显示
        popupWindow.showAtLocation(mContext.findViewById(R.id.nest_scroll_view_fruit_activity),
                Gravity.BOTTOM, 0, 0);


    }
}
