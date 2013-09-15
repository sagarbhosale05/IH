package com.ih;


public class AppURL {

	private static boolean devEnvironment;

	public static boolean isProdServer() {
		return devEnvironment;
	}

	public static void setToDev() {
		devEnvironment = false;
	}

	public static void setToProd() {
		devEnvironment = true;
	}

}
