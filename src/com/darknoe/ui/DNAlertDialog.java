package com.darknoe.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DNAlertDialog {
	
	public static void show(Context context, String title, String text, String buttonText){		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(text);
		// Add the buttons
		builder.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               // User clicked OK button
           }
        });
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
