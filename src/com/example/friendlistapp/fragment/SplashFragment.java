package com.example.friendlistapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.friendlistapp.R;


/**
 * Login fragment
 * 
 * @author Rascal
 *
 */
public class SplashFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.splash, container, false);
		return view;
	}

}
