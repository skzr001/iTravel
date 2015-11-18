/**
 * 
 */
package com.example.travelguide;


import android.widget.EditText;


public class AMapUtil {
	
	public static String checkEditText(EditText editText) {
		if (editText != null && editText.getText() != null
				&& !(editText.getText().toString().trim().equals(""))) {
			return editText.getText().toString().trim();
		} else {
			return "";
		}
	}

	
}
