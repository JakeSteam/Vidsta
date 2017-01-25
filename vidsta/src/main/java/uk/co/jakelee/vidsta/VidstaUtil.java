package uk.co.jakelee.vidsta;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VidstaUtil {
    public static String getTimeString(long durationMs, boolean setValueNegative) {
        return String.format(Locale.getDefault(), "%s%02d:%02d",
                setValueNegative ? "-" : "", TimeUnit.MILLISECONDS.toMinutes(durationMs), TimeUnit.MILLISECONDS.toSeconds(durationMs) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durationMs))
        );
    }
}
