package activity.example.com.a3_7uibestpractice;

/**
 * Created by bingo on 2017/6/18.
 */

public class ChatMsg {
    public static final int TYPE_SENT = 1;
    public static final int TYPE_RECEIVED = 0;
    private int type;
    private String content;

    public ChatMsg(int type, String content) {
        this.type = type;
        this.content = content;
    }

    int getType() {
        return this.type;
    }

    String getContent() {
        return this.content;
    }
}
