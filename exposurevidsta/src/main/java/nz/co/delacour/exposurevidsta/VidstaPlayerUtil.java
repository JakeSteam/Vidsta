package nz.co.delacour.exposurevidsta;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Chris on 11-Sep-16.
 */
public class VidstaPlayerUtil {
    public static String getTimeString(long durationMs, boolean setValueNegative) {
        return String.format(Locale.getDefault(), "%s%02d:%02d",
                setValueNegative ? "-" : "", TimeUnit.MILLISECONDS.toMinutes(durationMs), TimeUnit.MILLISECONDS.toSeconds(durationMs) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durationMs))
        );
    }
}
