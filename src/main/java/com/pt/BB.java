package com.pt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BB {

	public static void main(String[] args) {

		// 4210242563 = FA F3 34 03

		byte[] bs = new byte[] { 0x03, 0x34, (byte) 0xf3, (byte) 0xfa };
		// byte[] bs = new byte[] { (byte) 0xfa, (byte) 0xf3, 0x34, 0x03 };

		long v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).getInt();
		v &= 4294967295L;
		System.out.println(v);

		// 00 00 40 40 = 3.0 (real)
		byte[] bs2 = new byte[] { 0x00, 0x00, (byte) 0x40, (byte) 0x40 };
		double d = ByteBuffer.wrap(bs2).order(ByteOrder.LITTLE_ENDIAN).getFloat();
		System.out.println(d);

		byte[] bs3 = new byte[] { 0x47, (byte) 0xC0, 0x0, 0x0 };
		int s = ByteBuffer.wrap(bs3).order(ByteOrder.LITTLE_ENDIAN).getInt();
		s &= (short) 0xFFFF;
		System.out.println(s);

		byte[] dt = new byte[] { 0x32, 0x30, 0x31, 0x34, 0x2D, 0x30, 0x32, 0x30, 0x54, 0x31, 0x38, 0x3A, 0x32, 0x31,
				0x3A, 0x32, 0x33, 0x2E, 0x32, 0x35, 0x37 };
		String dts = new String(dt);
		System.out.println(dts);

	}
}
