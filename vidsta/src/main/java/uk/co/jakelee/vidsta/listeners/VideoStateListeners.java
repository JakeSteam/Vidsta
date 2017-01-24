package uk.co.jakelee.vidsta.listeners;

import uk.co.jakelee.vidsta.VidstaPlayer;

public class VideoStateListeners {

    public interface OnVideoStartedListener {
        void OnVideoStarted(VidstaPlayer evp);
    }

    public interface OnVideoPausedListener {
        void OnVideoPaused(VidstaPlayer evp);
    }

    public interface OnVideoStoppedListener {
        void OnVideoStopped(VidstaPlayer evp);
    }

    public interface OnVideoFinishedListener {
        void OnVideoFinished(VidstaPlayer evp);
    }

    public interface OnVideoBufferingListener {
        void OnVideoBuffering(VidstaPlayer evp, int buffPercent);
    }

    public interface OnVideoErrorListener {
        void OnVideoError(int what, int extra);
    }

    public interface OnVideoRestartListener {
        void OnVideoRestart(VidstaPlayer evp);
    }
}