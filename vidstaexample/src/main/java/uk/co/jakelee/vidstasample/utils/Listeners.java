package uk.co.jakelee.vidstasample.utils;

import android.widget.LinearLayout;

import uk.co.jakelee.vidsta.listeners.FullScreenClickListener;
import uk.co.jakelee.vidsta.listeners.VideoStateListeners;
import uk.co.jakelee.vidsta.VidstaPlayer;

public class Listeners {
    final private LinearLayout messagesContainer;

    public Listeners(LinearLayout container) {
        this.messagesContainer = container;
    }

    final public VideoStateListeners.OnVideoBufferingListener bufferingListener = new VideoStateListeners.OnVideoBufferingListener() {
        @Override
        public void OnVideoBuffering(VidstaPlayer evp, int buffPercent) {
            ListenerHelper.createListenerLog(messagesContainer, "Video buffering " + buffPercent + "%!");
        }
    };

    final public VideoStateListeners.OnVideoErrorListener errorListener = new VideoStateListeners.OnVideoErrorListener() {
        @Override
        public void OnVideoError(int what, int extra) {
            ListenerHelper.createListenerLog(messagesContainer, "Video errored!");
        }
    };

    final public VideoStateListeners.OnVideoFinishedListener finishedListener = new VideoStateListeners.OnVideoFinishedListener() {
        @Override
        public void OnVideoFinished(VidstaPlayer evp) {
            ListenerHelper.createListenerLog(messagesContainer, "Video finished!");
        }
    };

    final public VideoStateListeners.OnVideoPausedListener pausedListener = new VideoStateListeners.OnVideoPausedListener() {
        @Override
        public void OnVideoPaused(VidstaPlayer evp) {
            ListenerHelper.createListenerLog(messagesContainer, "Video paused!");
        }
    };

    final public VideoStateListeners.OnVideoRestartListener restartListener = new VideoStateListeners.OnVideoRestartListener() {
        @Override
        public void OnVideoRestart(VidstaPlayer evp) {
            ListenerHelper.createListenerLog(messagesContainer, "Video restarted!");
        }
    };

    final public VideoStateListeners.OnVideoStartedListener startedListener = new VideoStateListeners.OnVideoStartedListener() {
        @Override
        public void OnVideoStarted(VidstaPlayer evp) {
            ListenerHelper.createListenerLog(messagesContainer, "Video started!");
        }
    };

    final public VideoStateListeners.OnVideoStoppedListener stoppedListener = new VideoStateListeners.OnVideoStoppedListener() {
        @Override
        public void OnVideoStopped(VidstaPlayer evp) {
            ListenerHelper.createListenerLog(messagesContainer, "Video stopped!");
        }
    };

    final public FullScreenClickListener fullScreenListener = new FullScreenClickListener() {
        @Override
        public void onToggleClick(boolean isFullscreen) {
            ListenerHelper.createListenerLog(messagesContainer, "Video set to " + (isFullscreen ? "not " : "") + " fullscreen!");
        }
    };
}
