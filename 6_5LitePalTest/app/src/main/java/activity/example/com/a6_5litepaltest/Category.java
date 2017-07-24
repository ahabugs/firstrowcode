package activity.example.com.a6_5litepaltest;

/**
 * Created by bingo on 2017/7/24.
 */

public class Category {
    private int id;
    private String categoryName;
    private int categoryCode;

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryName(String categoryName) {

        this.categoryName = categoryName;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
}
