package activity.example.com.a3_5listviewtest;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

/**
 * Created by bingo on 2017/6/17.
 */

public class FruitItemResource {
    @IdRes  private int imageViewId;
    @IdRes  private int textViewId;
    @LayoutRes private int layoutId;

    public FruitItemResource(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
    }

    public void setImageViewId(int imageViewId) {
        this.imageViewId =imageViewId;
    }

    public void setTextViewId(int textViewId) {
        this.textViewId = textViewId;
    }

    public int getLayoutId() {
        return this.layoutId;
    }

    public int getImageViewId() {
        return this.imageViewId;
    }

    public int getTextViewId() {
        return this.textViewId;
    }
}
