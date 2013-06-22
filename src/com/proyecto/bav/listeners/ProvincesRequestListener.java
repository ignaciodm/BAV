package com.proyecto.bav.listeners;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.BaseSpiceActivity;
import com.proyecto.bav.DisplayAddressesActivity;
import com.proyecto.bav.NewAddressActivity;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Province;
import com.proyecto.bav.results.AddressResult;
import com.proyecto.bav.results.ProvinceResult;


// ============================================================================================
// INNER CLASSES
// ============================================================================================

public final class ProvincesRequestListener implements RequestListener< ProvinceResult > {

	private NewAddressActivity activity;

	public ProvincesRequestListener(NewAddressActivity activity) {
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
	
	public List<Province> getProvinces() {
		final String[] provincesNames = {
	                "Buenos Aires", "Mendoza", "Catamarca", "San Juan", "San Luis", "Tucuman", "Tierra del Fuego"
        };
		
		for (int i = 0; i < provincesNames.length; i++) {
			activity.provinces.add(new Province(i, provincesNames[i]));
		}
		
		return activity.provinces;
	        
	}

	@Override
	public void onRequestSuccess(final ProvinceResult result) {
		activity.provinces = result.getProvincias();
		
    	List<String> provincesNames = new ArrayList<String>();
    	for (Province province : activity.provinces) {
    		provincesNames.add(province.getName());
		}
    	CharSequence[] charProvinceNames = provincesNames.toArray(new CharSequence[provincesNames.size()]);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Make your selection");
        builder.setItems(charProvinceNames, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int provinceIndex) {
                // Do something with the selection
            	activity.province = activity.provinces.get(provinceIndex);
            	activity.provinceEditText.setText(activity.province.getName());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
		
	}
}