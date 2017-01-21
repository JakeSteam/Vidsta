package uk.co.jakelee.vidstasample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import uk.co.jakelee.vidstasample.utils.Listeners;
import uk.co.jakelee.vidsta.Vidsta;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        Vidsta player = (Vidsta) findViewById(R.id.player);
        player.setVideoSource(intent.getBooleanExtra("remote", false) ?
                "http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4" :
                "android.resource://" + getPackageName() + "/" + R.raw.big_buck_bunny);
        player.setFullScreen(intent.getBooleanExtra("fullscreen", false));

        if (!intent.getBooleanExtra("nologging", false)) {
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
