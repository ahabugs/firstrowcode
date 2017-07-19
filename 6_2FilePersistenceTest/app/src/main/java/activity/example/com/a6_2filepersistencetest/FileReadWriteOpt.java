package activity.example.com.a6_2filepersistencetest;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
/*import java.io.FileNotFoundException;*/
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by bingo on 2017/7/13.
 */

public class FileReadWriteOpt{
    private String fileName;
    private Context context;

    public FileReadWriteOpt(Context context, String fileName) {
        this.fileName = fileName;
        this.context = context;
    }

    public String load() {
        /*
        * =================================
        * in端    流(理解成类似管道)    out端
        * =================================
        *
        * 对于管道而言，它读，数据就是从in端进来；
        * 它写，数据就从out端出去了。所以FileOutputStream就是写操作，
        * FileInputStream就是读操作
        * FileInputStream-->InputStreamReader-->BufferedReader-->BufferedReader reader()
        * 文件输入流-->输入流阅读器-->缓存阅读器-->调用缓存阅读器的读函数。
        * */
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            fileInputStream = context.openFileInput(this.fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = "";
                /*while ((line=bufferedReader.readLine()) != null)*/
            while ((line=bufferedReader.readLine()) != null)
                stringBuilder.append(line);
        } catch (IOException io_e) {
            io_e.printStackTrace();
        } finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return stringBuilder.toString();
    }

    public void save(String data) {
        /*
        * FileOutputStream-->OutputStreamWriter-->BufferedWriter-->BufferedWriter writer()
        * 文件输出流-->输出流编写器-->缓存编写器-->调用缓存编写器的写函数。
        * */
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = context.openFileOutput(this.fileName, Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(data);
        } catch (IOException io_e) {
            io_e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
