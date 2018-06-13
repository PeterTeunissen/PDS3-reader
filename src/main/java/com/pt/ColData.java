package com.pt;

/**
 * Class to hold the name and value of one data point from the .DAT file. This
 * class contains a colName member which will be set to the correct index if the
 * entry came from an n-dimensional array.
 * 
 * E.g. in a ACCUMULATION array of 2x4, one entry's colName could be set to
 * ACCUMULATION[1][3]
 * 
 * @author PT
 *
 */
public class ColData {

	public String colName;
	public String colValue;

	public String toString() {
		return colName + "=" + colValue + ",";
	}
}
