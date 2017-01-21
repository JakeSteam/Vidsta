package nz.co.delacour.exposurevidsta;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Created by Chris on 17-Sep-16.
 */
public class VidstaThumbnailView extends FrameLayout implements View.OnClickListener {

    private Bitmap bitmapImage;
    private View imageButPlayView;
    private ImageButton imageButPlay;
    private ImageView imgV;
    private String videoSource;
    private Drawable playVideoDrawable;

    private OnThumbnailClickListener onThumbnailClickListener;
    private boolean allowCache;
    private boolean setFullScreen;
    private boolean autoPlay;
    private boolean disableStandalone;
    @SuppressWarnings("FieldCanBeLocal")
    private String thumbnailCache = "thumbnails";
    private File cacheDir;
    private File cacheFile;
    private FileInputStream bitmapFileIn;
    private boolean isClicked = false;

    public VidstaThumbnailView(Context context) {
        super(context);
        init(context, null);
    }

    public VidstaThumbnailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VidstaThumbnailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray customAttr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Vidsta, 0, 0);
            try {
                String s = customAttr.getString(R.styleable.Vidsta_videoSource);
                if (s != null && !s.trim().isEmpty()) videoSource = s;
                allowCache = customAttr.getBoolean(R.styleable.VidstaThumbnailView_allowCache, true);
                //setFullScreen = customAttr.getBoolean(R.styleable.ExposureThumbnailView_allowCache, true);
                //autoPlay = customAttr.getBoolean(R.styleable.ExposureThumbnailView_allowCache, true);
                //disableStandalone = customAttr.getBoolean(R.styleable.ExposureThumbnailView_allowCache, false);
            } finally {
                customAttr.recycle();
            }
        } else {
            videoSource = null;
            allowCache = true;
            setFullScreen = true;
            autoPlay = true;
            disableStandalone = false;
        }

        if (playVideoDrawable == null)
            playVideoDrawable = ContextCompat.getDrawable(context, R.drawable.video_play);

        cacheDir = new File(getContext().getCacheDir(), thumbnailCache);
    }

    public void setVideoSource(String source) {
        this.videoSource = source;
        setThumbnailImage(source);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imgV = new ImageView(getContext());
        addView(imgV);

        LayoutInflater li = LayoutInflater.from(getContext());
        imageButPlayView = li.inflate(R.layout.layout_video_img_button_play_pause, this, false);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) imageButPlayView.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.width = imgV.getLayoutParams().width;
        lp.height = imgV.getLayoutParams().height;
        addView(imageButPlayView, lp);

        imageButPlay = (ImageButton) imageButPlayView.findViewById(R.id.imageButtonPlayPauseRetry);
        imageButPlay.setImageDrawable(playVideoDrawable);
        imageButPlayView.setOnClickListener(this);
        setThumbnailImage(getVideoSource());
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public void setAllowCache(boolean allowCache) {
        this.allowCache = allowCache;
    }

    public void disableStandalonePlayer(boolean disableStandalone) {
        this.disableStandalone = disableStandalone;
    }


    public void setFullScreen(boolean setFullScreen) {
        this.setFullScreen = setFullScreen;
    }

    public void setThumbnailImage(String videoSource) {
        if (videoSource != null) {
            cacheFile = new File(cacheDir, formatSource(videoSource));
            imgV.setImageBitmap(getBitmapImage(videoSource));
        }
    }

    public void centerCrop() {
        setThumbnailImageScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void centerInside() {
        setThumbnailImageScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    public void fitCenter() {
        setThumbnailImageScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    public void setThumbnailImageScaleType(ImageView.ScaleType st) {
        if (imgV != null)
            imgV.setScaleType(st);
    }

    public void setThumbnailImageMatrix(Matrix matrix) {
        if (imgV != null)
            imgV.setImageMatrix(matrix);
    }

    public ImageView getThumbnailImageView() {
        if (imgV != null)
            return imgV;
        else return null;
    }



    public Drawable getDrawable() {
        if (imgV.getDrawable() != null)
            return imgV.getDrawable();
        else return null;
    }

    private Bitmap getBitmapImage(String videoSource) {
        if (videoSource == null) return null;
        Bitmap bitmap = null;
        try {
            if (allowCache) {
                if (cacheFile.exists()) {
                    bitmapFileIn = new FileInputStream(cacheFile);
                    bitmap = BitmapFactory.decodeStream(bitmapFileIn);
                } else {
                    bitmap = getBitmap(videoSource);
                    saveBitmapToCache(bitmap);
                }
            } else {
                bitmap = getBitmap(videoSource);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    private void saveBitmapToCache(Bitmap bitmap) {
        try {
            cacheFile.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(cacheFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }

    private String formatSource(String str) {
        return str.replace("http:", "").replace("https:", "").replaceAll("/", "");
    }

    @Override
    public void onClick(View view) {
        if (onThumbnailClickListener != null) onThumbnailClickListener.onClick();
        if (!disableStandalone) {
            Intent intent = new Intent(getContext(), VidstaStandalonePlayer.class);
            intent.putExtra("videoSource", videoSource);
            intent.putExtra("autoPlay", autoPlay);
            intent.putExtra("setFullScreen", setFullScreen);
            getContext().startActivity(intent);
        }
    }

    public void setOnThumbnailClickListener(OnThumbnailClickListener onThumbnailClickListener) {
        this.onThumbnailClickListener = onThumbnailClickListener;
    }

    private Bitmap getBitmap(String videoSource) {
        MediaMetadataRetriever r = new MediaMetadataRetriever();
        r.setDataSource(videoSource, new HashMap<String, String>());
        return r.getFrameAtTime(1);
    }
}
