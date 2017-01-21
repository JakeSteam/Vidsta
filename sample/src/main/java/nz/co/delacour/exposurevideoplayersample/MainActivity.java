package nz.co.delacour.exposurevideoplayersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import nz.co.delacour.exposurevidsta.OnThumbnailClickListener;
import nz.co.delacour.exposurevidsta.Vidsta;
import nz.co.delacour.exposurevidsta.VidstaThumbnailView;


public class MainActivity extends AppCompatActivity {

    Vidsta evp;
    VidstaThumbnailView etv;
    String videoSource = "http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //evp = (Vidsta) findViewById(R.id.evp);
        //evp.init(this);
        // This allows for a better fullscreen setting, If you don't have this when the user clicks the notification bar it pops out of fullscreen.
        // This will be fixed later but currently its going to stay like this.
        //evp.setVideoSource(videoSource);
        // Set video source from raw source, evp.setVideoSource("android.resource://" + getPackageName() + "/"+R.raw.big_buck_bunny);
        //evp.setOnVideoListeners(this);
        // If you haven't set autoplay to true you can with start the video with one of these,
        // evp.start();
        // Or you can wait for the user to click the play button on screen.
/*
        evp.setFullScreen();//Enters fullscreen.

        evp.exitFullScreen();//Exits fullscreen. In 1.0.2 the method is set to private so it currently does not work.

        evp.isFullScreen();//Checks to see if layout is fullscreen or not.

        evp.isAutoPlayEnabled();//Checks to see if the autoplay setting is enabled. Default = true.

        evp.setAutoLoop(true);//Sets the autoloop setting. Default = false.

        evp.setAutoPlay(true);//Sets the autoloop setting. Default = true.

        evp.setVideoSource("<LINK TO RAW RESOURCE OR WEB LINK WITH VIDEO>");//Sets video source. This is just like below, but does the Uri.parse("link") for you.

        evp.setVideoSource(Uri.parse("<LINK TO RAW RESOURCE OR WEB LINK WITH VIDEO>"));//Sets video source.

        evp.setOnFullScreenClickListener(this or new <LISTENER HERE>);//Sets the onFullScreenClickListener.

        evp.setOnLayoutCreatedListener(this or new <LISTENER HERE>);//Sets the OnLayoutCreatedListener.

        evp.setOnLayoutPauseListener(this or new <LISTENER HERE>);//Sets the OnLayoutPauseListener.

        evp.setOnLayoutResumedListener(this or new <LISTENER HERE>);//Sets the OnLayoutResumedListener.

        evp.setOnLayoutDestroyedListener(this or new <LISTENER HERE>);//Sets the OnLayoutDestroyedListener.

        evp.setOnVideoStartedListener(this or new <LISTENER HERE>);//Sets the OnVideoStartedListener.

        evp.setOnVideoPausedListener(this or new <LISTENER HERE>);//Sets the OnVideoPausedListener.

        evp.setOnVideoStoppedListener(this or new <LISTENER HERE>);//Sets the OnVideoStoppedListener.

        evp.setOnVideoRestartListener(this or new <LISTENER HERE>);//Sets the OnVideoRestartListener.

        evp.setOnVideoFinishedListener(this or new <LISTENER HERE>);//Sets the OnVideoFinishedListener.

        evp.setOnVideoErrorListener(this or new <LISTENER HERE>);//Sets the OnVideoErrorListener.

        evp.setOnVideoBufferingListener(this or new <LISTENER HERE>);//Sets the OnVideoBufferingListener.

*/
        // If you want to use just the thumbnail which uses the first frame of the video as an place holder thumbnail image.
        etv = (VidstaThumbnailView) findViewById(R.id.etv);
        etv.setVideoSource(videoSource);
        etv.setAutoPlay(true);
        etv.setFullScreen(true);
        etv.setAllowCache(true); test
        //etv.center();

        etv.setOnThumbnailClickListener(new OnThumbnailClickListener() {
            @Override
            public void onClick() {
                Log.e("Thumbnail: ", "Clicked");
                // Add your own on clicks methods here.
                // Start activity with video player etc.
            }
        });
    }

}