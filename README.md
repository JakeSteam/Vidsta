# Vidsta
#### An easily implementable and customisable Android video player
----
#### Whats New:
###### V1.0.0: Initial release, forked from Exposure Video Player.
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
* Min SDK Version: 16
----
#### Setup
##### 1. Import library:
Add `compile 'uk.co.jakelee.vidsta:1.0.0'` to your `build.gradle` dependencies.

##### 2. Create view to display video in:

```    
<uk.co.jakelee.vidsta.VidstaPlayer
        android:id="@+id/player"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
##### 3. Configure video source, and other options:
```
        VidstaPlayer player = (VidstaPlayer) findViewById(R.id.player);
        player.setVideoSource("http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4");
        player.setAutoLoop(true);
        player.setAutoPlay(true);
```
##### 4. That's it! You're done!
Adding custom listeners, customising the UI, and other configuration options are detailed below, but you're ready to go!
----



