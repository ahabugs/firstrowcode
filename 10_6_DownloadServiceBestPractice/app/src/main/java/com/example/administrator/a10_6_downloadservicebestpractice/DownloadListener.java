package com.example.administrator.a10_6_downloadservicebestpractice;

/**
 * Created by Administrator on 17-8-14.
 */

public interface DownloadListener {
    public void onProgress(int progress);
    public void onSuccess();
    public void onFailed();
    public void onPaused();
    public void onCanceled();
}
