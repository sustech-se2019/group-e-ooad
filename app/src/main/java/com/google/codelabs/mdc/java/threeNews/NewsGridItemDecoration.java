package com.google.codelabs.mdc.java.threeNews;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Custom item decoration for a vertical {@link NewsGridFragment} {@link RecyclerView}. Adds a
 * small amount of padding to the left of grid items, and a large amount of padding to the right.
 */
public class NewsGridItemDecoration extends RecyclerView.ItemDecoration {
    final private int largePadding;
    final private int smallPadding;

    /**
     * News grid item's Decoration
     *
     * @param largePadding Large padding
     * @param smallPadding Small padding
     */
    public NewsGridItemDecoration(int largePadding, int smallPadding) {
        this.largePadding = largePadding;
        this.smallPadding = smallPadding;
    }

    /**
     *
     * Get Item's offsets
     *
     * @param outRect The out react of item
     * @param view The view
     * @param parent The parent of view
     * @param state The state of view
     */
    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = smallPadding;
        outRect.right = smallPadding;
        outRect.top = largePadding;
        outRect.bottom = largePadding;
    }
}
