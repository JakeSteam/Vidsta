package nz.co.delacour.exposurevidsta;

/**
 * Created by Chris on 13-Sep-16.
 */

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
        void OnVideoError(int i, int ii);
    }

    public interface OnVideoRestartListener {
        void OnVideoRestart(Vidsta player);
    }
}