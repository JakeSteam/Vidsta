package uk.co.jakelee.vidstasample.utils;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


class ListenerHelper {
    static void createListenerLog(LinearLayout messagesContainer, String text) {
        String currentDateTime = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(new Date());
        TextView textView = new TextView(messagesContainer.getContext());
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(14);
        textView.setText(currentDateTime + ": " + text);

        messagesContainer.addView(textView);
        final ScrollView scrollView = (ScrollView)messagesContainer.getParent();
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }


}
