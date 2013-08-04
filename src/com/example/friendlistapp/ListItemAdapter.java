package com.example.friendlistapp;

import java.util.List;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.example.friendlistapp.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Custom adapter for ListView
 * 
 * @author Rascal
 */
public class ListItemAdapter extends ArrayAdapter<GraphUser> {

	private final Context context;
	private final List<GraphUser> users;

	public ListItemAdapter(Context context, List<GraphUser> users) {
		super(context, R.layout.listitem, users);
		this.context = context;
		this.users = users;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listitem, null);

			holder = new ViewHolder();
			holder.pictureView = (ProfilePictureView) convertView
					.findViewById(R.id.list_profile_pic);
			holder.pictureView.setCropped(true);
			holder.nameView = (TextView) convertView
					.findViewById(R.id.list_user_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.pictureView.setProfileId(users.get(position).getId());
		holder.nameView.setText(users.get(position).getName());

		return convertView;
	}

	private static class ViewHolder {
		public ProfilePictureView pictureView;
		public TextView nameView;
	}

}
