package activity.example.com.a3_7uibestpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UIChatActivity extends AppCompatActivity implements View.OnClickListener{

    private String[] sentContent = new String[] {
            "What's your favourite fruit?",
            "What kind of fruit do you want?",
            "What kind of fruit do you like best",
            "Which kind of fruit would you want to eat?"
    };
    private String[] receiveContent = new String[] {
            "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry",
            "Cherry", "Mango"
    };

    private Button button_send;
    private EditText editText_input;
    private RecyclerView recyclerView;
    private RecyclerViewMsgAdapter adapter;

    /* ArrayList<String> strArray = new ArrayList<>() */

    private List<ChatMsg> msgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uichat);

        initChatMsg();
        /* RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_chat_msg); */
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_chat_msg);
        /* set layoutManager for recyclerView */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        /* pad RecyclerViewAdapter */
        /*RecyclerViewMsgAdapter adapter = new RecyclerViewMsgAdapter(msgList);*/
        adapter = new RecyclerViewMsgAdapter(msgList);

        /* add adapter into recycler view */
        recyclerView.setAdapter(adapter);

        button_send = (Button) findViewById(R.id.button_send1);
        button_send.setOnClickListener(this);
        editText_input = (EditText) findViewById(R.id.edit_text_input1);
    }

    @Override
    public void onClick(View v) {
        String content = editText_input.getText().toString();

        if (!"".equals(content)) {
            /* ChatMsg chatMsg = new ChatMsg(ChatMsg.TYPE_SENT, content); */
            ChatMsg chatMsg = new ChatMsg(getRandomTypeChatMsg(), content);
            msgList.add(chatMsg);
            adapter.notifyItemInserted(msgList.size() - 1);
            recyclerView.scrollToPosition(msgList.size() - 1);
            editText_input.setText("");
        }
    }

    private int getRandomTypeChatMsg() {
        Random random = new Random();
        return random.nextInt(2);
    }


    private String getRandomSentContent() {
        Random random = new Random();
        int i = random.nextInt(4);
        return sentContent[i];
    }


    private String getRandomRECVContent() {
        Random random = new Random();
        int i = random.nextInt(10);

        return receiveContent[i];
    }


    private void addChatMsg() {
        Random random = new Random();
        if (random.nextInt(ChatMsg.TYPE_SENT+1) == ChatMsg.TYPE_RECEIVED)
            msgList.add(new ChatMsg(ChatMsg.TYPE_RECEIVED, getRandomRECVContent()));
        else
            msgList.add(new ChatMsg(ChatMsg.TYPE_SENT, getRandomSentContent()));

    }

    private void initChatMsg() {
        for (int i = 0; i < 2; i++) {
            addChatMsg();
            addChatMsg();
            addChatMsg();
            addChatMsg();
            addChatMsg();
            addChatMsg();
        }
    }

}
