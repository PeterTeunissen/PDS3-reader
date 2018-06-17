package com.pt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
				System.out.println("Use two or three parameters: <filename> <showHeadersFlag> <showFileOnScreen>");
				System.out.println("The showHeadersFlag parameter should be true or false");
				System.out.println("The showFileOnScreen parameter is optional, can be true or false");
				System.out.println("E.g. JAD_HSK_ELC_ALL_2014020_V02.LBL true true");
				System.out.println("E.g. JAD_HSK_ELC_ALL_2014020_V02.LBL false");
				System.exit(0);
			}

			boolean showHeaders = Boolean.valueOf(args[1]);

			boolean showFileOnScreen = false;
			if (args.length > 1) {
				showFileOnScreen = Boolean.valueOf(args[2]);
			}

			boolean onScreen = showFileOnScreen;

			File f = new File(args[0]);
			String parent = f.getParent();
			if (parent == null) {
				parent = ".";
			}

			DatReader reader = new DatReader();
			try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(parent), f.getName())) {
				dirStream.forEach(path -> reader.processFile(path.toString(), showHeaders, onScreen));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processFile(String file, boolean hdr, boolean toSysOut) {

		try {
			ColDefReader rdr = new ColDefReader();
			FileDescriptor fd = rdr.readDefinition(file);

			System.out.println(fd);

			FileReader frdr = new FileReader();
			List<RowData> filedata = frdr.readFile(fd.datFile, fd);

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
					if (toSysOut) {
						System.out.println(bf.toString());
					}
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
				if (toSysOut) {
					System.out.println(bf.toString());
				}
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
