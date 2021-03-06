package com.liteplayer.core;

import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;

import com.liteplayer.core.audiotrack.AudioTrack;
import com.liteplayer.core.subtitle.StDisplayCallBack;
import com.liteplayer.core.subtitle.SubTitle;

import java.util.List;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class ProxyPlayer {
    private LitePlayer mLitePlayer;
    private PlayerStatusInfo mStatusInfo;
    private OnVideoSizeChangedListener mListener = null;

    public void start() {
        getLitePlayer().start();
    }

    public void pause() {
        getLitePlayer().pause();
    }

    public void release() {
        getLitePlayer().release();
        mLitePlayer = null;
    }

    public boolean isPlaying() {
        return getLitePlayer().isPlaying();
    }

    public int getDuration() {
        return getLitePlayer().getDuration();
    }

    public int getVideoWidth() {
        return getLitePlayer().getVideoWidth();
    }

    public int getVideoHeight() {
        return getLitePlayer().getVideoHeight();
    }

    public float getVideoWidthHeightRate() {
        return getLitePlayer().getVideoWidthHeightRate();
    }

    public int getCurrentPosition() {
        return getLitePlayer().getCurrentPosition();
    }

    public void setPlayerDisplay(SurfaceHolder sh) {
        getLitePlayer().setDisplay(sh);
    }

    public void setMovieSubTitle(int index) {
        getLitePlayer().setMovieSubTitle(index);
    }

    public void setMovieAudioTrack(int index) {
        getLitePlayer().setMovieAudioTrack(index);
    }

    public List<SubTitle> getVideoSubTitles() {
        return getLitePlayer().getVideoSubTitles();
    }

    public List<AudioTrack> getAudioTracks() {
        return getLitePlayer().getAudioTracks();
    }

    public void setSubTitleDisplayCallBack(StDisplayCallBack callback) {
        getLitePlayer().setSubTitleDisplayCallBack(callback);
    }

    public void setOnCompletionListener(OnCompletionListener listener) {
        getLitePlayer().setOnCompletionListener(listener);
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener l) {
        mListener = l;
    }

    public void seekTo(int duration, OnSeekCompleteListener listener) {
        getLitePlayer().seekTo(duration);
        getLitePlayer().setOnSeekCompleteListener(listener);
        start();
    }

    public void playMedia(String uri, final Runnable callBack) throws Exception {
        getLitePlayer().reset();
        getLitePlayer().setDataSource(uri);
        getLitePlayer().prepareAsync();
        getLitePlayer().setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer arg0) {
                if (callBack != null) {
                    callBack.run();
                }
            }
        });
        getLitePlayer().setOnVideoSizeChangedListener(mListener);
    }

    public void runOnActivityPause() {
        mStatusInfo = getLitePlayer().getStatusInfo();
        mStatusInfo.mCurrentPosition = getCurrentPosition();
        release();
    }

    public void runOnActivityResume() throws Exception {
        if (null == getLitePlayer().getStatusInfo())
            return;
        mStatusInfo = getLitePlayer().getStatusInfo();
        Log.i("ProxyPlayer", "runOnActivityResume");
        getLitePlayer().setDisplay(mStatusInfo.mHolder);
        setOnCompletionListener(mStatusInfo.mOnCompletionListener);
        getLitePlayer().setOnTimedTextListener(mStatusInfo.mOnTimedTextListener);
        setSubTitleDisplayCallBack(mStatusInfo.mStDisplayCallBack);
        Log.i("ProxyPlayer", "playMedia");
        playMedia(mStatusInfo.mSourceUri, new Runnable() {
            @Override
            public void run() {
                if (mStatusInfo.mAudioTrackIndex >= 0)
                    setMovieAudioTrack(mStatusInfo.mAudioTrackIndex);
                if (mStatusInfo.mVideoSubTitleIndex >= 0)
                    setMovieSubTitle(mStatusInfo.mVideoSubTitleIndex);
                Log.i("ProxyPlayer", "startseekTo");
                seekTo(mStatusInfo.mCurrentPosition, new OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer arg0) {
                        Log.i("ProxyPlayer", "SeekComplete");
                        mStatusInfo = null;
                        start();
                    }
                });
            }
        });
    }

    private LitePlayer getLitePlayer() {
        if (mLitePlayer == null) {
            mLitePlayer = new LitePlayer();
            mLitePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        return mLitePlayer;
    }

    public LitePlayer getPlayer() {
        return getLitePlayer();
    }

}
