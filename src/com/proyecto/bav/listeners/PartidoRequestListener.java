package com.proyecto.bav.listeners;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.NewAddressActivity;
import com.proyecto.bav.models.Localidad;
import com.proyecto.bav.results.PartidoResult;

public class PartidoRequestListener implements
		RequestListener<PartidoResult> {
	
	private NewAddressActivity activity;

	public PartidoRequestListener(NewAddressActivity activity) {
		this.activity = activity;
	}

	@Override
    public void onRequestFailure( SpiceException spiceException ) {
		activity.lookingFor = 0;
		Toast toast = Toast.makeText(activity.getApplicationContext(), "Fail Localidad", Toast.LENGTH_SHORT);
		toast.show();
    }
	
	@Override
	public void onRequestSuccess(final PartidoResult result) {
		
		final List<Localidad> localidades = result.getLocalidades();
		
    	List<String> localidadesNames = new ArrayList<String>();
    	
    	for (Localidad localidad : localidades) {
    		localidadesNames.add(localidad.getNombre());
		}
    	
    	CharSequence[] charPartidosNames = localidadesNames.toArray(new CharSequence[localidadesNames.size()]);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        
        builder.setTitle("Seleccione una Localidad");
        builder.setItems(charPartidosNames, new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int localidadIndex) {
                // Do something with the selection
            	activity.localidad = localidades.get(localidadIndex);
            	activity.localidadEditText.setText(activity.localidad.getNombre());
            }
            
        });
        
        AlertDialog alert = builder.create();
        alert.show();
        activity.lookingFor = 0;
		
	}

}
