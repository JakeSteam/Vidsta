package uk.co.jakelee.vidsta.listeners;

import uk.co.jakelee.vidsta.VidstaPlayer;

public interface VideoListeners {

    void OnVideoStarted(VidstaPlayer evp);

    void OnVideoPaused(VidstaPlayer evp);

    void OnVideoStopped(VidstaPlayer evp);

    void OnVideoFinished(VidstaPlayer evp);

    void OnVideoBuffering(VidstaPlayer evp, int buffPercent);

    void OnVideoError(Exception err);//Todo: Add Error listener, not urgent tho.

    void OnVideoRestart(VidstaPlayer VidstaPlayer);
}


