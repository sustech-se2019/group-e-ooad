package com.google.codelabs.mdc.java.threeNews;

import android.support.v4.app.Fragment;

/**
 * A host (typically an {@code Activity}} that can display fragments and knows how to respond to
 * navigation events.
 * @author Yu Qiu
 * @author Yinqi Chen
 */
public interface NavigationHost {
    /**
     * Trigger a navigation to the specified fragment, optionally adding a transaction to the back
     * stack to make this navigation reversible.
     */
    void navigateTo(Fragment fragment, boolean addToBackstack, boolean isReplace);
}
