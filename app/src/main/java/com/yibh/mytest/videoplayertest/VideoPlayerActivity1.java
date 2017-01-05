package com.yibh.mytest.videoplayertest;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.liteplayer.core.subtitle.StDisplayCallBack;
import com.yibh.mytest.R;

import java.io.IOException;

/**
 * 测试MediaPlayer
 */
public class VideoPlayerActivity1 extends AppCompatActivity implements StDisplayCallBack {

    private SurfaceView mSurfaceView;
    private MediaPlayer mProxyPlayer;
    private TextView mSubTextView;  //字幕
//    private String video_srt_url = "/mnt/usb/sda1/测试文件/a测试字幕.mp4";
    private String video_srt_url = "/mnt/usb/94D5-4C4A/测试文件/Beauty.mpg";
//    private String video_sub_url = "/mnt/usb/sda1/测试文件/b测试字幕.mp4";
    private String video_sub_url = "/mnt/usb/94D5-4C4A/测试文件/b测试字幕.mp4";
    //    private String video_ass_url = "/mnt/usb/sda1/测试文件/c测试字幕.mp4";
//    private String video_ass_url = "/mnt/usb/sdb1/3gp.3gp";
    private String video_ass_url = "/storage/udisk0/内嵌字幕/百思不得姐....wmv";


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mSubTextView = (TextView) findViewById(R.id.subview);

        mProxyPlayer = new MediaPlayer();
//        mProxyPlayer.setSubTitleDisplayCallBack(this);
        mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceView.getHolder().setKeepScreenOn(true);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
//                mProxyPlayer.setPlayerDisplay(surfaceHolder);
                mProxyPlayer.setDisplay(surfaceHolder);
//                playVideo();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mProxyPlayer.runOnActivityPause();
    }

    @Override
    public void onSubTitleChanging() {

    }

    @Override
    public void showSubTitleText(final String text) {
        Log.w("字幕 ", text);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSubTextView.setText(text);
            }
        });
    }

    public void playVideo(String url) {
        try {
            mProxyPlayer.reset();
//            mProxyPlayer.release();
            mProxyPlayer.setDataSource(url);
            mProxyPlayer.prepareAsync();
            mProxyPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mProxyPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放对应有srt字幕的视频
     *
     * @param view
     */
    public void srtClick(View view) {
        playVideo(video_srt_url);
    }

    /**
     * 播放对应有sub字幕的视频
     *
     * @param view
     */
    public void subClick(View view) {
        playVideo(video_sub_url);
    }

    public void assClick(View view) {
        playVideo(video_ass_url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProxyPlayer.stop();
    }
}
