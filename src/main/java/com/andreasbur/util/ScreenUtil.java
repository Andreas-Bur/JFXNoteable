package com.andreasbur.util;

import javafx.stage.Screen;

public class ScreenUtil {

	public static double convertMmToPx(double millimeters) {
		double dpi = Screen.getPrimary().getDpi();
		return millimeters * dpi / 25.4; //25.4mm per inch
	}

}
