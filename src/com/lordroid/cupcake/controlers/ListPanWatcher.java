package com.lordroid.cupcake.controlers;

import com.lordroid.cupcake.ui.MovieItem;
import com.lordroid.cupcake.yify.YifyMovie;

public interface ListPanWatcher {
	public void ItemSelected(MovieItem movieItem);

	public void ListActionPerformed(YifyMovie movie, int action);
}
