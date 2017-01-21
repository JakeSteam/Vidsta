package uk.co.jakelee.vidstasample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.OnColorSelectedListener;
import com.pavelsikun.vintagechroma.colormode.ColorMode;


public class MainActivity extends AppCompatActivity {
    private int tintColour = Color.WHITE;
    private int textColour = Color.WHITE;

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
                .putExtra("textColourEnabled", ((CheckBox)findViewById(R.id.textColour)).isChecked())
                .putExtra("textColour", textColour)
                .putExtra("iconTintEnabled", ((CheckBox)findViewById(R.id.iconTint)).isChecked())
                .putExtra("iconTint", tintColour)
                .putExtra("remote", ((CheckBox)findViewById(R.id.remote)).isChecked())
        );
    }

    public void launchTintColourPicker(View v) {
        if (((CheckBox)v).isChecked()) {
            new ChromaDialog.Builder()
                    .initialColor(Color.GREEN)
                    .colorMode(ColorMode.ARGB) // RGB, ARGB, HVS, CMYK, CMYK255, HSL
                    .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL; Note that (HSV || HSL || CMYK) && IndicatorMode.HEX is a bad idea
                    .onColorSelected(new OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(@ColorInt int color) {
                            tintColour = color;
                            findViewById(R.id.iconTint).setBackgroundColor(color);
                        }
                    })
                    .create()
                    .show(getSupportFragmentManager(), "ChromaDialog");
        } else {
            findViewById(R.id.iconTint).setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
    }

    public void launchTextColourPicker(View v) {
        if (((CheckBox)v).isChecked()) {
            new ChromaDialog.Builder()
                    .initialColor(Color.GREEN)
                    .colorMode(ColorMode.ARGB) // RGB, ARGB, HVS, CMYK, CMYK255, HSL
                    .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL; Note that (HSV || HSL || CMYK) && IndicatorMode.HEX is a bad idea
                    .onColorSelected(new OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(@ColorInt int color) {
                            textColour = color;
                            findViewById(R.id.textColour).setBackgroundColor(color);
                        }
                    })
                    .create()
                    .show(getSupportFragmentManager(), "ChromaDialog");
        } else {
            findViewById(R.id.textColour).setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
    }

}