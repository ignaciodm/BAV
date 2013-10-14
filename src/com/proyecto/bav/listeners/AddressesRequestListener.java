package com.proyecto.bav.listeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.DisplayAddressesActivity;
import com.proyecto.bav.R;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.results.AddressesResult;

public class AddressesRequestListener implements RequestListener<AddressesResult> {

	private DisplayAddressesActivity activity;
	
	public AddressesRequestListener(DisplayAddressesActivity displayAddressesActivity) {
		this.activity = displayAddressesActivity;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
		alertDialog.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
			   dialog.dismiss();
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
		
		activity.myProgressDialog.dismiss();
	}

	@Override
	public void onRequestSuccess(AddressesResult result) {
		
		for(Address a: result.getAddresses())
			Address.save(a, activity.getApplicationContext());	
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		alertDialogBuilder.setMessage("Direcciones sincronizadas exitosamente");
	    alertDialogBuilder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
			   activity.fetchAddresses();
			   activity.myProgressDialog.dismiss();	
		   }
		});
	    
	    AlertDialog alert = alertDialogBuilder.create();
	    alert.show();
	    Button b = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
	    b.setBackgroundResource(R.drawable.background_button_rectangular);
		
	}

}
