package com.pt;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to loop through the .DAT file and read it according to the columns
 * defined in the FileDescriptor object.
 * 
 * For each row of data in the .DAT file, it adds an entry to the fileData
 * array. Each entry in the fileData array, contains an array of ColData
 * objects.
 * 
 * @author PT
 *
 */
public class FileReader {

	public List<RowData> readFile(String fin, FileDescriptor fd) {

		FileInputStream in = null;

		List<RowData> fileData = new ArrayList<RowData>();

		try {
			in = new FileInputStream(fin);

			int l = 0;
			boolean more = true;
			while (more) {
				RowData rowdata = new RowData();
				for (ColDef cdef : fd.columns) {
					more = cdef.getData(rowdata, in);
					if (!more) {
						break;
					}
				}
				fileData.add(rowdata);

				if (l % 100 == 0) {
					System.out.println(l);
				} else {
					System.out.print(".");
				}
				++l;
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}

		return fileData;
	}
}
