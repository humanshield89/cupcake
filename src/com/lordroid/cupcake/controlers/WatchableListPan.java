package com.lordroid.cupcake.controlers;

import com.lordroid.cupcake.ui.MovieItem;


public interface WatchableListPan {
	
	public MovieItem addListPanWatcher(ListPanWatcher lw);
	
	public void removeListPanWatcher(ListPanWatcher lw) ;
	
	public void updateListWatchers(int action) ;
}
