package com.pt;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Holds the column definition for one column, and also has a method to do a
 * read operation on an inputStream and fill ColData object(s) with values it
 * reads and adds to a RowData object.
 * 
 * @author PT
 *
 */
public class ColDef {

	public String colName;
	/**
	 * Data type according to the Python convention:
	 * https://docs.python.org/2/library/struct.html
	 */
	public String dataType;
	/**
	 * Number of bytes we'll read for one instance value.
	 */
	public int dataSize;
	/**
	 * Number of dimensions (for arrays). E.g. a 2x4 array would be 2 (since it
	 * has 2 axes). A regular single value 1x1, so the dimensions=1 and
	 * dimensionSize[0] would be 1.
	 */
	public int dimensions = 1;
	/**
	 * Array with dimension sizes. E.g. a 2x4 array would be an array of {2,4}
	 */
	public int[] dimensionSize = new int[] { 1 };

	/**
	 * Method to data read from the inputStream according to this column's
	 * definition, and add the values to the RowData object. This method assumes
	 * the file pointer inside the inputStream is at the correct location.
	 * 
	 * @param accum
	 * @param fis
	 * @return
	 */
	public boolean getData(RowData accum, FileInputStream fis) {
		boolean more = true;
		try {

			// Calculate total number of entries we need to read. For single
			// value it will be 1, but for an array of e.g. 2x4 it would be 8.
			int t = 1;
			for (int j = 0; j < dimensionSize.length; j++) {
				t = (t * dimensionSize[j]);
			}

			// =MOD(INT((A2-1)/1),3)
			// =MOD(INT((A2-1)/3),4)
			// =MOD(INT((A2-1)/12),2)

			// Loop for as many entries we will read from the file
			for (int j = 1; j <= t; j++) {

				// Read the bytes from the file and fill bs byte array
				byte[] bs = new byte[dataSize];
				for (int i = 0; i < dataSize; i++) {
					byte[] x = new byte[1];
					int nread = fis.read(x);
					if (nread == -1) {
						throw new IOException("End of the input file");
					}
					bs[i] = x[0];
				}

				// Construct a column name
				StringBuilder col = new StringBuilder(colName);
				if (t > 1) {
					// More than one value to read: so lets make a column name
					// that has a colName[x][y] syntax to show which one we are
					// talking about.
					int devider = 1;
					for (int k = 0; k < dimensionSize.length; k++) {
						int idx = ((j - 1) / devider) % dimensionSize[k];
						col.append("[").append(idx).append("]");
						devider = devider * dimensionSize[k];
					}
				}

				// ColData will hold the data we read.
				ColData coldat = new ColData();
				coldat.colName = col.toString();

				// unsigned
				if (dataType.equals("H")) {
					byte[] n = new byte[4];
					n[0] = bs[0];
					n[1] = bs[1];
					long v = ByteBuffer.wrap(n).order(ByteOrder.LITTLE_ENDIAN).getInt();
					v &= (short) 0xFFFF;
					coldat.colValue = String.valueOf(v);
				}

				// signed
				if (dataType.equals("h")) {
					byte[] n = new byte[4];
					n[0] = bs[0];
					n[1] = bs[1];
					long v = ByteBuffer.wrap(n).order(ByteOrder.LITTLE_ENDIAN).getInt();
					coldat.colValue = String.valueOf(v);
				}

				// unsigned
				if (dataType.equals("I")) {
					long v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).getInt();
					v &= 0xFFFFFFFFL;
					coldat.colValue = String.valueOf(v);
				}

				// signed
				if (dataType.equals("i")) {
					long v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).getInt();
					coldat.colValue = String.valueOf(v);
				}

				// unsigned
				if (dataType.equals("B")) {
					byte v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).get();
					v &= 0xFF;
					coldat.colValue = String.valueOf(v);
				}

				// signed
				if (dataType.equals("b")) {
					byte v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).get();
					coldat.colValue = String.valueOf(v);
				}

				// float
				if (dataType.equals("f")) {
					double v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).getFloat();
					coldat.colValue = String.valueOf(v);
				}

				// char
				if (dataType.equals("c")) {
					coldat.colValue = new String(bs);
				}

				// Append to the row
				accum.rowData.add(coldat);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
			more = false;
		}

		return more;
	}

	public String toString() {
		String r = "ColName:" + colName + "," + "DataType:" + dataType + "," + "DataSize:" + dataSize + ","
				+ "Dimensions:" + dimensions + "," + "DimensionSizes:(";

		for (int i = 0; i < dimensionSize.length; i++) {
			r = r + "[" + i + "]=" + dimensionSize[i];
		}
		r = r + ")";
		return r;
	}
}
