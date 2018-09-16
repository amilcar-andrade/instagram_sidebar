package andrade.amilcar.instagramsidebar.ui;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.View;

public class RightySlidingPaneLayout extends SlidingPaneLayout {
    private final boolean isRtlDirection;

    public RightySlidingPaneLayout(Context context) {
        this(context, null);
    }

    public RightySlidingPaneLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightySlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // Ask the system if we are using a RTL
        isRtlDirection = getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        // If the user is using RTL then use the default direction (left), otherwise use RTL as the default
        setLayoutDirection(isRtlDirection ? View.LAYOUT_DIRECTION_LTR : View.LAYOUT_DIRECTION_RTL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final int childCount = getChildCount();
        if (childCount == 0) {
            // Nothing to do
            return;
        }

        for (int i = 0; i < childCount; i++) {
            // Apply the correct layout direction for the children
            getChildAt(i).setLayoutDirection(isRtlDirection ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
        }
    }
}