package andrade.amilcar.instagramsidebar.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

class GridMarginDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    GridMarginDecoration(final int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.top = space;
        outRect.right = space;
        outRect.bottom = space;
    }
}
