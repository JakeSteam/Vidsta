package nz.co.delacour.exposurevidsta;

/**
 * Created by Chris on 13-Sep-16.
 */

public interface VideoListeners {

    void OnVideoStarted(Vidsta evp);

    void OnVideoPaused(Vidsta evp);

    void OnVideoStopped(Vidsta evp);

    void OnVideoFinished(Vidsta evp);

    void OnVideoBuffering(Vidsta evp, int buffPercent);

    void OnVideoError(Exception err);//Todo: Add Error listener, not urgent tho.

    void OnVideoRestart(Vidsta player);
}


