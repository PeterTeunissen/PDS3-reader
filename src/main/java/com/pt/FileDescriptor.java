package com.pt;

import java.util.ArrayList;
import java.util.List;

public class FileDescriptor {

	public String fmtFile = "";
	public String datFile = "";
	public String csvFile = "";
	public List<ColDef> columns = new ArrayList<ColDef>();

	public String toString() {
		String r = ".FMT file=" + fmtFile + "\n" + ".DAT file=" + datFile + "\n" + ".csv file=" + csvFile + "\n";
		for (ColDef cd : columns) {
			r = r + cd.toString() + "\n";
		}
		return r;
	}
}
