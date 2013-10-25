package com.proyecto.bav.listeners;

import android.content.Intent;
import android.widget.Toast;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.MainActivity;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.User;
import com.proyecto.bav.results.UsuarioResult;

public class UserDeleteRequestListener implements RequestListener<UsuarioResult> {
	
	private MainActivity activity;
	
	public UserDeleteRequestListener(MainActivity mainActivity) {
		this.activity = mainActivity;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		activity.myProgressDialog.dismiss();
		
		if (spiceException instanceof NoNetworkException)
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
		else 
			Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
	}

	@Override
	public void onRequestSuccess(UsuarioResult result) {		
		
		User.destroy(activity.getApplicationContext());
		Address.destroy(activity.getApplicationContext());
		activity.myProgressDialog.dismiss();
		
		Toast.makeText(activity.getApplicationContext(), "Cuenta Eliminada", Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(intent);
	}

}
