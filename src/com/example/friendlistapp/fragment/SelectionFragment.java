package com.example.friendlistapp.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendlistapp.R;
import com.example.friendlistapp.adapter.ListItemAdapter;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class SelectionFragment extends Fragment {

	private ProfilePictureView profilePictureView;
	private TextView userNameView;
	private ListView listView;
	private static final int REAUTH_ACTIVITY_CODE = 100;
	private List<GraphUser> myusers;

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.selection, container, false);

		profilePictureView = (ProfilePictureView) view
				.findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(true);

		userNameView = (TextView) view.findViewById(R.id.selection_user_name);
		listView = (ListView) view.findViewById(R.id.list);

		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			makeMeRequest(session);
			makeMyFriendsRequest(session);
		}

		return view;
	}

	private void makeMyFriendsRequest(final Session session) {
		Request request = Request.newMyFriendsRequest(session,
				new Request.GraphUserListCallback() {

					@Override
					public void onCompleted(List<GraphUser> users,
							Response response) {
						if (session == Session.getActiveSession()) {
							if (users.size() != 0 ) {
								myusers = new ArrayList<GraphUser>(); 
								myusers.addAll(users);
								listView.setAdapter(new ListItemAdapter(getActivity(), myusers));
							}
						}
						if (response.getError() != null) {
							Log.d("error!", response.getError().getErrorMessage());
						}
					}
				});
		request.executeAsync();
	}

	private void makeMeRequest(final Session session) {
		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (session == Session.getActiveSession()) {
							if (user != null) {
								profilePictureView.setProfileId(user.getId());
								userNameView.setText(user.getName() + "'s friends");
							}
						}
						if (response.getError() != null) {
							Log.d("error!", response.getError().getErrorMessage());
						}
					}

				});
		request.executeAsync();
	}

	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			makeMeRequest(session);
			makeMyFriendsRequest(session);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REAUTH_ACTIVITY_CODE) {
			uiHelper.onActivityResult(requestCode, resultCode, data);
		}
	}
	

}
