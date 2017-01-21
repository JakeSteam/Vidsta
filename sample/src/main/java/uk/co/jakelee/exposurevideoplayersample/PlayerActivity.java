package uk.co.jakelee.exposurevideoplayersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import uk.co.jakelee.exposurevideoplayersample.utils.Listeners;
import uk.co.jakelee.vidsta.Vidsta;

public class PlayerActivity extends AppCompatActivity {
    private Vidsta player;

    private boolean hideNotificationBar = true;
    private boolean loadRemote = false;
    private boolean listenersEnabled = true;
    private boolean autoPlay = true;
    private boolean autoLoop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        player = (Vidsta) findViewById(R.id.player);

        if (hideNotificationBar) {
            player.init(this);
        }

        if (loadRemote) {
            player.setVideoSource("http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4");
        } else {
            player.setVideoSource("android.resource://" + getPackageName() + "/" + R.raw.big_buck_bunny);
        }

        if (listenersEnabled) {
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

        if (autoLoop) {
            player.setAutoLoop(true);
        }

        if (autoPlay) {
            player.setAutoPlay(true);
        }
    }
}
