package com.proyecto.bav.listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.NewAddressActivity;
import com.proyecto.bav.models.Provincia;
import com.proyecto.bav.results.ProvincesResult;

public class ProvincesRequestListener implements RequestListener<ProvincesResult> {

	private NewAddressActivity activity;

	public ProvincesRequestListener(NewAddressActivity activity) {
		this.activity = activity;
	}

	@Override
    public void onRequestFailure( SpiceException spiceException ) {
		activity.lookingFor = 0;
		Toast toast = Toast.makeText(activity.getApplicationContext(), "Error en la conexión.\nIntente nuevamente.", Toast.LENGTH_SHORT);
		toast.show();
		activity.myProgressDialog.dismiss();
    }
	
	@Override
	public void onRequestSuccess(final ProvincesResult result) {
		
		final List<Provincia> provincias = result.getProvincias();
		
		// Para ordenar por Descripción
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

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		
		if(provincias.size() == 0)
			builder.setTitle("No hay Provincias cargadas en el sistema");
		else
			builder.setTitle("Seleccione una Provincia");
		
		builder.setItems(charProvinceNames, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int provinceIndex) {
				activity.provincia = provincias.get(provinceIndex);
				activity.provinceEditText.setText(activity.provincia.getNombre());
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
		activity.lookingFor = 0;
		activity.myProgressDialog.dismiss();		
	}
}