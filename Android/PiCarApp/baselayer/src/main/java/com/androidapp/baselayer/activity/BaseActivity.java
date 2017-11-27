package com.androidapp.baselayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.androidapp.baselayer.R;
import com.androidapp.baselayer.utils.Log4a;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by muzammilpeer on 01/11/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    String FRAGMENT_TAG = "FRAGMENT_TAG";

    Handler handler = new Handler();
    Runnable runnable;

    protected AtomicBoolean isFragmentLoaded = new AtomicBoolean(false);

    //mandatory layout for creating activity
    protected abstract int getLayoutId();

    public abstract int getFrameLayoutId();

    protected void initViews() {
    }

    protected void initObjects() {
    }

    protected void initListenerOrAdapter() {
    }

    protected void initNetworkCalls() {
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setupActivity();
    }


    //setup will be called by oncreateView
    protected void setupActivity() {
        try {
            // 1. inject view with butterknife or manually
            initViews();

            if (isFragmentLoaded.get() == false) {
                //2. Load object once
                initObjects();
                //3. Network calls once
                initNetworkCalls();
            }

            //4. rebind the views with listeners or adapter again for renewal created views.
            initListenerOrAdapter();

            //mark current fragment as loaded just recreate the views only.
            isFragmentLoaded.set(true);

        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /////////////////////////////////Common methods///////////////////////////////////

    public void replaceFragment(final Fragment frag, final int containerID) {
        runnable = new Runnable() {
            @Override
            public void run() {
                //Second fragment after 5 seconds appears
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(containerID, frag)
                        .addToBackStack(FRAGMENT_TAG)
                        .commit();
            }
        };
        handler.postDelayed(runnable, 100);
    }


    public void replaceFragmentWithiOSAnimation(final Fragment frag, final int containerID) {

        // Note that we pass in 4 animations here.  Please see the
        // documentation on this method as it is critical to the
        // understanding of this solution.
        runnable = new Runnable() {
            @Override
            public void run() {
                //Second fragment after 5 seconds appears
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(containerID, frag)
                        .addToBackStack(FRAGMENT_TAG)
                        .commit();
            }
        };
        handler.postDelayed(runnable, 100);
    }

    public void replaceFragmentWithFlipAnimation(final Fragment frag, final int containerID) {

        // Note that we pass in 4 animations here.  Please see the
        // documentation on this method as it is critical to the
        // understanding of this solution.
        runnable = new Runnable() {
            @Override
            public void run() {
                //Second fragment after 5 seconds appears
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.card_flip_right_in, R.anim.card_flip_right_out,
                                R.anim.card_flip_left_in, R.anim.card_flip_left_out)
                        .replace(containerID, frag)
                        .addToBackStack(FRAGMENT_TAG)
                        .commit();

            }
        };
        handler.postDelayed(runnable, 100);
    }

    public void replaceFragmentWithoutStack(final Fragment frag, final int containerID) {


        runnable = new Runnable() {
            @Override
            public void run() {
                //Second fragment after 5 seconds appears
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(containerID, frag)
                        .commit();
            }
        };
        handler.postDelayed(runnable, 100);
    }

    public void addFragment(Fragment frag, int containerID) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerID,
                        frag).commit();
    }

    public void addFragmentWithFlipAnimation(Fragment frag, int containerID) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.card_flip_right_in, R.anim.card_flip_right_out,
                        R.anim.card_flip_left_in, R.anim.card_flip_left_out)
                .add(containerID, frag)
                .commit();
    }


    public void popAllFragment() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void popFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
    }


    public Fragment getLastFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            return fm.getFragments().get(fm.getBackStackEntryCount() - 1);
        } else if (fm.getFragments().size() > 0) {
            return fm.getFragments().get(fm.getFragments().size() - 1);
        }
        return null;
    }

    public int getFragmentsCount() {
        FragmentManager fm = getSupportFragmentManager();
        int totalFragments = fm.getFragments().size() + fm.getBackStackEntryCount();

        return totalFragments;
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }


}
