package com.pt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Main entry program. Takes the command line arguments, calls the ColDefReader
 * to get the layout of the .DAT file (by reading the .LBL and .FMT files). Then
 * reads the actual .DAT file with that definition and writes it out to a .csv
 * file.
 * 
 * @author PT
 *
 */
public class DatReader {

	public static void main(String[] args) {

		try {

			if (args.length < 2) {
				System.out.println("Use two parameters: <filename> <showheadersflag>");
				System.out.println("E.g. JAD_HSK_ELC_ALL_2014020_V02.LBL true");
				System.out.println("E.g. JAD_HSK_ELC_ALL_2014020_V02.LBL false");
				System.exit(0);
			}

			ColDefReader rdr = new ColDefReader();
			FileDescriptor fd = rdr.readDefinition(args[0]);

			System.out.println(fd);

			FileReader frdr = new FileReader();
			List<RowData> filedata = frdr.readFile(fd.datFile, fd);

			boolean hdr = Boolean.parseBoolean(args[1]);

			File outF = new File(fd.csvFile);
			if (outF.exists()) {
				boolean d = outF.delete();
				if (d) {
					System.out.println("Existing .csv file deleted...");
				}
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(outF));
			for (RowData rd : filedata) {

				StringBuilder bf = new StringBuilder();

				boolean first = true;
				if (hdr) {
					hdr = false;
					for (ColData cd : rd.rowData) {
						if (!first) {
							bf.append(",");
						}
						first = false;
						bf.append(cd.colName);
					}
					System.out.println(bf.toString());
					bf.append("\n");
					writer.write(bf.toString());
				}
				bf = new StringBuilder();
				first = true;
				for (ColData cd : rd.rowData) {
					if (!first) {
						bf.append(",");
					}
					first = false;
					bf.append(cd.colValue);
				}
				System.out.println(bf.toString());
				bf.append("\n");
				writer.write(bf.toString());
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
