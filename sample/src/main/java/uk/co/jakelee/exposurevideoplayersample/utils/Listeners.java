package uk.co.jakelee.exposurevideoplayersample.utils;

import android.widget.LinearLayout;

import uk.co.jakelee.vidsta.listeners.FullScreenClickListener;
import uk.co.jakelee.vidsta.listeners.VideoStateListeners;
import uk.co.jakelee.vidsta.Vidsta;

public class Listeners {
    private LinearLayout messagesContainer;

    public Listeners(LinearLayout container) {
        this.messagesContainer = container;
    }

    public VideoStateListeners.OnVideoBufferingListener bufferingListener = new VideoStateListeners.OnVideoBufferingListener() {
        @Override
        public void OnVideoBuffering(Vidsta evp, int buffPercent) {
            ListenerHelper.createListenerLog(messagesContainer, "Video buffering " + buffPercent + "%!");
        }
    };

    public VideoStateListeners.OnVideoErrorListener errorListener = new VideoStateListeners.OnVideoErrorListener() {
        @Override
        public void OnVideoError(int what, int extra) {
            ListenerHelper.createListenerLog(messagesContainer, "Video errored!");
        }
    };

    public VideoStateListeners.OnVideoFinishedListener finishedListener = new VideoStateListeners.OnVideoFinishedListener() {
        @Override
        public void OnVideoFinished(Vidsta evp) {
            ListenerHelper.createListenerLog(messagesContainer, "Video finished!");
        }
    };

    public VideoStateListeners.OnVideoPausedListener pausedListener = new VideoStateListeners.OnVideoPausedListener() {
        @Override
        public void OnVideoPaused(Vidsta evp) {
            ListenerHelper.createListenerLog(messagesContainer, "Video paused!");
        }
    };

    public VideoStateListeners.OnVideoRestartListener restartListener = new VideoStateListeners.OnVideoRestartListener() {
        @Override
        public void OnVideoRestart(Vidsta evp) {
            ListenerHelper.createListenerLog(messagesContainer, "Video restarted!");
        }
    };

    public VideoStateListeners.OnVideoStartedListener startedListener = new VideoStateListeners.OnVideoStartedListener() {
        @Override
        public void OnVideoStarted(Vidsta evp) {
            ListenerHelper.createListenerLog(messagesContainer, "Video started!");
        }
    };

    public VideoStateListeners.OnVideoStoppedListener stoppedListener = new VideoStateListeners.OnVideoStoppedListener() {
        @Override
        public void OnVideoStopped(Vidsta evp) {
            ListenerHelper.createListenerLog(messagesContainer, "Video stopped!");
        }
    };

    public FullScreenClickListener fullScreenListener = new FullScreenClickListener() {
        @Override
        public void onToggleClick(boolean isFullscreen) {
            ListenerHelper.createListenerLog(messagesContainer, "Video set to " + (isFullscreen ? "not " : "") + " fullscreen!");
        }
    };
}
