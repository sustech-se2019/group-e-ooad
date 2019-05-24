package com.google.codelabs.mdc.java.threeNews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.codelabs.mdc.java.threeNews.R;

import java.util.List;

/**
 * Main view and activity
 * @author Yu QIU
 * @author Yinqi Chen
 * @author Sen Peng
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements NavigationHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new NewsGridFragment())
                    .commit();
        }
    }

    /**
     * Navigate to the given fragment.
     *
     * @param fragment Fragment to navigate to.
     * @param addToBackstack Whether or not the current fragment should be added to the backstack.
     */
    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack, boolean isReplace) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if(isReplace){

            fragmentTransaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
                                R.animator.fragment_slide_left_exit,
                                R.animator.fragment_slide_right_enter,
                                R.animator.fragment_slide_right_exit)
                        .replace(R.id.container, fragment);
        }else {
            fragmentTransaction.setCustomAnimations(R.animator.h_fragment_enter,
                    R.animator.h_fragment_exit,
                    R.animator.h_fragment_pop_enter,
                    R.animator.h_fragment_pop_exit);

            // 1.hide all fragments
            List<Fragment> childFragments = mFragmentManager.getFragments();
            for (Fragment childFragment : childFragments) {
                fragmentTransaction.hide(childFragment);
            }

            // 2.Add or display fragment
            if (!childFragments.contains(fragment)) {
                fragmentTransaction.add(R.id.container, fragment);
            } else {
                fragmentTransaction.show(fragment);
            }
        }
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

    }
}
