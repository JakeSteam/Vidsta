package uk.co.jakelee.vidsta.listeners;

public class LayoutStates {

    public interface OnLayoutCreated {
        void onCreated();
    }

    public interface OnLayoutPaused {
        void onPaused();
    }

    public interface OnLayoutDestroyed {
        void onDestroy();
    }

    public interface OnLayoutResumed {
        void onResume();
    }
}
