package com.proyecto.bav.listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.R;
import com.proyecto.bav.RegistroActivity;
import com.proyecto.bav.results.UsuarioResult;

public class RegistrarUsuarioRequestListener implements RequestListener<UsuarioResult> {

	private RegistroActivity activity;
	
	public RegistrarUsuarioRequestListener(RegistroActivity registroActivity) {
		this.activity = registroActivity;
	}

	@Override
	public void onRequestFailure(SpiceException result) {		
		activity.myProgressDialog.dismiss();
	}

	@Override
	public void onRequestSuccess(UsuarioResult result) {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		alertDialogBuilder.setMessage("Cuenta creada exitosamente.\n\nVerifique su cuenta de email para confirmar el registro.");
	    alertDialogBuilder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
			   activity.myProgressDialog.dismiss();
			   
			   // Con esto cierro el Teclado si queda abierto
				InputMethodManager mgr = (InputMethodManager) 
				activity.getSystemService(Context.INPUT_METHOD_SERVICE);
				mgr.hideSoftInputFromWindow(activity.editTextFechaNacimiento.getWindowToken(), 0);
			   
			   activity.finish();
		   }
		});
	    
	    AlertDialog alert = alertDialogBuilder.create();
	    alert.show();
	    Button b = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
	    b.setBackgroundResource(R.drawable.background_button_rectangular);		
	}

}
