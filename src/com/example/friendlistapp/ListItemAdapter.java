package com.example.friendlistapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import com.facebook.model.GraphUser;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * Custom adapter for ListView
 * 
 * @author Rascal
 */
public class ListItemAdapter extends ArrayAdapter<GraphUser> implements SectionIndexer {
	
	private final Context context;
	private final List<GraphUser> users;
	private HashMap<String, Integer> alphas;
    private String[] sections;

	public ListItemAdapter(Context context, List<GraphUser> users) {
		super(context, R.layout.listitem, users);
		this.context = context;
		this.users = users;
		getMySections(users);
	}

	
	private void getMySections(List<GraphUser> users) {
		alphas = new HashMap<String, Integer>();
        for (int i = users.size()-1 ; i >= 0; i--) 
        		alphas.put(users.get(i).getName().substring(0, 1), i); 
        
        ArrayList<String> letters = new ArrayList<String>(alphas.keySet());
        Collections.sort(letters);
        sections = new String[letters.size()];
        letters.toArray(sections);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listitem, null);

			holder = new ViewHolder();
			holder.pictureView = (ImageView) convertView
					.findViewById(R.id.list_profile_pic);
			holder.nameView = (TextView) convertView
					.findViewById(R.id.list_user_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		String url = "http://graph.facebook.com/" + users.get(position).getId() + "/picture?type=large";
		Picasso.with(context).load(url).resize(60, 60).centerCrop().into(holder.pictureView);
		holder.nameView.setText(users.get(position).getName());

		return convertView;
	}
	
	@Override
	public int getPositionForSection(int section) {
		return alphas.get(sections[section]);
	}

	@Override
	public int getSectionForPosition(int position) {
		return 1;
	}

	@Override
	public Object[] getSections() {
		return sections;
	}	
	

	private static class ViewHolder {
		public ImageView pictureView;
		public TextView  nameView;
	}

}
