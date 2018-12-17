# This repo is not being maintained! Please use the many other alternatives if it doesn't fit your use case.

[ ![Download](https://api.bintray.com/packages/jakesteam/vidsta/vidsta/images/download.svg) ](https://bintray.com/jakesteam/vidsta/vidsta/_latestVersion)

# Vidsta
#### An easily implementable and customisable Android video player. Try out the [sample app](https://play.google.com/store/apps/details?id=uk.co.jakelee.exposurevideoplayersample)!
----
#### Feature List:
* Plays local and remote files
* Send custom headers
* Customise play / pause / fullscreen etc button images
* Apply colour tint to play / pause / fullscreen images to quickly theme the player
* Customise text colour
* Autoplay
* Autoloop
* Listeners for video play / pause / buffering / error
* Easy to implement
* Min SDK Version: 14

----
#### Setup
##### 1. Import library:
Add `compile 'uk.co.jakelee:vidsta:1.0.0'` to your `build.gradle` dependencies.

##### 2. Create view to display video in:
```java    
<uk.co.jakelee.vidsta.VidstaPlayer
        android:id="@+id/player"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
##### 3. Configure video source, and other options:
```java
        VidstaPlayer player = (VidstaPlayer) findViewById(R.id.player);
        player.setVideoSource("http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4");
        player.setAutoLoop(true);
        player.setAutoPlay(true);
```
##### 4. That's it! You're done!
Adding custom listeners, customising the UI, and other configuration options are detailed below, but you're ready to go!

----
#### Changelog
###### V1.0.0: Initial release, forked from Exposure Video Player.

----
#### Customisation
##### Via XML:
```java    
    <uk.co.jakelee.vidsta.VidstaPlayer
        android:id="@+id/player"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:videoSource = "http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4" <!-- Setting a remote video source -->
        app:textColor = "@color/colorPrimary" <!-- Setting colour for duration / progress text -->
        app:playVideoDrawable = "@drawable/custom_video_play" <!-- Setting drawable for play button -->
        app:pauseVideoDrawable = "@drawable/custom_video_pause" <!-- Setting drawable for pause button -->
        app:retryVideoDrawable = "@drawable/custom_video_retry" <!-- Setting drawable for retry button -->
        app:nextVideoDrawable = "@drawable/custom_video_next" <!-- Setting drawable for next button -->
        app:previousVideoDrawable = "@drawable/custom_video_previous" <!-- Setting drawable for previous button -->
        app:buttonTintColor = "@color/colorAccent" <!-- Setting tint colour for above buttons -->
        app:autoPlay = "true" <!-- Enabling autoplay -->
        app:autoLoop = "true" <!-- Enablimg looping once video is finished -->
        app:setFullScreen = "true" <!-- Enabling fullscreen -->
        app:fullScreenButtonEnabled = "true" <!-- Making fullscreen button clickable -->
        app:fullScreenButtonVisible = "true" <!-- Making fullscreen button visible -->/>
```
##### Programmatically: 
```java
# Setting video source to a remote video
player.setVideoSource("http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4");

# Enabling fullscreen mode
player.setFullScreen(true);
player.setFullScreenButtonVisible(true);

# Adding coloured tint to text / buttons
player.setTextColor(intent.getIntExtra("textColour", Color.WHITE));
player.setButtonTintColor(intent.getIntExtra("iconTint", Color.WHITE));

# Setting new drawables
player.setPlayButtonDrawable(R.drawable.custom_video_play);
player.setPauseButtonDrawable(R.drawable.custom_video_pause);
player.setRetryButtonDrawable(R.drawable.custom_video_retry);
player.setFullscreenEnterDrawable(R.drawable.custom_video_screen_fullscreen_enter);
player.setFullscreenExitDrawable(R.drawable.custom_video_screen_fullscreen_exit);
player.setPreviousButtonDrawable(R.drawable.custom_video_previous);
player.setNextButtonDrawable(R.drawable.custom_video_next);

# Setting up autoloop & autoplay
player.setAutoLoop(true);
player.setAutoPlay(true);

# Setting up custom listeners for events
player.setOnVideoBufferingListener(new VideoStateListeners.OnVideoBufferingListener());
player.setOnVideoErrorListener(new VideoStateListeners.OnVideoErrorListener());
player.setOnVideoFinishedListener(new VideoStateListeners.OnVideoFinishedListener());
player.setOnVideoPausedListener(new VideoStateListeners.OnVideoPausedListener());
player.setOnVideoRestartListener(new VideoStateListeners.OnVideoRestartListener());
player.setOnVideoStartedListener(new VideoStateListeners.OnVideoStartedListener());
player.setOnVideoStoppedListener(new VideoStateListeners.OnVideoStoppedListener());

# Setting a fullscreen listener. Note that starting a new fullscreen activity must be handled in your application for now
player.setOnFullScreenClickListener(new FullScreenClickListener());
```
