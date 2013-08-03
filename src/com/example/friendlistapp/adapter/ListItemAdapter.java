package com.example.friendlistapp.adapter;

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
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.listitem, parent, false);
	    
	    ProfilePictureView pictureView = (ProfilePictureView) rowView.findViewById(R.id.list_profile_pic);
	    pictureView.setProfileId(users.get(position).getId());
	    
	    TextView nameView = (TextView) rowView.findViewById(R.id.list_user_name);
	    nameView.setText(users.get(position).getName());

	    return rowView;
	  }

}
