package com.pt;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ColDef {

	public String colName;
	public String dataType;
	public int dataSize;
	public int dimensions = 1;
	public int[] dimensionSize = new int[] { 1 };

	public boolean getData(RowData accum, FileInputStream fis) {
		boolean more = true;
		try {

			int t = 1;
			for (int j = 0; j < dimensionSize.length; j++) {
				t = (t * dimensionSize[j]);
			}

			int[] c = new int[dimensionSize.length];

			// =MOD(INT((A2-1)/1),3)
			// =MOD(INT((A2-1)/3),4)
			// =MOD(INT((A2-1)/12),2)

			for (int j = 1; j <= t; j++) {
				byte[] bs = new byte[dataSize];
				for (int i = 0; i < dataSize; i++) {
					byte[] x = new byte[1];
					int nread = fis.read(x);
					if (nread == -1) {
						throw new IOException("End of the input file");
					}
					bs[i] = x[0];
				}

				String col = colName;
				if (t > 1) {
					int devider = 1;
					for (int k = 0; k < dimensionSize.length; k++) {
						c[k] = ((j - 1) / devider) % dimensionSize[k];
						col = col + "[" + c[k] + "]";
						devider = devider * dimensionSize[k];
					}
				}

				ColData coldat = new ColData();
				coldat.colName = col;

				if (dataType.equals("H")) {
					byte[] n = new byte[4];
					n[0] = bs[0];
					n[1] = bs[1];
					long v = ByteBuffer.wrap(n).order(ByteOrder.LITTLE_ENDIAN).getInt();
					v &= (short) 0xFFFF;
					coldat.colValue = String.valueOf(v);
				}

				if (dataType.equals("h")) {
					byte[] n = new byte[4];
					n[0] = bs[0];
					n[1] = bs[1];
					long v = ByteBuffer.wrap(n).order(ByteOrder.LITTLE_ENDIAN).getInt();
					coldat.colValue = String.valueOf(v);
				}

				if (dataType.equals("I")) {
					long v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).getInt();
					v &= 4294967295L;
					coldat.colValue = String.valueOf(v);
				}

				if (dataType.equals("i")) {
					long v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).getInt();
					coldat.colValue = String.valueOf(v);
				}

				if (dataType.equals("B")) {
					byte v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).get();
					coldat.colValue = String.valueOf(v & 0xFF);
				}

				if (dataType.equals("b")) {
					byte v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).get();
					coldat.colValue = String.valueOf(v & 0xFF);
				}

				if (dataType.equals("f")) {
					double v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).getFloat();
					coldat.colValue = String.valueOf(v);
				}

				if (dataType.equals("c")) {
					coldat.colValue = new String(bs);
				}

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
