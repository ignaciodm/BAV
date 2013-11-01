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
import com.proyecto.bav.models.Partido;
import com.proyecto.bav.models.Provincia;
import com.proyecto.bav.results.ProvinceResult;

public class ProvinceRequestListener implements RequestListener<ProvinceResult> {
	
	private NewAddressActivity activity;
	private Provincia provincia;
	private boolean retry;

	public ProvinceRequestListener(NewAddressActivity activity, Provincia provincia, boolean retry) {
		this.activity = activity;
		this.provincia = provincia;
		this.retry = retry;
	}

	@Override
    public void onRequestFailure(SpiceException spiceException) {	
		
		if (spiceException instanceof NoNetworkException){
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
			activity.myProgressDialog.dismiss();
		}
		else {
			
			if(this.retry == true)
				activity.getPartidos(this.provincia, false);
			else{
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
				activity.myProgressDialog.dismiss();
			}
			
		}
    }
	
	@Override
	public void onRequestSuccess(final ProvinceResult result) {
		
		final List<Partido> partidos = result.getPartidos();
		
		// Para ordenar por Descripción
		Collections.sort(partidos, new Comparator<Partido>() {
			@Override
			public int compare(Partido p1, Partido p2) {
				return p1.getNombre().compareTo(p2.getNombre());
			}			
	    });

    	List<String> partidosNames = new ArrayList<String>();
    	
    	for (Partido partido : partidos) {
    		partidosNames.add(partido.getNombre());
		}
    	
    	CharSequence[] charPartidosNames = partidosNames.toArray(new CharSequence[partidosNames.size()]);
        
    	//AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.AlertDialogCustom));
        
        if(partidos.size() == 0){
			alertDialog.setMessage("Todavía no hay Partidos de su Provincia en nuestro sistema");
			alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
				   dialog.dismiss();
			   }
			});
        }
		else{
			
	        alertDialog.setItems(charPartidosNames, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int partidoIndex) {
	            	activity.partido = partidos.get(partidoIndex);
	            	activity.partidoEditText.setText(activity.partido.getNombre());
	            }
	        });
	        
		}
        
        AlertDialog alert = alertDialog.create();
        alert.show();
	    
        activity.myProgressDialog.dismiss();
		
	}
}
