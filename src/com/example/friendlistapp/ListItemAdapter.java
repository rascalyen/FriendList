package com.example.friendlistapp;

import java.util.List;
import com.facebook.model.GraphUser;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Custom adapter for ListView
 * 
 * @author Rascal
 */
public class ListItemAdapter extends ArrayAdapter<GraphUser> {
	
	private final Context context;
	private final List<GraphUser> users;
    private String initHeader;

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
			initRowView(convertView, holder);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		setRowView(position, holder);
		return convertView;
	}
	
	
	private void initRowView(View convertView, ViewHolder holder) {
		holder.pictureView = (ImageView) convertView
				.findViewById(R.id.list_profile_pic);
		holder.nameView = (TextView) convertView
				.findViewById(R.id.list_user_name);
		holder.headerView = (TextView) convertView
				.findViewById(R.id.list_header);	
	}	
	
	
	private void setRowView(int position, ViewHolder holder) {
		holder.nameView.setText(users.get(position).getName());
		
		String firstLetter = users.get(position).getName().substring(0, 1);
		if (position == 0)
			initHeader = "";
		if (!initHeader.equals(firstLetter)) {
			initHeader = firstLetter;
			holder.headerView.setText(initHeader);
			holder.headerView.setVisibility(View.VISIBLE);
		} else {
			holder.headerView.setVisibility(View.GONE);
		}
		
		String url = "http://graph.facebook.com/" + users.get(position).getId() + "/picture?type=large";
		Picasso.with(context).load(url).resize(60, 60).centerCrop().into(holder.pictureView);
	}
	
	
	private static class ViewHolder {
		public ImageView pictureView;
		public TextView  nameView;
		public TextView  headerView;
	}

}
