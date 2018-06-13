package com.pt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ColDefReader {

	public FileDescriptor readDefinition(String defFile) {

		if (!defFile.endsWith(".LBL")) {
			System.out.println("You need to pass the .LBL filename!");
			return null;
		}

		String fmtFile = "";
		String datFile = "";
		String dirName = "";

		FileDescriptor fd = new FileDescriptor();

		FileReader fileReader = null;
		try {
			File file = new File(defFile);

			dirName = file.getAbsolutePath();
			dirName = file.getCanonicalPath();
			dirName = file.getParent();

			fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				if (line.trim().startsWith("^STRUCTURE")) {
					String[] pieces = line.trim().split("=");
					fmtFile = pieces[1].trim().replaceAll("\"", "");
				}
				if (line.trim().startsWith("^TABLE")) {
					String[] pieces = line.trim().split("=");
					datFile = pieces[1].trim().replaceAll("\"", "");
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (Exception e) {
				}
			}
		}

		fd.fmtFile = dirName + File.separator + fmtFile;
		fd.datFile = dirName + File.separator + datFile;
		fd.csvFile = dirName + File.separator + datFile.substring(0, datFile.length() - 4) + ".csv";

		try {
			fileReader = null;
			File file = new File(fd.fmtFile);
			fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {

				if (line.startsWith("/* RJW,")) {
					line = line.replaceAll("/[*]", "").replaceAll("[*]/", "").trim();
					String[] pieces = line.trim().split(",");
					// RJW, ACCUMULATION_TIME, H, 1, 1 */
					if (pieces[1].trim().equals("BYTES_PER_RECORD")) {
						continue;
					}
					if (pieces[1].trim().equals("OBJECTS_PER_RECORD")) {
						continue;
					}
					ColDef cd = new ColDef();
					cd.colName = pieces[1].trim();
					cd.dataType = pieces[2].trim();
					cd.dataSize = getSize(pieces[2].trim());
					cd.dimensions = Integer.valueOf(pieces[3].trim());
					cd.dimensionSize = new int[cd.dimensions];
					for (int i = 1; i <= cd.dimensions; i++) {
						cd.dimensionSize[i - 1] = Integer.valueOf(pieces[4 + i - 1].trim());
					}

					// Fake a sequence of characters to a single string
					if (cd.dataType.equals("c")) {
						cd.dataSize = cd.dimensionSize[0];
						cd.dimensionSize[0] = 1;
					}
					fd.columns.add(cd);
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (Exception e) {
				}
			}
		}
		return fd;
	}

	private int getSize(String dt) {
		if (dt.equals("H")) {
			return 2;
		}
		if (dt.equals("I")) {
			return 4;
		}
		if (dt.equals("B")) {
			return 1;
		}
		if (dt.equals("f")) {
			return 4;
		}
		if (dt.equals("h")) {
			return 2;
		}
		if (dt.equals("c")) {
			return 1;
		}
		return 1;
	}
}
