package uk.co.jakelee.vidstasample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchPlayer(View v) {
        startActivity(new Intent(this, PlayerActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                .putExtra("autoplay", ((CheckBox)findViewById(R.id.autoplay)).isChecked())
                .putExtra("smallPlayer", ((CheckBox)findViewById(R.id.smallPlayer)).isChecked())
                .putExtra("autoloop", ((CheckBox)findViewById(R.id.autoloop)).isChecked())
                .putExtra("noLogging", ((CheckBox)findViewById(R.id.noLogging)).isChecked())
                .putExtra("fullscreen", ((CheckBox)findViewById(R.id.fullscreen)).isChecked())
                .putExtra("fullscreenButton", ((CheckBox)findViewById(R.id.fullscreenButton)).isChecked())
                .putExtra("customIcons", ((CheckBox)findViewById(R.id.customIcons)).isChecked())
                .putExtra("controls", ((CheckBox)findViewById(R.id.controls)).isChecked())
                .putExtra("remote", ((CheckBox)findViewById(R.id.remote)).isChecked())
        );
    }

}