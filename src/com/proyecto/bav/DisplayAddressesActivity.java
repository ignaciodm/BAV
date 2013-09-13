package com.proyecto.bav;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proyecto.bav.models.Address;

public class DisplayAddressesActivity extends BaseSpiceActivity {
	
	public final static String DIRECCION_SELECCIONADA = "com.proyecto.bav.DIRECCION_SELECCIONADA";
	public final static String NUEVA_DIRECCION = "com.proyecto.bav.NUEVA_DIRECCION";
	
	private DisplayAddressesActivity activity;
	private static AddressesAdapter adapter;
	private List<Address> addresses;
	private int posicionDireccionSeleccionada;
	ListView listView;
	
	protected void onCreate(Bundle savedInstanceState) {		
		activity = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_addresses);		
		fetchAddresses();
	}
	
	@Override
	protected void onRestart() {
	    super.onRestart();
	    fetchAddresses();
	}

	private void fetchAddresses() {
		
		listView = (ListView) findViewById(R.id.addresses_list);
		addresses = Address.getAddresses(this.getApplicationContext());
		DisplayAddressesActivity.adapter = new AddressesAdapter(DisplayAddressesActivity.this, R.layout.address_for_display_addresses, 0, addresses);
		listView.setAdapter(DisplayAddressesActivity.adapter);
		listView.setItemsCanFocus(true);
		
		// Mantener Direcci�n
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int direccionPos, long id) {
				
				posicionDireccionSeleccionada = direccionPos;
				
				List<String> opciones = new ArrayList<String>();
				opciones.add("Borrar Direcci�n"); // Posicion 0
				CharSequence[] opcionesList = opciones.toArray(new CharSequence[opciones.size()]);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		        //builder.setTitle("Opciones");
		        builder.setItems(opcionesList, new DialogInterface.OnClickListener() {
		        	
		            public void onClick(DialogInterface dialog, int posIndex) {
		            	
		            	switch (posIndex) {
		            	
						case 0:
							
							Toast toast = Toast.makeText(getApplicationContext(), "Borrando...", Toast.LENGTH_SHORT);
							toast.show();
							Address.delete(posicionDireccionSeleccionada, getApplicationContext());
							
							Intent intent = getIntent();
							finish();
							startActivity(intent);	
							
							break;
							
						default: break;
						
						}											
		            }
		        });		
		        
		        AlertDialog alert = builder.create();
		        alert.show();				
				return true;
				
			}
			
		});
		
		// Click sobre direcci�n
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				/*** Visualizar Direcci�n ***/
				
				// Get de la direcci�n
				Address a = addresses.get(position);
				
				// Lo paso a un JSON y la "grabo"
				Gson gson = new Gson();
				Type addressType = new TypeToken<Address>() {}.getType();
				String json = gson.toJson(a, addressType);
				
				Intent intent = new Intent(getApplicationContext(), NewAddressActivity.class);
				intent.putExtra(DIRECCION_SELECCIONADA, json);
				intent.putExtra(NUEVA_DIRECCION, false);
				startActivity(intent);				
				
			}
			
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_addresses, menu);
		return true;
	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.btn_new_address:
	        	newAddress();
	            return true;
	        case R.id.menu_new_address:
	        	newAddress();
	            return true;
	        case R.id.btn_sincronizar:
	        	sincronizar();
	            return true;
	        case R.id.menu_sincronizar:
	        	sincronizar();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void sincronizar() {
		// TODO Auto-generated method stub		
	}

	private void newAddress() {
		Intent intent = new Intent(this, NewAddressActivity.class);
		intent.putExtra(NUEVA_DIRECCION, true);
		startActivity(intent);
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
                if(address.getNumber() == null)
                	streetText.setText(address.getStreet() + " " + "S/N");
                else
                	streetText.setText(address.getStreet() + " " + address.getNumber());
            }
            
            return view;
            
		}
	}
	
}
