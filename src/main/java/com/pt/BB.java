package com.pt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Little test class to see how we need to read bytes from a file and massage
 * them into proper java values.
 * 
 * @author PT
 *
 */
public class BB {

	public static void main(String[] args) {

		String utc = "2012-074T18:30:53.683";
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-DDD'T'HH:mm:ss.SSS");
		Date dt2;
		try {
			dt2 = sd.parse(utc);
			System.out.println(dt2);
			SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			System.out.println(sd2.format(dt2));
			System.out.println("Epoch:" + dt2.getTime());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// We read 03 34 F3 FA from the file
		byte[] bs = new byte[] { 0x03, 0x34, (byte) 0xf3, (byte) 0xfa };

		// The array 03 34 F3 FA should be translated to 4210242563
		// But 4210242563 = FA F3 34 03 which is the reversed order of bytes as
		// we read from the file. This means we need to tell the ByteBuffer to
		// flip the order of bytes around.

		// unsigned long
		long v = ByteBuffer.wrap(bs).order(ByteOrder.LITTLE_ENDIAN).getInt();
		v &= 4294967295L;
		System.out.println(v);

		// 00 00 40 40 = 3.0 (real)
		byte[] bs2 = new byte[] { 0x00, 0x00, (byte) 0x40, (byte) 0x40 };
		double d = ByteBuffer.wrap(bs2).order(ByteOrder.LITTLE_ENDIAN).getFloat();
		System.out.println(d);

		// unsigned short
		byte[] bs3 = new byte[] { 0x47, (byte) 0xC0, 0x0, 0x0 };
		int s = ByteBuffer.wrap(bs3).order(ByteOrder.LITTLE_ENDIAN).getInt();
		s &= (short) 0xFFFF;
		System.out.println(s);

		// DATE string
		byte[] dt = new byte[] { 0x32, 0x30, 0x31, 0x34, 0x2D, 0x30, 0x32, 0x30, 0x54, 0x31, 0x38, 0x3A, 0x32, 0x31,
				0x3A, 0x32, 0x33, 0x2E, 0x32, 0x35, 0x37 };
		String dts = new String(dt);
		System.out.println(dts);

	}
}
