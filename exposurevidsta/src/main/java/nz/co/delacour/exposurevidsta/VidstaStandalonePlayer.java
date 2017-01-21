package nz.co.delacour.exposurevidsta;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by Chris on 17-Sep-16.
 */
public class VidstaStandalonePlayer extends Activity {

    Vidsta evp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_standalone_player);
        evp = (Vidsta) findViewById(R.id.evpStandalone);
        evp.init(this);
        Bundle b = getIntent().getExtras();
        evp.setVideoSource(b.getString("videoSource"));
        evp.setAutoPlay(b.getBoolean("autoPlay"));
        evp.setFullScreen(b.getBoolean("setFullScreen"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        evp.stop();
        finish();
    }
}
