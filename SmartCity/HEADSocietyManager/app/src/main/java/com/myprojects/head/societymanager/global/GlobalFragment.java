package com.myprojects.head.societymanager.global;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myprojects.head.societymanager.Dependencies;
import com.myprojects.head.societymanager.R;
import com.myprojects.head.societymanager.global.presenter.GlobalPresenter;
import com.myprojects.head.societymanager.global.view.GlobalDisplayer;
import com.myprojects.head.societymanager.navigation.AndroidNavigator;

/**
 * Created by marco on 08/08/16.
 */

public class GlobalFragment extends Fragment {

    private GlobalPresenter presenter;
    private AndroidNavigator navigator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_global, container, false);
        GlobalDisplayer globalDisplayer = (GlobalDisplayer) rootView.findViewById(R.id.globalView);
        getActivity().setTitle(R.string.global_toolbar_title);

        navigator = new AndroidNavigator(getActivity());
        presenter = new GlobalPresenter(
                Dependencies.INSTANCE.getLoginService(),
                Dependencies.INSTANCE.getGlobalService(),
                globalDisplayer,
                Dependencies.INSTANCE.getUserService(),
                navigator
        );

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.startPresenting();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stopPresenting();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
