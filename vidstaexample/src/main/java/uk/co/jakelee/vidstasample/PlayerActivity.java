package uk.co.jakelee.vidstasample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import uk.co.jakelee.vidsta.VidstaPlayer;
import uk.co.jakelee.vidstasample.utils.Listeners;

import static android.view.View.GONE;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();

        boolean useSmallPlayer = intent.getBooleanExtra("smallPlayer", false);
        VidstaPlayer player = (VidstaPlayer) findViewById(useSmallPlayer ? R.id.playerSmall : R.id.player);
        findViewById(R.id.playerSmall).setVisibility(useSmallPlayer ? View.VISIBLE : GONE);
        findViewById(R.id.player).setVisibility(useSmallPlayer ? GONE : View.VISIBLE);

        player.setVideoSource(intent.getBooleanExtra("remote", false) ?
                "http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4" :
                "android.resource://" + getPackageName() + "/" + R.raw.big_buck_bunny);
        player.setFullScreen(intent.getBooleanExtra("fullscreen", false));
        player.setFullScreenButtonVisible(intent.getBooleanExtra("fullscreenButton", false));

        if (!intent.getBooleanExtra("noLogging", false)) {
            Listeners listeners = new Listeners((LinearLayout)findViewById(R.id.listenerMessages));
            player.setOnVideoBufferingListener(listeners.bufferingListener);
            player.setOnVideoErrorListener(listeners.errorListener);
            player.setOnVideoFinishedListener(listeners.finishedListener);
            player.setOnVideoPausedListener(listeners.pausedListener);
            player.setOnVideoRestartListener(listeners.restartListener);
            player.setOnVideoStartedListener(listeners.startedListener);
            player.setOnVideoStoppedListener(listeners.stoppedListener);
            player.setOnFullScreenClickListener(listeners.fullScreenListener);
        }

        if (intent.getBooleanExtra("textColourEnabled", false)) {
            player.setTextColor(intent.getIntExtra("textColour", Color.WHITE));
        }

        if (intent.getBooleanExtra("iconTintEnabled", false)) {
            player.setButtonTintColor(intent.getIntExtra("iconTint", Color.WHITE));
        }

        if (intent.getBooleanExtra("customIcons", false)) {
            player.setPlayButtonDrawable(R.drawable.custom_video_play);
            player.setPauseButtonDrawable(R.drawable.custom_video_pause);
            player.setRetryButtonDrawable(R.drawable.custom_video_retry);
            player.setFullscreenEnterDrawable(R.drawable.custom_video_screen_fullscreen_enter);
            player.setFullscreenExitDrawable(R.drawable.custom_video_screen_fullscreen_exit);
            player.setPreviousButtonDrawable(R.drawable.custom_video_previous);
            player.setNextButtonDrawable(R.drawable.custom_video_next);
        }

        if (intent.getBooleanExtra("controls", false)) {
            // Make a bar of controls
        }

        if (intent.getBooleanExtra("autoloop", false)) {
            player.setAutoLoop(true);
        }

        if (intent.getBooleanExtra("autoplay", false)) {
            player.setAutoPlay(true);
        }
    }
}
