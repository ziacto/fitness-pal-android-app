package com.fitpal.android.common;

import android.support.v4.app.Fragment;
import android.view.View;


public abstract class BaseFragment extends Fragment{

	public abstract void addClicked();
	protected abstract void initializeUI(View view);
	
}
