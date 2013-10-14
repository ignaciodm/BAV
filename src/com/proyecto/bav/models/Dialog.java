package com.proyecto.bav.models;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;

import com.proyecto.bav.R;

public class Dialog {
	
	public static void showDialog(final Activity activity, final boolean finishActivity, final boolean backGround, String message){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
		alertDialog.setMessage(message);
		alertDialog.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
			   dialog.dismiss();
			   
			   if(finishActivity == true)
				   activity.finish();
		   }
		});
		
		AlertDialog alert = alertDialog.create();
        alert.show();
		
        if(backGround == true){
        	Button b = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
        	b.setBackgroundResource(R.drawable.background_button_rectangular);
        }
	    
	}

}
