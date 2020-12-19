package net.voxelmine.util;

public class Logger {
	public static void info(String msg) {
		System.out.println("[INFO] " + msg);
	}
	public static void warn(String msg) {
		System.out.println("[WARN] " + msg);
	}
	public static void fail(String msg) {
		System.err.println("[FAIL] " + msg);
		System.exit(-1);
	}
}
