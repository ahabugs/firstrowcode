package activity.example.com.a3_7uibestpractice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bingo on 2017/6/18.
 */

public
class RecyclerViewMsgAdapter extends RecyclerView.Adapter<RecyclerViewMsgAdapter.ViewHolder> {
    private List<ChatMsg> mMsgList;

    public RecyclerViewMsgAdapter(List<ChatMsg> msgList) {
        this.mMsgList = msgList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;

        public ViewHolder(View v) {
            super(v);
            leftLayout = (LinearLayout) v.findViewById(R.id.linear_layout_left1);
            rightLayout = (LinearLayout) v.findViewById(R.id.linear_layout_right1);
            leftMsg = (TextView) v.findViewById(R.id.text_view_left);
            rightMsg = (TextView) v.findViewById(R.id.text_view_right);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /* a view holder is a recycler view item*/
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,
                parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMsg chatMsg = mMsgList.get(position);
        if (chatMsg.getType() == chatMsg.TYPE_SENT) {
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightMsg.setText(chatMsg.getContent());
        } else if (chatMsg.getType() == chatMsg.TYPE_RECEIVED) {
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftLayout.setVisibility(View.VISIBLE);
            String string = chatMsg.getContent();
            holder.leftMsg.setText(string);
        }

    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
