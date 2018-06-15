package com.pt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColDefTest {

	private File dummyFile;

	@Before
	public void setup() throws IOException {
		dummyFile = File.createTempFile("tmp", "junit");
	}

	@After
	public void cleanup() {
		if (dummyFile != null && dummyFile.exists()) {
			dummyFile.delete();
		}
		dummyFile = null;
	}

	@Test
	public void test_c_Type() {

		FileDescriptor fd = new FileDescriptor();

		ColDef cd = new ColDef();
		cd.colName = "Test";
		cd.dimensions = 1;
		cd.dimensionSize = new int[] { 1 };
		cd.dataSize = 4;
		cd.dataType = "c";

		fd.columns.add(cd);

		DummyStream ds;
		try {
			ds = new DummyStream(dummyFile);
			ds.setBytes(new byte[] { 0x32, 0x33, 0x34, 0x35 });

			RowData rd = new RowData();
			cd.getData(rd, ds);
			Assert.assertEquals(1, rd.rowData.size());
			Assert.assertEquals("Test", rd.rowData.get(0).colName);
			Assert.assertEquals("2345", rd.rowData.get(0).colValue);

		} catch (FileNotFoundException e) {
			// Should never get here
			Assert.assertEquals(true, false);
			e.printStackTrace();
		}
	}

	@Test
	public void test_I_Type() {

		FileDescriptor fd = new FileDescriptor();

		ColDef cd = new ColDef();
		cd.colName = "Test";
		cd.dimensions = 1;
		cd.dimensionSize = new int[] { 1 };
		cd.dataSize = 4;
		cd.dataType = "I";

		fd.columns.add(cd);

		DummyStream ds;
		try {
			ds = new DummyStream(dummyFile);
			ds.setBytes(new byte[] { 0x03, 0x34, (byte) 0xf3, (byte) 0xfa });

			RowData rd = new RowData();
			cd.getData(rd, ds);
			Assert.assertEquals(1, rd.rowData.size());
			Assert.assertEquals("Test", rd.rowData.get(0).colName);
			Assert.assertEquals("4210242563", rd.rowData.get(0).colValue);

		} catch (FileNotFoundException e) {
			// Should never get here
			Assert.assertEquals(true, false);
			e.printStackTrace();
		}
	}

	@Test
	public void test_I_Type_Array_1_dimension_2_entries() {

		FileDescriptor fd = new FileDescriptor();

		ColDef cd = new ColDef();
		cd.colName = "Test";
		cd.dimensions = 1;
		cd.dimensionSize = new int[] { 2 };
		cd.dataSize = 4;
		cd.dataType = "I";

		fd.columns.add(cd);

		DummyStream ds;
		try {
			ds = new DummyStream(dummyFile);
			ds.setBytes(new byte[] { 0x03, 0x34, (byte) 0xf3, (byte) 0xfa, 0x04, 0x35, (byte) 0xf4, (byte) 0xfb });

			RowData rd = new RowData();
			cd.getData(rd, ds);
			Assert.assertEquals(2, rd.rowData.size());
			Assert.assertEquals("Test[0]", rd.rowData.get(0).colName);
			Assert.assertEquals("4210242563", rd.rowData.get(0).colValue);
			Assert.assertEquals("Test[1]", rd.rowData.get(1).colName);
			Assert.assertEquals("4227085572", rd.rowData.get(1).colValue);

		} catch (FileNotFoundException e) {
			// Should never get here
			Assert.assertEquals(true, false);
			e.printStackTrace();
		}
	}

	@Test
	public void test_I_Type_Array_2_dimensions_2_by_2() {

		FileDescriptor fd = new FileDescriptor();

		ColDef cd = new ColDef();
		cd.colName = "Test";
		cd.dimensions = 2;
		cd.dimensionSize = new int[] { 2, 2 };
		cd.dataSize = 4;
		cd.dataType = "I";

		fd.columns.add(cd);

		DummyStream ds;
		try {
			ds = new DummyStream(dummyFile);
			ds.setBytes(new byte[] { 0x03, 0x34, (byte) 0xf3, (byte) 0xfa, 0x04, 0x35, (byte) 0xf4, (byte) 0xfb, 0x04,
					0x35, (byte) 0xf4, (byte) 0xfb, 0x03, 0x34, (byte) 0xf3, (byte) 0xfa, });

			RowData rd = new RowData();
			cd.getData(rd, ds);
			Assert.assertEquals(4, rd.rowData.size());
			Assert.assertEquals("Test[0][0]", rd.rowData.get(0).colName);
			Assert.assertEquals("4210242563", rd.rowData.get(0).colValue);
			Assert.assertEquals("Test[1][0]", rd.rowData.get(1).colName);
			Assert.assertEquals("4227085572", rd.rowData.get(1).colValue);
			Assert.assertEquals("Test[0][1]", rd.rowData.get(2).colName);
			Assert.assertEquals("4227085572", rd.rowData.get(2).colValue);
			Assert.assertEquals("Test[1][1]", rd.rowData.get(3).colName);
			Assert.assertEquals("4210242563", rd.rowData.get(3).colValue);

		} catch (FileNotFoundException e) {
			// Should never get here
			Assert.assertEquals(true, false);
			e.printStackTrace();
		}
	}

	@Test
	public void test_i_Type() {

		FileDescriptor fd = new FileDescriptor();

		ColDef cd = new ColDef();
		cd.colName = "Test";
		cd.dimensions = 1;
		cd.dimensionSize = new int[] { 1 };
		cd.dataSize = 4;
		cd.dataType = "i";

		fd.columns.add(cd);

		DummyStream ds;
		try {
			ds = new DummyStream(dummyFile);
			ds.setBytes(new byte[] { 0x03, 0x34, (byte) 0xf3, (byte) 0xfa });

			RowData rd = new RowData();
			cd.getData(rd, ds);
			Assert.assertEquals(1, rd.rowData.size());
			Assert.assertEquals("Test", rd.rowData.get(0).colName);
			Assert.assertEquals("-84724733", rd.rowData.get(0).colValue);

		} catch (FileNotFoundException e) {
			// Should never get here
			Assert.assertEquals(true, false);
			e.printStackTrace();
		}
	}

	@Test
	public void test_f_Type() {

		FileDescriptor fd = new FileDescriptor();

		ColDef cd = new ColDef();
		cd.colName = "Test";
		cd.dimensions = 1;
		cd.dimensionSize = new int[] { 1 };
		cd.dataSize = 4;
		cd.dataType = "f";

		fd.columns.add(cd);

		DummyStream ds;
		try {
			ds = new DummyStream(dummyFile);
			ds.setBytes(new byte[] { 0x00, 0x00, 0x40, 0x40 });

			RowData rd = new RowData();
			cd.getData(rd, ds);
			Assert.assertEquals(1, rd.rowData.size());
			Assert.assertEquals("Test", rd.rowData.get(0).colName);
			Assert.assertEquals("3.0", rd.rowData.get(0).colValue);

		} catch (FileNotFoundException e) {
			// Should never get here
			Assert.assertEquals(true, false);
			e.printStackTrace();
		}
	}

	@Test
	public void test_b_Type() {

		FileDescriptor fd = new FileDescriptor();

		ColDef cd = new ColDef();
		cd.colName = "Test";
		cd.dimensions = 1;
		cd.dimensionSize = new int[] { 1 };
		cd.dataSize = 1;
		cd.dataType = "b";

		fd.columns.add(cd);

		DummyStream ds;
		try {
			ds = new DummyStream(dummyFile);
			ds.setBytes(new byte[] { (byte) 0xD3 });

			RowData rd = new RowData();
			cd.getData(rd, ds);
			Assert.assertEquals(1, rd.rowData.size());
			Assert.assertEquals("Test", rd.rowData.get(0).colName);
			Assert.assertEquals("-45", rd.rowData.get(0).colValue);

		} catch (FileNotFoundException e) {
			// Should never get here
			Assert.assertEquals(true, false);
			e.printStackTrace();
		}
	}

	@Test
	public void test_B_Type() {

		FileDescriptor fd = new FileDescriptor();

		ColDef cd = new ColDef();
		cd.colName = "Test";
		cd.dimensions = 1;
		cd.dimensionSize = new int[] { 1 };
		cd.dataSize = 1;
		cd.dataType = "B";

		fd.columns.add(cd);

		DummyStream ds;
		try {
			ds = new DummyStream(dummyFile);
			ds.setBytes(new byte[] { (byte) 0xD3 });

			RowData rd = new RowData();
			cd.getData(rd, ds);
			Assert.assertEquals(1, rd.rowData.size());
			Assert.assertEquals("Test", rd.rowData.get(0).colName);
			Assert.assertEquals("-45", rd.rowData.get(0).colValue);

		} catch (FileNotFoundException e) {
			// Should never get here
			Assert.assertEquals(true, false);
			e.printStackTrace();
		}
	}

	@Test
	public void test_h_Type() {

		FileDescriptor fd = new FileDescriptor();

		ColDef cd = new ColDef();
		cd.colName = "Test";
		cd.dimensions = 1;
		cd.dimensionSize = new int[] { 1 };
		cd.dataSize = 2;
		cd.dataType = "h";

		fd.columns.add(cd);

		DummyStream ds;
		try {
			ds = new DummyStream(dummyFile);
			ds.setBytes(new byte[] { (byte) 0xf3, (byte) 0xfa });

			RowData rd = new RowData();
			cd.getData(rd, ds);
			Assert.assertEquals(1, rd.rowData.size());
			Assert.assertEquals("Test", rd.rowData.get(0).colName);
			Assert.assertEquals("64243", rd.rowData.get(0).colValue);

		} catch (FileNotFoundException e) {
			// Should never get here
			Assert.assertEquals(true, false);
			e.printStackTrace();
		}
	}

	@Test
	public void test_H_Type() {

		FileDescriptor fd = new FileDescriptor();

		ColDef cd = new ColDef();
		cd.colName = "Test";
		cd.dimensions = 1;
		cd.dimensionSize = new int[] { 1 };
		cd.dataSize = 2;
		cd.dataType = "H";

		fd.columns.add(cd);

		DummyStream ds;
		try {
			ds = new DummyStream(dummyFile);
			ds.setBytes(new byte[] { (byte) 0xf3, (byte) 0xfa });

			RowData rd = new RowData();
			cd.getData(rd, ds);
			Assert.assertEquals(1, rd.rowData.size());
			Assert.assertEquals("Test", rd.rowData.get(0).colName);
			Assert.assertEquals("64243", rd.rowData.get(0).colValue);

		} catch (FileNotFoundException e) {
			// Should never get here
			Assert.assertEquals(true, false);
			e.printStackTrace();
		}
	}

	public class DummyStream extends FileInputStream {

		private byte[] theBytes;
		private int fp = 0;

		public void setBytes(byte[] b) {
			this.theBytes = b;
		}

		public DummyStream(File arg0) throws FileNotFoundException {
			super(arg0);
		}

		public int read(byte[] b) {
			if (fp == this.theBytes.length) {
				return -1;
			}

			b[0] = this.theBytes[fp];
			fp++;
			return 1;
		}

	}
}
