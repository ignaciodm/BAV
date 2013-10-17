package com.proyecto.bav.listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.NewAddressActivity;
import com.proyecto.bav.R;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.Provincia;
import com.proyecto.bav.results.ProvincesResult;

public class ProvincesRequestListener implements RequestListener<ProvincesResult> {

	private NewAddressActivity activity;

	public ProvincesRequestListener(NewAddressActivity activity) {
		this.activity = activity;
	}

	@Override
    public void onRequestFailure(SpiceException spiceException) {
		
		activity.myProgressDialog.dismiss();
		
		if (spiceException instanceof NoNetworkException)
			Dialog.showDialog(activity, false, true, "No hay conexi�n. Intente nuevamente");
		else 
			Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexi�n. Intente nuevamente");
    }
	
	@Override
	public void onRequestSuccess(final ProvincesResult result) {
		
		final List<Provincia> provincias = result.getProvincias();
		
		// Para ordenar por Descripci�n
		Collections.sort(provincias, new Comparator<Provincia>() {
			@Override
			public int compare(Provincia p1, Provincia p2) {
				return p1.getNombre().compareTo(p2.getNombre());
			}			
	    });
		
		List<String> provincesNames = new ArrayList<String>();

		for (Provincia province : provincias) {
			provincesNames.add(province.getNombre());
		}

		CharSequence[] charProvinceNames = provincesNames.toArray(new CharSequence[provincesNames.size()]);

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.AlertDialogCustom));
		
		if(provincias.size() == 0){
			alertDialog.setMessage("No hay Provincias cargadas en el sistema");
			alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
				   dialog.dismiss();
			   }
			});
			
        }
		else{
			
			alertDialog.setItems(charProvinceNames, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int provinceIndex) {
					activity.provincia = provincias.get(provinceIndex);
					activity.provinceEditText.setText(activity.provincia.getNombre());
				}
			});
			
		}

		AlertDialog alert = alertDialog.create();
		alert.show();
	    
		activity.myProgressDialog.dismiss();		
	}
}