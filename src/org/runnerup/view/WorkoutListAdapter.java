/*
 * Copyright (C) 2013 jonas.oreland@gmail.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.runnerup.view;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import org.runnerup.util.Constants.DB;
import org.runnerup.workout.WorkoutSerializer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class WorkoutListAdapter extends BaseAdapter {

	/**
	 * 
	 */
	LayoutInflater inflater = null;
	String[] workoutList = new String[0];

	public WorkoutListAdapter(LayoutInflater inflater) {
		super();
		this.inflater = inflater;
	}
	
	@Override
	public int getCount() {
		return workoutList.length;
	}

	@Override
	public Object getItem(int position) {
		return workoutList[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
	        convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
		}

		TextView ret = (TextView) convertView.findViewById(android.R.id.text1);
		ret.setText(getItem(position).toString());
		return ret;
	}

	public int find(String name) {
		for (int i = 0; i < getCount(); i++) {
			if (name.contentEquals(getItem(i).toString()))
				return i;
		}
		return 0;
	}

	public void reload() {
		File f = inflater.getContext().getDir(WorkoutSerializer.WORKOUTS_DIR, 0);
		String[] list = f.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".json");
			}});
		if (list == null) {
			if (workoutList != null && workoutList.length == 0) {
				// return;
			} else {
				workoutList = new String[0];
			}
		} else {
			workoutList = list;
		}
		this.notifyDataSetChanged();
	}
}