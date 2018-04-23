/*
 *  Cupcake Player
 * 
 *  Copyright 2018 Rachid Boudjelida <rachidboudjelida@gmail.com>
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
package com.lordroid.cupcake.bt;

import java.util.PrimitiveIterator;

import bt.torrent.PieceStatistics;
import bt.torrent.selector.BaseStreamSelector;

/**
 * @author HumanShield85
 * 
 */
public class MyPeiceSelector extends BaseStreamSelector {
	static int x = 0;
	/**
	 * @since 1.1
	 */
	public static MyPeiceSelector sequential() {
		return new MyPeiceSelector();
	}

	public static int setI(int value) {

		x = value - 1;

		return x;
	}

	PieceStatistics pieceStatistics;

	@Override
	protected PrimitiveIterator.OfInt createIterator(
			PieceStatistics pieceStatisticsargs) {
		pieceStatistics = pieceStatisticsargs;
		return new PrimitiveIterator.OfInt() {

			int i = x;

			@Override
			public boolean hasNext() {
				while (i < pieceStatistics.getPiecesTotal()
						&& pieceStatistics.getCount(i) == 0) {
					i++;
				}
				return i < pieceStatistics.getPiecesTotal();
			}

			@Override
			public int nextInt() {
				return i++;
			}
		};
	}
}