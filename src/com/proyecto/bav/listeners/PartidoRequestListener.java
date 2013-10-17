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
import com.proyecto.bav.models.Localidad;
import com.proyecto.bav.results.PartidoResult;

public class PartidoRequestListener implements
		RequestListener<PartidoResult> {
	
	private NewAddressActivity activity;

	public PartidoRequestListener(NewAddressActivity activity) {
		this.activity = activity;
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
	public void onRequestSuccess(final PartidoResult result) {
		
		final List<Localidad> localidades = result.getLocalidades();
		
		// Para ordenar por Descripción
		Collections.sort(localidades, new Comparator<Localidad>() {
			@Override
			public int compare(Localidad l1, Localidad l2) {
				return l1.getNombre().compareTo(l2.getNombre());
			}			
	    });
		
    	List<String> localidadesNames = new ArrayList<String>();
    	
    	for (Localidad localidad : localidades) {
    		localidadesNames.add(localidad.getNombre());
		}
    	
    	CharSequence[] charPartidosNames = localidadesNames.toArray(new CharSequence[localidadesNames.size()]);
        
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.AlertDialogCustom));
        
        if(localidades.size() == 0){
			alertDialog.setMessage("Todavía no hay Localidades de su Partido en nuestro sistema");
			alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
				   dialog.dismiss();
			   }
			});
        }
		else{   
        
	        alertDialog.setItems(charPartidosNames, new DialogInterface.OnClickListener() {	        	
	            public void onClick(DialogInterface dialog, int localidadIndex) {
	            	activity.localidad = localidades.get(localidadIndex);
	            	activity.localidadEditText.setText(activity.localidad.getNombre());
	            }	            
	        });
		}
        
        AlertDialog alert = alertDialog.create();
        alert.show();
	    
        activity.myProgressDialog.dismiss();
	}

}
