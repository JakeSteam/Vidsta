package uk.co.jakelee.vidsta.listeners;

import uk.co.jakelee.vidsta.Vidsta;

public class VideoStateListeners {

    public interface OnVideoStartedListener {
        void OnVideoStarted(Vidsta evp);
    }

    public interface OnVideoPausedListener {
        void OnVideoPaused(Vidsta evp);
    }

    public interface OnVideoStoppedListener {
        void OnVideoStopped(Vidsta evp);
    }

    public interface OnVideoFinishedListener {
        void OnVideoFinished(Vidsta evp);
    }

    public interface OnVideoBufferingListener {
        void OnVideoBuffering(Vidsta evp, int buffPercent);
    }

    public interface OnVideoErrorListener {
        void OnVideoError(int what, int extra);
    }

    public interface OnVideoRestartListener {
        void OnVideoRestart(Vidsta evp);
    }
}