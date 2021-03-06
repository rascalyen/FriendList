package com.example.friendlistapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;


/**
 * Authenticated fragment
 * 
 * @author Rascal
 * 
 */
public class SelectionFragment extends Fragment {

	private ListView listView;
	private List<GraphUser> friends;
	private static final int REAUTH_ACTIVITY_CODE = 100;

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
		
		view.findViewById(R.id.selection_text);
		listView = (ListView) view.findViewById(R.id.list);
		
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			makeMyFriendsRequest(session);
		}
		
		return view;
	}
	

	/**
	 * make request for friend list of authenticated user
	 * 
	 * @param session
	 */
	private void makeMyFriendsRequest(final Session session) {
		Request request = Request.newMyFriendsRequest(session,
				new Request.GraphUserListCallback() {

					@Override
					public void onCompleted(List<GraphUser> users,
							Response response) {
						if (session == Session.getActiveSession()) {
							if (users.size() != 0) {
								sortFriends(users);
								friends = new ArrayList<GraphUser>();
								friends.addAll(users);
								listView.setAdapter(new ListItemAdapter(getActivity(), friends));
							}
						}
						if (response.getError() != null) {
							Log.e("error!", response.getError()
									.getErrorMessage());
						}
					}
				});
		request.executeAsync();
	}
	
	
	/**
	 * Sort a list of GraphUser objects alphabetically
	 * 
	 * @param friends
	 */
	private void sortFriends(List<GraphUser> friends) {
		Collections.sort(friends, new Comparator<GraphUser>() {
			@Override
			public int compare(GraphUser f1, GraphUser f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});
	}

	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
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
