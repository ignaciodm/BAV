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
import com.proyecto.bav.models.Comisaria;
import com.proyecto.bav.results.LocalidadResult;

public class LocalidadRequestListener implements
		RequestListener<LocalidadResult> {
	
	private NewAddressActivity activity;

	public LocalidadRequestListener(NewAddressActivity activity) {
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
	public void onRequestSuccess(final LocalidadResult result) {
		
		final List<Comisaria> comisarias = result.getComisarias();
		
		// Para ordenar por Descripción
		Collections.sort(comisarias, new Comparator<Comisaria>() {
			@Override
			public int compare(Comisaria c1, Comisaria c2) {
				return c1.getNombre().compareTo(c2.getNombre());
			}			
	    });
		
    	List<String> comisariasNames = new ArrayList<String>();
    	
    	for (Comisaria comisaria : comisarias) {
    		comisariasNames.add(comisaria.getNombre());
		}
    	
    	CharSequence[] charComisariasNames = comisariasNames.toArray(new CharSequence[comisariasNames.size()]);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        
        if(comisarias.size() == 0)
			builder.setTitle("Todavía no hay Comisarías de su Localidad en nuestro sistema");
		else
			builder.setTitle("Seleccione una Comisaría");
        
        builder.setItems(charComisariasNames, new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int comisariaIndex) {
                // Do something with the selection
            	activity.comisaria = comisarias.get(comisariaIndex);
            	activity.comisariaEditText.setText(activity.comisaria.getNombre());
            }
            
        });
        
        AlertDialog alert = builder.create();
        alert.show();
        activity.lookingFor = 0;
        activity.myProgressDialog.dismiss();
	}

}
