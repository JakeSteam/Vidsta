package uk.co.jakelee.exposurevideoplayersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import uk.co.jakelee.vidsta.listeners.OnThumbnailClickListener;
import uk.co.jakelee.vidsta.VidstaThumbnailView;

public class ThumbnailActivity extends AppCompatActivity {
    String videoSource = "http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4";
    VidstaThumbnailView etv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail);

        etv = (VidstaThumbnailView) findViewById(R.id.thumbnailPlayer);
        etv.setVideoSource(videoSource);
        etv.setAutoPlay(true);
        etv.setFullScreen(true);
        etv.setAllowCache(true);
        //etv.center();

        etv.setOnThumbnailClickListener(new OnThumbnailClickListener() {
            @Override
            public void onClick() {
                Log.e("ThumbnailActivity: ", "Clicked");
                // Add your own on clicks methods here.
                // Start activity with video player etc.
            }
        });
    }
}
