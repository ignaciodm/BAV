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
import com.proyecto.bav.models.Comisaria;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.Localidad;
import com.proyecto.bav.results.LocalidadResult;

public class LocalidadRequestListener implements
		RequestListener<LocalidadResult> {
	
	private NewAddressActivity activity;
	private Localidad localidad;
	private boolean retry;

	public LocalidadRequestListener(NewAddressActivity activity, Localidad localidad, boolean retry) {
		this.activity = activity;
		this.localidad = localidad;
		this.retry = retry;
	}

	@Override
    public void onRequestFailure( SpiceException spiceException ) {
		
		if (spiceException instanceof NoNetworkException){
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
			activity.myProgressDialog.dismiss();
		}
		else {
			
			if(this.retry == true)
				activity.getComisarias(localidad, false);
			else{
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
				activity.myProgressDialog.dismiss();
			}

		}
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
        
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.AlertDialogCustom));
        
        if(comisarias.size() == 0){
			alertDialog.setMessage("Todavía no hay Comisarías de su Localidad en nuestro sistema");
			alertDialog.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
				   dialog.dismiss();
			   }
			});
        }
		else{
			
	        alertDialog.setItems(charComisariasNames, new DialogInterface.OnClickListener() {	        	
	            public void onClick(DialogInterface dialog, int comisariaIndex) {
	            	activity.comisaria = comisarias.get(comisariaIndex);
	            	activity.comisariaEditText.setText(activity.comisaria.getNombre());
	            }	            
	        });
	        
		}
        
        AlertDialog alert = alertDialog.create();
        alert.show();
	    
        activity.myProgressDialog.dismiss();
	}

}
