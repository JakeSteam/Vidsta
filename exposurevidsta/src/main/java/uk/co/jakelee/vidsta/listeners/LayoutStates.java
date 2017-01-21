package uk.co.jakelee.vidsta.listeners;

/**
 * Created by Chris on 14-Sep-16.
 */

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
