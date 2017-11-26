package com.androidapp.baselayer.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.androidapp.baselayer.activity.BaseActivity;
import com.androidapp.baselayer.utils.Log4a;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by muzammilpeer on 01/11/2017.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    protected View fragmentBaseView; //main layout inflated by fragment
    protected Context mContext;
    protected AtomicBoolean isFragmentLoaded = new AtomicBoolean(false);
    protected List localDataSource = new ArrayList();


    public abstract int getLayoutId();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mContext == null)
            mContext = getBaseActivity();
        if (fragmentBaseView == null)
            fragmentBaseView = inflater.inflate(getLayoutId(), container, false);
        //Injection Views
        ButterKnife.bind(this, fragmentBaseView);
        unbinder = ButterKnife.bind(this, fragmentBaseView);
        return fragmentBaseView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFragment(view, savedInstanceState);
    }


    public void initViews(View view, Bundle savedInstanceState) {
        getBaseActivity().showToolBar();
        getBaseActivity().hideRightToolbarItem();
        if (getBaseActivity().getFragmentsCount() == 1) {
            getBaseActivity().hideLeftToolbarItem();
        } else {
            getBaseActivity().showLeftToolbarItem();
        }
    }

    public abstract void initObjects();

    public abstract void initListenerOrAdapter();

    public abstract void initNetworkCalls();


    //setup will be called by oncreateView
    private void setupFragment(View view, Bundle savedInstanceState) {
        try {
            if (isFragmentLoaded.get() == false) {
                //1. Load object once
                initObjects();
            }

            // 2. inject view with butterknife or manually
            initViews(view, savedInstanceState);
            //3. rebind the views with listeners or adapter again for renewal created views.
            initListenerOrAdapter();

            if (isFragmentLoaded.get() == false) {
                //4. Network calls once
                initNetworkCalls();
            }

            //mark current fragment as loaded just recreate the views only.
            isFragmentLoaded.set(true);

        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) this.getActivity();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        fragmentBaseView = null;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
