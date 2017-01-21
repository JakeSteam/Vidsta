###Donate Here: [![Stories in Ready](https://button.flattr.com/flattr-badge-large.png)](https://flattr.com/profile/De-La-Cour)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Exposure--Video--Player-green.svg?style=true)](https://android-arsenal.com/details/1/4345)
[![Codewake](https://www.codewake.com/badges/ask_question.svg)](https://www.codewake.com/p/exposure-core)

<a href='https://bintray.com/delacour/maven/exposurevideoplayer?source=watch' alt='Get automatic notifications about new "exposurevideoplayer" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a>

####Note that this Repo is still in development. 
####So if you find any bugs or things that dont work the way they should please report them, 
####Thank you.

# Exposure Vidsta
####Custom Android video player API library. Simple and integrate with your apps quickly and efficiently.


<p align="center">
<img src="https://github.com/UrbanChrisy/Exposure-Video-Player/blob/master/screenshots/screenshot_1.png" height="640px" width="360px">
<img src="https://github.com/UrbanChrisy/Exposure-Video-Player/blob/master/screenshots/screenshot_2.png" height="640px" width="360px"> </p>

#Whats New:
#New Name
##Renamed the library to Exposure Vidsta. While doing so I have reset version control back to v1.0.0.
##Keep in mind the old gradle link will still work I am not going to get rid of it. But its version will stay the same.
##But new versions will use new naming.

#Old Name
##Version 1.0.5
###-Fixed constraint layout error as I was including it in the gradle but I wasnt using it.

##Version 1.0.4
###-Fullscreen can now be rotated.
###-Thumbnail Image can now be scaled and changed with ImageView matrix. Plus the abiliy to grab the imageview from the view.

##Version 1.0.3
###-Thumbnaiul View.
###-Standalone Player - Has its own activity.
###-Fullscreen Button working.
###-Fullscreen Button is now able to be disabled.
###-Autoplay working.
###-Removed auto hide controls on play. Will add in again at later date.
###-Video listners working. Also they have changed from implementing them all, so implementing select listeners you choose.
###-Layout created, destryoed, paused or resumed listeners are now working.

#Feature List:
####-UI is very similar to what youtube uses as such.
####-Simple and easy to use and setup.
####-Allot of feature to be still implemented.
####-Made around the base MediaPlayer API. So it can play anything and on most devices.

#Min SDK Version: SDK < 14

#More detailed Wiki found [here](https://github.com/UrbanChrisy/Exposure-Vidsta/wiki).

#Video Player Setup
### Firstly Add this to your layout

```XML
<nz.co.delacour.exposurevidsta.Vidsta
    android:id="@+id/evp"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
### If you have not change any layout settings in the above snippet. The basic base video player will have theses settings.
#####-Autoplay set to false (Disabled).
#####-Auto Fullscreen and Fullscreen set to false (Disabled).
#####-Color tint of play and pause button set to white. (Default)

###Then... You should be able to work it out from here. 
####Wiki is still begin developed so if you have any issues please feel free to email me on chris@delacour.co.nz.

```Java
 public class MainActivity extends AppCompatActivity implements VideoListeners {

    Vidsta vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vid = (Vidsta) findViewById(R.id.evp);
        vid.init(this);//You must include a Activity here, for the video player will not function correctly.
        vid.setVideoSource(videoSource);
        // Set video source from raw source, vid.setVideoSource("android.resource://" + getPackageName() + "/"+R.raw.big_buck_bunny);
        vid.setOnVideoListeners(this);
        // If you haven't set autoplay to true you can with start the video with one of these,
        // vid.start();
        // Or you can wait for the user to click the play button on screen.
        ...
    }
}
```
###NOTE: In your code you must include the init(<YOUR ACTIVITY>) method, for the video player to function correctly.

#Thumbnail View Setup
```XML
    <nz.co.delacour.exposurevidsta.VidstaThumbnailView
        android:id="@+id/etv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

###And again same as above, bit slightly different.

```Java
    VidstaThumbnailView thumb;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //Thumbnail view uses the first frame(first milisecond) of video given as source.
            thumb = (VidstaThumbnailView) findViewById(R.id.thumb);
            thumb.setVideoSource("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
            thumb.setAutoPlay(true);//Set your video settings if you need them. They carry over the the standalone player.
            thumb.setFullScreen(false);
            thumb.disableStandalonePlayer(true);//Disables standalone player.
            thumb.setOnThumbnailClickListener(new OnThumbnailClickListener() {
                @Override
                public void onClick() {
                    Log.e("Thumbnail: ", "Clicked");
                    //Standlone activity starts here.
                    //Add your own on clicks methods here.
                    //Start activity with video player etc.
                }
            });
        }
        ...
```

#Things to be added and/or finished in given time.
####-setDisplayHomeAsUpEnabled toolbar action.
####-Automatically resize, little broken at the moment.
####-Batch preloading.
####-Ability to load next video.
####-Override mediaplayer buffer to sort out data saver mode.
####-Ability to take away seekbar and timers.
####-Allot more to come also.


