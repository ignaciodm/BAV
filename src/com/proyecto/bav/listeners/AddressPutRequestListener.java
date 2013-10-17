package com.proyecto.bav.listeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.NewAddressActivity;
import com.proyecto.bav.R;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.AddressResult;

public class AddressPutRequestListener implements RequestListener<AddressResult> {
	
	private NewAddressActivity activity;
	
	public AddressPutRequestListener(NewAddressActivity newAddressRequestListener) {
		this.activity = newAddressRequestListener;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
		alertDialog.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
			   activity.myProgressDialog.dismiss();
		   }
		});
		
		if (spiceException instanceof NoNetworkException)
			alertDialog.setMessage("No hay conexión. Intente nuevamente");
		else 
			alertDialog.setMessage("Ha ocurrido un error con la conexión. Intente nuevamente");
		
		AlertDialog alert = alertDialog.create();
        alert.show();
		
	    Button b = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
	    b.setBackgroundResource(R.drawable.background_button_rectangular);
	}

	@Override
	public void onRequestSuccess(AddressResult result) {		
		Address.save(result.getAddress(), activity.getApplicationContext());	    
	    activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, true, "Dirección Guardada");
	}

}
