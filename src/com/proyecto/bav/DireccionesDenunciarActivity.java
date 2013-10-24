package com.proyecto.bav;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.proyecto.bav.listeners.DenunciarRequestListener;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.User;
import com.proyecto.bav.requests.PostDenunciarRequest;

public class DireccionesDenunciarActivity extends BaseSpiceActivity {
	
	private final static int CONFIRMAR_ANIO = 1;

	private DireccionesDenunciarActivity activity;
	private static AddressesAdapter adapter;
	private List<Address> addresses;
	private Address address;
	ListView listView;
	int reintento;
	
	public ProgressDialog myProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.activity = this;
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_direcciones_denunciar);
	    fetchAddresses();
	}
	
	private void fetchAddresses() {
		
		addresses = Address.getAddresses(this.getApplicationContext());
		
		if(addresses.size() == 0){
			Dialog.showDialog(this, true, false, "No hay direcciones cargadas");
		}
		
		listView = (ListView) findViewById(R.id.addresses_list);		
		DireccionesDenunciarActivity.adapter = new AddressesAdapter(DireccionesDenunciarActivity.this, R.layout.address_for_display_addresses, 0, addresses);
		listView.setAdapter(DireccionesDenunciarActivity.adapter);
		listView.setItemsCanFocus(true);
		
		// Click sobre dirección
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				/*** Denunciar con la Dirección Seleccionada ***/				
				// Get de la dirección
				address = addresses.get(position);				
							
				Intent intent = new Intent(activity, ConfirmarAnioActivity.class);
				startActivityForResult(intent, CONFIRMAR_ANIO);
				
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.direcciones_denunciar, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CONFIRMAR_ANIO)
			if(resultCode == RESULT_OK){
				
				myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
				myProgressDialog.setTitle("Por favor, espere...");
				myProgressDialog.setMessage("Denunciando desde " + address.getDescription() + "...");
				myProgressDialog.show();
				
				this.denunciar(getDenunciarJSON(address.getId(), User.getTokenUser(getApplicationContext())));
			}
	}
	
	private String getDenunciarJSON(int id, String authToken) {
		return "{\"direccionId\":" + id + "," + "\"authToken\":" + "\"" + authToken + "\""+ "}";
	}

	private class AddressesAdapter extends ArrayAdapter<Address> {
		
		final private List<Address> addresses;
		
		public AddressesAdapter(Context context, int resource, int textViewResourceId, List<Address> addresses) {
			super(context, resource, textViewResourceId, addresses);
			this.addresses = addresses;
		}
		
		// Fills the list view with addresses details
		@Override
        public View getView(final int position, View view, ViewGroup parent) {
                
			if (view == null) {
            	LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            	view = vi.inflate(R.layout.address_for_display_addresses, null);
            }
			
			Address address = this.addresses.get(position);
			
            if (address != null) {
                	
            	TextView descripcionText = (TextView) view.findViewById(R.id.display_address_descripcion);
                descripcionText.setText(address.getDescription());
                    
                TextView streetText = (TextView) view.findViewById(R.id.display_address_street);
                if(address.getNumero() == null)
                	streetText.setText(address.getCalle() + " " + "S/N");
                else
                	streetText.setText(address.getCalle() + " " + address.getNumero());
            }
            
            return view;
            
		}
	}

	public void denunciar(String jsonDeDenuncia) {
		
		if(reintento < 20){
			
			reintento++;
		
			getSpiceManager().execute(new PostDenunciarRequest(jsonDeDenuncia),
					null, 
					DurationInMillis.ONE_MINUTE,
					new DenunciarRequestListener(this, jsonDeDenuncia));
		} else {
			myProgressDialog.dismiss();
		}
		
	}

}
