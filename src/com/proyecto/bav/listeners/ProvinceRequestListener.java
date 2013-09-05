package com.proyecto.bav.listeners;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.NewAddressActivity;
import com.proyecto.bav.models.Partido;
import com.proyecto.bav.results.ProvinceResult;

public class ProvinceRequestListener implements
		RequestListener<ProvinceResult> {
	private NewAddressActivity activity;

	public ProvinceRequestListener(NewAddressActivity activity) {
		this.activity = activity;
	}

	@Override
    public void onRequestFailure( SpiceException spiceException ) {
//        Toast.makeText( NewAddressActivity.this, "failure", Toast.LENGTH_SHORT ).show();
//		Address.save(address, activity.getApplicationContext());
//		Intent intent = new Intent(activity, DisplayAddressesActivity.class);
//		activity.startActivity(intent);
//		
		Integer i;
		i = 1 + 1;
		
    }
	
	@Override
	public void onRequestSuccess(final ProvinceResult result) {
		final List<Partido> matches = result.getPartidos();
		
    	List<String> matchesNames = new ArrayList<String>();
    	for (Partido match : matches) {
    		matchesNames.add(match.getName());
		}
    	CharSequence[] charMatchesNames = matchesNames.toArray(new CharSequence[matchesNames.size()]);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Seleccione un Partido");
        builder.setItems(charMatchesNames, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int matchIndex) {
                // Do something with the selection
            	activity.partido = matches.get(matchIndex);
            	activity.partidoEditText.setText(activity.partido.getName());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
		
	}

}
