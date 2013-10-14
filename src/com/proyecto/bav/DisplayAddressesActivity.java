package com.proyecto.bav;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.proyecto.bav.listeners.AddressesRequestListener;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.User;
import com.proyecto.bav.requests.GetAddressesRequest;

public class DisplayAddressesActivity extends BaseSpiceActivity {
	
	public final static String DIRECCION_SELECCIONADA = "com.proyecto.bav.DIRECCION_SELECCIONADA";
	public final static String NUEVA_DIRECCION = "com.proyecto.bav.NUEVA_DIRECCION";
	final static int SINCRONIZAR = 1;
	
	private DisplayAddressesActivity activity;
	private static AddressesAdapter adapter;
	private List<Address> addresses;
	private int posicionDireccionSeleccionada;
	ListView listView;
	
	public ProgressDialog myProgressDialog;
	
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
	
	@Override
	protected void onResume() {
	    super.onResume();
	    fetchAddresses();
	}

	public void fetchAddresses() {
		
		listView = (ListView) findViewById(R.id.addresses_list);
		addresses = Address.getAddresses(this.getApplicationContext());
		DisplayAddressesActivity.adapter = new AddressesAdapter(DisplayAddressesActivity.this, R.layout.address_for_display_addresses, 0, addresses);
		listView.setAdapter(DisplayAddressesActivity.adapter);
		listView.setItemsCanFocus(true);
		
		// Mantener Dirección
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int direccionPos, long id) {
				
				posicionDireccionSeleccionada = direccionPos;
				
				List<String> opciones = new ArrayList<String>();
				opciones.add("Borrar Dirección"); // Posicion 0
				CharSequence[] opcionesList = opciones.toArray(new CharSequence[opciones.size()]);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.AlertDialogCustom));
		        builder.setItems(opcionesList, new DialogInterface.OnClickListener() {
		        	
		            public void onClick(DialogInterface dialog, int posIndex) {
		            	
		            	switch (posIndex) {
		            	
						case 0:
							
							Dialog.showDialog(activity, false, true, "Direccion eliminada");
							Address.delete(posicionDireccionSeleccionada, getApplicationContext());
							
							fetchAddresses();	
							
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
		
		// Click sobre dirección
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				/*** Visualizar Dirección ***/
				
				// Get de la dirección
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
		getMenuInflater().inflate(R.menu.display_addresses, menu);
		
		if(android.os.Build.VERSION.SDK_INT <= 11){
			menu.findItem(R.id.btn_new_address).setIcon(R.drawable.ic_new_address_black);
			menu.findItem(R.id.btn_sincronizar).setIcon(R.drawable.ic_sincronizar_black);
		}
		
		return true;
	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.btn_new_address:
	        	newAddress();
	            return true;
	        case R.id.btn_sincronizar:
	        	confirmarPassUpdate();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void confirmarPassUpdate() {
		Intent intent = new Intent(this, ConfirmarPassActivity.class);
		startActivityForResult(intent, SINCRONIZAR);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == SINCRONIZAR)
			if(resultCode == RESULT_OK)
				sincronizar();				
	}
	
	private void sincronizar() {
		
		myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
		myProgressDialog.setTitle("Por favor, espere...");
		myProgressDialog.setMessage("Sincronizando...");
		myProgressDialog.show();
		
		User user = User.getUser(this.getApplicationContext());
		
		getSpiceManager().execute(new GetAddressesRequest(user.getId(), user.getAuthToken()),
				null, 
				DurationInMillis.ONE_MINUTE,
				new AddressesRequestListener(this));
		
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
                if(address.getNumero() == null)
                	streetText.setText(address.getCalle() + " " + "S/N");
                else
                	streetText.setText(address.getCalle() + " " + address.getNumero());
            }
            
            return view;
            
		}
	}
	
}
