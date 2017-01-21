package nz.co.delacour.exposurevidsta;

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
