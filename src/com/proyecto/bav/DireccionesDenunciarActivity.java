package com.proyecto.bav;

import java.util.List;

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
import android.widget.Toast;

import com.proyecto.bav.models.Address;

public class DireccionesDenunciarActivity extends BaseSpiceActivity {
	
	private final static int CONFIRMAR_ANIO = 1;

	private DireccionesDenunciarActivity activity;
	private static AddressesAdapter adapter;
	private List<Address> addresses;
	private Address address;
	ListView listView;
	
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
			Toast.makeText(getApplicationContext(), "No hay direcciones cargadas", Toast.LENGTH_SHORT).show();
			activity.finish();
		}
		
		listView = (ListView) findViewById(R.id.addresses_list);		
		DireccionesDenunciarActivity.adapter = new AddressesAdapter(DireccionesDenunciarActivity.this, R.layout.address_for_display_addresses, 0, addresses);
		listView.setAdapter(DireccionesDenunciarActivity.adapter);
		listView.setItemsCanFocus(true);
		
		// Click sobre direcci�n
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				/*** Denunciar con la Direcci�n Seleccionada ***/				
				// Get de la direcci�n
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
				Toast.makeText(getApplicationContext(), "Denunciando desde: " + address.getDescription(), Toast.LENGTH_LONG).show();  
				this.finish();
			}
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

}
