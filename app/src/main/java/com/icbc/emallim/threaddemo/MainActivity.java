
package com.icbc.emallim.threaddemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button asynctask_download;
    Button asynctask_download_cancel;
    MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new 一次 只能运行一次 异步任务
        myAsyncTask = new MyAsyncTask();
        initView();


    }

    /**
     * findview
     */
    private void initView() {

        asynctask_download = findViewById(R.id.asynctask_download);
        asynctask_download.setOnClickListener(this);
        asynctask_download_cancel = findViewById(R.id.asynctask_download_cancel);
        asynctask_download_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.asynctask_download:
                myAsyncTask.execute("");
                break;
            case R.id.asynctask_download_cancel:
                //参数  ture 允许线程中断 ，false 执行完当前线程
                //myAsyncTask.cancel(true);
                myAsyncTask.cancel(false);
                break;
            default:
                break;
        }

    }

    /**
     * 定义 asyncTask
     * <p>
     * Params 参数类型
     * progress 进度类型
     * Result 返回结果类型
     */
    private class MyAsyncTask extends AsyncTask<String, Integer, Long> {

        /**
         * 异步任务执行前会被调用
         * 主线程执行
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("asynctask", "onPreExecute() 方法执行");
        }

        /**
         * 在异步任务执行之后 会被调用
         *
         * @param aLong doInBackground 返回结果
         */
        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Log.i("asynctask", "onPostExecute() 方法执行");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.i("asynctask", "onProgressUpdate(" + values[0] + ") 方法执行");
        }


        @Override
        protected void onCancelled(Long aLong) {
            super.onCancelled(aLong);
            Log.i("asynctask", "onCancelled(" + aLong + ") 方法执行");
        }

        /**
         * 取消异步任务的时候会被执行 此方法被调用是 onPostExecute 不会被执行
         * 主线程执行
         */
        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("asynctask", "onCancelled() 方法执行");
        }


        /**
         * 返回值 为 onPostExecute() 参数值
         * 在线程中执行
         *
         * @param urls
         * @return
         */
        @Override
        protected Long doInBackground(String... urls) {
            Log.i("asynctask", "doInBackground() 方法执行");
            int count = 0;
            try {
                count = 0;
                int length = 1;
                while (count < 10000) {
                    count += length;
                    // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                    publishProgress(count);
                    // 模拟耗时任务
                    //Thread.sleep(50);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ((Integer) count).longValue();
        }
    }
}
