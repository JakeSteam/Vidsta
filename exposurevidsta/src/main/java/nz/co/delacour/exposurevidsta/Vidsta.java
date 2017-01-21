package nz.co.delacour.exposurevidsta;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.ProgressView;

import java.io.IOException;

/**
 * Created by Chris on 11-Sep-16.
 */
public class Vidsta extends FrameLayout implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnVideoSizeChangedListener, View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, MediaPlayer.OnErrorListener {

    private TextureView textureView;
    private MediaPlayer videoPlayer = new MediaPlayer();
    private View controlPlayPause;
    private View controlSeekBar;
    private View videoLoadingView;
    private SeekBar seekBarDuration;
    private ImageButton imgBtnPlayPause;
    private ImageButton imgBtnFullScreenToggle;
    private TextView tvPosition;
    private TextView tvDuration;
    private ProgressView proViewVideoLoading;

    private Drawable playVideoDrawable;
    private Drawable pauseVideoDrawable;
    private Drawable retryVideoDrawable;
    private Drawable nextVideoDrawable;
    private Drawable previousVideoDrawable;
    private Drawable enterFullScreenDrawable;
    private Drawable exitFullScreenDrawable;

    private VideoStateListeners.OnVideoStartedListener onVideoStarted;
    private VideoStateListeners.OnVideoPausedListener onVideoPaused;
    private VideoStateListeners.OnVideoStoppedListener onVideoStopped;
    private VideoStateListeners.OnVideoFinishedListener onVideoFinished;
    private VideoStateListeners.OnVideoBufferingListener onVideoBuffering;
    private VideoStateListeners.OnVideoErrorListener onVideoError;
    private VideoStateListeners.OnVideoRestartListener onVideoRestart;

    private LayoutStates.OnLayoutCreated onLayoutCreated;
    private LayoutStates.OnLayoutResumed onLayoutResumed;
    private LayoutStates.OnLayoutPaused onLayoutPaused;
    private LayoutStates.OnLayoutDestroyed onLayoutDestroyed;
    private FullScreenClickListener fullscreenToggleClickListener;
    private OnBackCalledListener onBackCalled;

    private Surface surface;
    private Uri videoSource;
    private boolean autoPlay;
    private boolean wasPlaying;
    private int initialViewHeight;
    private int initialViewWidth;
    private Integer initialVideoWidth;
    private Integer initialVideoHeight;
    private int videoDuration;
    private boolean isFullScreen = false;
    private boolean isSetFullScreen;

    private Handler handler = new Handler();
    private Configuration layoutConfig;
    private int buttonTintColor = 0;
    private Activity baseAct;
    private boolean setFullScreenButtonEnabled;
    private boolean autoLoop;
    private boolean isPrepared = false;


    public Vidsta(Context context) {
        super(context);
        init(context, null);
    }

    public Vidsta(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Vidsta(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.isInEditMode();
        setBackgroundColor(Color.BLACK);
        if (attrs != null) {
            TypedArray customAttr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Vidsta, 0, 0);
            try {
                String s = customAttr.getString(R.styleable.Vidsta_videoSource);
                if (s != null && !s.trim().isEmpty()) videoSource = Uri.parse(s);
                autoPlay = customAttr.getBoolean(R.styleable.Vidsta_autoPlay, false);
                setFullScreenButtonEnabled = customAttr.getBoolean(R.styleable.Vidsta_fullScreenButtonEnabled, true);
                isSetFullScreen = customAttr.getBoolean(R.styleable.Vidsta_setFullScreen, false);
                isFullScreen = isSetFullScreen;
                autoLoop = customAttr.getBoolean(R.styleable.Vidsta_autoLoop, false);

                buttonTintColor = customAttr.getColor(R.styleable.Vidsta_buttonTintColor, ContextCompat.getColor(getContext(), R.color.colorPrimaryText));
                playVideoDrawable = customAttr.getDrawable(R.styleable.Vidsta_playVideoDrawable);
                pauseVideoDrawable = customAttr.getDrawable(R.styleable.Vidsta_pauseVideoDrawable);
                retryVideoDrawable = customAttr.getDrawable(R.styleable.Vidsta_retryVideoDrawable);
                nextVideoDrawable = customAttr.getDrawable(R.styleable.Vidsta_nextVideoDrawable);
                previousVideoDrawable = customAttr.getDrawable(R.styleable.Vidsta_previousVideoDrawable);
            } finally {
                customAttr.recycle();
            }
        } else {
            autoLoop = false;
            autoPlay = false;
            isSetFullScreen = false;
            setFullScreenButtonEnabled = true;
            buttonTintColor = ContextCompat.getColor(getContext(), R.color.colorPrimaryText);
        }

        if (playVideoDrawable == null)
            playVideoDrawable = ContextCompat.getDrawable(context, R.drawable.video_play);
        if (pauseVideoDrawable == null)
            pauseVideoDrawable = ContextCompat.getDrawable(context, R.drawable.video_pause);
        if (retryVideoDrawable == null)
            retryVideoDrawable = ContextCompat.getDrawable(context, R.drawable.video_retry);
        //if (nextVideoDrawable == null)
        //    nextVideoDrawable = ContextCompat.getDrawable(context, R.drawable.video_next);
        //if (previousVideoDrawable == null)
        //    previousVideoDrawable = ContextCompat.getDrawable(context, R.drawable.video_previous);

        if (enterFullScreenDrawable == null)
            enterFullScreenDrawable = ContextCompat.getDrawable(context, R.drawable.video_screen_fullscreen_enter);
        if (exitFullScreenDrawable == null)
            exitFullScreenDrawable = ContextCompat.getDrawable(context, R.drawable.video_screen_fullscreen_exit);
    }

    public void start() {
        if (videoPlayer == null || !isPrepared) return;
        videoPlayer.start();
        imgBtnPlayPause.setImageDrawable(pauseVideoDrawable);
        handler.post(seekBarProgress);
        if (onVideoStarted != null) onVideoStarted.OnVideoStarted(this);
    }

    public void pause() {
        if (videoPlayer.isPlaying()) {
            videoPlayer.pause();
            imgBtnPlayPause.setImageDrawable(playVideoDrawable);
            handler.removeCallbacks(seekBarProgress);
        }
        if (onVideoPaused != null) onVideoPaused.OnVideoPaused(this);
    }

    public void stop() {
        videoPlayer.stop();
        imgBtnPlayPause.setImageDrawable(playVideoDrawable);
        handler.removeCallbacks(seekBarProgress);
        if (onVideoStopped != null) onVideoStopped.OnVideoStopped(this);
    }

    public void restart() {
        videoPlayer.stop();
        if (autoPlay) {
            start();
        } else {
            imgBtnPlayPause.setImageDrawable(playVideoDrawable);
        }
        handler.removeCallbacks(seekBarProgress);
        if (onVideoRestart != null) onVideoRestart.OnVideoRestart(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        videoPlayer.setOnPreparedListener(this);
        videoPlayer.setOnBufferingUpdateListener(this);
        videoPlayer.setOnCompletionListener(this);
        videoPlayer.setOnErrorListener(this);
        videoPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        textureView = new TextureView(getContext());
        addView(textureView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout rl = new LinearLayout(getContext());
        addView(rl, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rl.setId(R.id.touchId);
        rl.setOnClickListener(this);

        LayoutInflater li = LayoutInflater.from(getContext());
        videoLoadingView = li.inflate(R.layout.layout_video_loading_view, this, false);
        addView(videoLoadingView);

        controlPlayPause = li.inflate(R.layout.layout_video_img_button_play_pause, this, false);
        controlSeekBar = li.inflate(R.layout.layout_video_seek_bar, this, false);
        FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) controlPlayPause.getLayoutParams();
        FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) controlSeekBar.getLayoutParams();

        lp1.gravity = Gravity.CENTER;
        lp2.gravity = Gravity.BOTTOM;

        addView(controlPlayPause, lp1);
        addView(controlSeekBar, lp2);

        imgBtnPlayPause = (ImageButton) controlPlayPause.findViewById(R.id.imageButtonPlayPauseRetry);
        imgBtnFullScreenToggle = (ImageButton) controlSeekBar.findViewById(R.id.imageButtonFullScreenToggle);
        tvPosition = (TextView) controlSeekBar.findViewById(R.id.textViewPosition);
        tvDuration = (TextView) controlSeekBar.findViewById(R.id.textViewDuration);
        proViewVideoLoading = (ProgressView) videoLoadingView.findViewById(R.id.proViewVideoLoading);
        seekBarDuration = (SeekBar) controlSeekBar.findViewById(R.id.seekBarDuration);
        imgBtnPlayPause.setImageDrawable(playVideoDrawable);

        imgBtnPlayPause.setOnClickListener(this);
        imgBtnFullScreenToggle.setOnClickListener(this);
        textureView.setSurfaceTextureListener(this);
        seekBarDuration.setOnSeekBarChangeListener(this);
        controlPlayPause.setVisibility(INVISIBLE);
        controlSeekBar.setVisibility(INVISIBLE);
        proViewVideoLoading.start();
        setUpVideoPlayer();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        initialViewWidth = width;
        initialViewHeight = height;
        surface = new Surface(surfaceTexture);
        videoPlayer.setSurface(surface);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        if ((videoPlayer.getVideoWidth() == 0) || (videoPlayer.getVideoHeight() == 0)) return;
        adjustView(width, height, videoPlayer.getVideoWidth(), videoPlayer.getVideoHeight());
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        if (onVideoBuffering != null) onVideoBuffering.OnVideoBuffering(this, i);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        handler.removeCallbacks(seekBarProgress);
        Log.e("autoloop", "" + autoLoop);
        imgBtnPlayPause.setImageDrawable(playVideoDrawable);
        if (onVideoFinished != null) onVideoFinished.OnVideoFinished(this);
    }

    public void setOnBackCalled(OnBackCalledListener onBackCalled) {
        this.onBackCalled = onBackCalled;
    }

    public void onBackCalled() {
        if (onBackCalled != null) onBackCalled.onBackCalled();
        onLayoutDestroyed();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        isPrepared = true;
        videoDuration = mediaPlayer.getDuration();
        seekBarDuration.setProgress(0);
        seekBarDuration.setMax(videoDuration);
        tvPosition.setText(VidstaUtil.getTimeString(0, false));
        tvDuration.setText(VidstaUtil.getTimeString(videoDuration, true));
        proViewVideoLoading.stop();
        proViewVideoLoading.setVisibility(INVISIBLE);
        removeView(videoLoadingView);
        videoPlayer.setOnVideoSizeChangedListener(this);

        if (initialVideoWidth == null && initialVideoHeight == null) {
            initialVideoWidth = videoPlayer.getVideoWidth();
            initialVideoHeight = videoPlayer.getVideoHeight();
        }

        if (autoPlay) {
            start();
        } else {
            controlPlayPause.setVisibility(VISIBLE);
            controlSeekBar.setVisibility(VISIBLE);
            start();
            pause();
        }
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
        adjustView(initialViewWidth, initialViewHeight, width, height);
    }

    private void adjustView(int width1, int height1, int width2, int height2) {
        final double r = (double) height2 / width2;
        int nw, nH;

        if (height1 > (int) (width1 * r)) {
            nw = width1;
            nH = (int) (width1 * r);
        } else {
            nw = (int) (height1 / r);
            nH = height1;
        }

        final int moveX = (width1 - nw) / 2;
        final int moveY = (height1 - nH) / 2;

        final Matrix textureMatrix = new Matrix();
        textureView.getTransform(textureMatrix);
        textureMatrix.setScale((float) nw / width1, (float) nH / height1);
        textureMatrix.postTranslate(moveX, moveY);
        textureView.setTransform(textureMatrix);
    }

    public void setVideoSource(String str) {
        setVideoSource(Uri.parse(str));
    }

    public void setVideoSource(Uri uri) {
        videoSource = uri;
        setUpVideoPlayer();
    }

    private void setUpVideoPlayer() {
        if (videoPlayer == null || videoSource == null) return;
        videoPlayer.setSurface(surface);
        try {
            if (videoSource.getScheme() != null && (videoSource.getScheme().equals("http") || videoSource.getScheme().equals("https")))
                videoPlayer.setDataSource(videoSource.toString());
            else videoPlayer.setDataSource(getContext(), videoSource);
            videoPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isSetFullScreen) {
            imgBtnFullScreenToggle.setVisibility(GONE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tvDuration.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }

        setAutoLoop(autoLoop);

        if (buttonTintColor != ContextCompat.getColor(getContext(), R.color.colorPrimaryText)) {
            ColorDrawable dr = new ColorDrawable(buttonTintColor);
            playVideoDrawable.setColorFilter(dr.getColor(), PorterDuff.Mode.MULTIPLY);
            pauseVideoDrawable.setColorFilter(dr.getColor(), PorterDuff.Mode.MULTIPLY);
            enterFullScreenDrawable.setColorFilter(dr.getColor(), PorterDuff.Mode.MULTIPLY);
            exitFullScreenDrawable.setColorFilter(dr.getColor(), PorterDuff.Mode.MULTIPLY);
            imgBtnPlayPause.setImageDrawable(playVideoDrawable);
        }
    }

    public void setAutoLoop(boolean autoLoop) {
        this.autoLoop = autoLoop;
        if (autoLoop) videoPlayer.setLooping(true);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imageButtonPlayPauseRetry) {
            if (isPlaying()) {
                pause();
            } else {
                start();
                toggleControls();
            }
        } else if (id == R.id.touchId) {
            toggleControls();
        } else if (id == R.id.imageButtonFullScreenToggle) {
            if (isFullScreen) {
                onFullScreenToggleClick(false);
            } else {
                onFullScreenToggleClick(true);
            }
        }
    }

    public boolean isPlaying() {
        return (videoPlayer != null && videoPlayer.isPlaying());
    }

    public boolean controlsShowing() {
        return (controlPlayPause.getVisibility() == VISIBLE);
    }

    public void toggleControls() {
        if (videoPlayer == null) return;
        else if (controlsShowing()) {
            animateControls(controlPlayPause, 1f, 0f, INVISIBLE);
            animateControls(controlSeekBar, 1f, 0f, INVISIBLE);
        } else {
            animateControls(controlPlayPause, 0f, 1f, VISIBLE);
            animateControls(controlSeekBar, 0f, 1f, VISIBLE);
        }
    }


    public void animateControls(final View v, float f1, float f2, final int visibility) {
        v.animate().cancel();
        v.setAlpha(f1);
        v.setVisibility(VISIBLE);
        v.animate().alpha(f2)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(visibility);
                    }
                }).start();
    }

    private void setFullScreenToggle(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        if (isFullScreen) setFullScreen();
        else {
            exitFullScreen();
        }
    }

    public void setFullScreen(boolean isFullScreen) {
        this.isSetFullScreen = isFullScreen;
        this.isFullScreen = isFullScreen;
        if (isFullScreen) setFullScreen();
        else {
            exitFullScreen();
        }
    }


    public void exitFullScreen() {
        this.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        imgBtnFullScreenToggle.setImageDrawable(enterFullScreenDrawable);
        if (baseAct != null) {
            baseAct.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public void setFullScreen() {
        imgBtnFullScreenToggle.setImageDrawable(exitFullScreenDrawable);
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        if (baseAct != null) {
            //baseAct.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            baseAct.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            Toast.makeText(getContext(), "FullScreen will not work properly, as no Activity has been initialized.", Toast.LENGTH_LONG).show();
        }

        if (isSetFullScreen) {
            imgBtnFullScreenToggle.setVisibility(GONE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tvDuration.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
    }


    @Override
    public void onWindowSystemUiVisibilityChanged(int visible) {
        super.onWindowSystemUiVisibilityChanged(visible);

    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public boolean isAutoPlayEnabled() {
        return autoPlay;
    }

    public void playVideoFrom(int i) {
        if (videoPlayer == null) return;
        videoPlayer.seekTo(i);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b) playVideoFrom(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        wasPlaying = isPlaying();
        if (wasPlaying) videoPlayer.pause();

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (wasPlaying) videoPlayer.start();
    }

    private final Runnable seekBarProgress = new Runnable() {
        @Override
        public void run() {
            if (videoPlayer == null || handler == null || seekBarDuration == null) return;
            int videoPos = videoPlayer.getCurrentPosition();
            int videoLength = videoPlayer.getDuration();

            tvPosition.setText(VidstaUtil.getTimeString(videoPos, false));
            tvDuration.setText(VidstaUtil.getTimeString((videoLength - videoPos), true));
            seekBarDuration.setProgress(videoPos);
            seekBarDuration.setMax(videoLength);
            handler.postDelayed(this, 100);
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            onLayoutResumed();
        } else {
            onLayoutPaused();
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onLayoutCreated();
    }

    @Override
    protected void onDetachedFromWindow() {
        onLayoutDestroyed();
        super.onDetachedFromWindow();

    }

    public void setOnLayoutCreatedListener(@NonNull LayoutStates.OnLayoutCreated onLayoutCreated) {
        this.onLayoutCreated = onLayoutCreated;
    }

    public void setOnLayoutResumedListener(@NonNull LayoutStates.OnLayoutResumed onLayoutResumed) {
        this.onLayoutResumed = onLayoutResumed;
    }

    public void setOnLayoutPauseListener(@NonNull LayoutStates.OnLayoutPaused onLayoutPaused) {
        this.onLayoutPaused = onLayoutPaused;
    }

    public void setOnLayoutDestroyedListener(@NonNull LayoutStates.OnLayoutDestroyed onLayoutDestroyed) {
        this.onLayoutDestroyed = onLayoutDestroyed;
    }

    public void onLayoutCreated() {
        if (onLayoutCreated != null) onLayoutCreated.onCreated();
    }

    public void onLayoutResumed() {
        if (onLayoutResumed != null) onLayoutResumed.onResume();
        if (isSetFullScreen) {
            //if (baseAct != null)
            //    if (baseAct.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            //        baseAct.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            this.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            this.getResources().getConfiguration().orientation = Configuration.ORIENTATION_LANDSCAPE;
        }
    }

    public void onLayoutPaused() {
        if (onLayoutPaused != null) onLayoutPaused.onPaused();
        this.pause();
    }

    public void onLayoutDestroyed() {
        handler.removeCallbacks(seekBarProgress);
        if (onLayoutDestroyed != null) onLayoutDestroyed.onDestroy();
        this.stop();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int ii) {
        if (i == -38) return false;
        if (onVideoError != null) onVideoError.OnVideoError(i, ii);
        return false;
    }

    public void init(Activity act) {
        this.baseAct = act;
    }

//    public void init(Context ctx) {
//        this.ctx = ctx;
//    }


    public void setOnFullScreenClickListener(@NonNull FullScreenClickListener listener) {
        this.fullscreenToggleClickListener = listener;
    }

    private void onFullScreenToggleClick(boolean isFullscreen) {
        if (fullscreenToggleClickListener != null)
            fullscreenToggleClickListener.onToggleClick(isFullscreen);
        setFullScreenToggle(isFullscreen);
    }

    public void setOnVideoStartedListener(VideoStateListeners.OnVideoStartedListener onVideoStarted) {
        this.onVideoStarted = onVideoStarted;
    }

    public void setOnVideoRestartListener(VideoStateListeners.OnVideoRestartListener onVideoRestart) {
        this.onVideoRestart = onVideoRestart;
    }

    public void setOnVideoErrorListener(VideoStateListeners.OnVideoErrorListener onVideoError) {
        this.onVideoError = onVideoError;
    }

    public void setOnVideoBufferingListener(VideoStateListeners.OnVideoBufferingListener onVideoBuffering) {
        this.onVideoBuffering = onVideoBuffering;
    }

    public void setOnVideoFinishedListener(VideoStateListeners.OnVideoFinishedListener onVideoFinished) {
        this.onVideoFinished = onVideoFinished;
    }

    public void setOnVideoStoppedListener(VideoStateListeners.OnVideoStoppedListener onVideoStopped) {
        this.onVideoStopped = onVideoStopped;
    }

    public void setOnVideoPausedListener(VideoStateListeners.OnVideoPausedListener onVideoPaused) {
        this.onVideoPaused = onVideoPaused;
    }
}
