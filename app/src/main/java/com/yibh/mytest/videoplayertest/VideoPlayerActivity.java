package com.yibh.mytest.videoplayertest;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.liteplayer.core.ProxyPlayer;
import com.liteplayer.core.subtitle.StDisplayCallBack;
import com.yibh.mytest.R;

public class VideoPlayerActivity extends AppCompatActivity implements StDisplayCallBack {

    private SurfaceView mSurfaceView;
    private ProxyPlayer mProxyPlayer;
    private TextView mSubTextView;  //字幕
    private String video_srt_url = "/mnt/usb/sda1/测试文件/a测试字幕.mp4";
    private String video_sub_url = "/mnt/usb/sda1/测试文件/b测试字幕.mp4";


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mSubTextView = (TextView) findViewById(R.id.subview);

        mProxyPlayer = new ProxyPlayer();
        mProxyPlayer.setSubTitleDisplayCallBack(this);
        mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceView.getHolder().setKeepScreenOn(true);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mProxyPlayer.setPlayerDisplay(surfaceHolder);
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
        mProxyPlayer.runOnActivityPause();
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
        mProxyPlayer.getPlayer().getStatusInfo().mSourceUri = url;
//        mProxyPlayer.setMovieSubTitle(0);
        try {
            mProxyPlayer.playMedia(url, new Runnable() {
                @Override
                public void run() {
                    mProxyPlayer.start();
                }
            });
        } catch (Exception e) {
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

}
