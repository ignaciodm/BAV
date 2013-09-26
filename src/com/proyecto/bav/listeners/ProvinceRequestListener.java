package com.proyecto.bav.listeners;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.NewAddressActivity;
import com.proyecto.bav.models.Partido;
import com.proyecto.bav.results.ProvinceResult;

public class ProvinceRequestListener implements RequestListener<ProvinceResult> {
	
	private NewAddressActivity activity;

	public ProvinceRequestListener(NewAddressActivity activity) {
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
	public void onRequestSuccess(final ProvinceResult result) {
		
		final List<Partido> partidos = result.getPartidos();
		
    	List<String> partidosNames = new ArrayList<String>();
    	
    	for (Partido partido : partidos) {
    		partidosNames.add(partido.getNombre());
		}
    	
    	CharSequence[] charPartidosNames = partidosNames.toArray(new CharSequence[partidosNames.size()]);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Seleccione un Partido");
        builder.setItems(charPartidosNames, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int partidoIndex) {
            	activity.partido = partidos.get(partidoIndex);
            	activity.partidoEditText.setText(activity.partido.getNombre());
            }
        });
        
        AlertDialog alert = builder.create();
        alert.show();
        activity.lookingFor = 0;
        activity.myProgressDialog.dismiss();
		
	}
}
