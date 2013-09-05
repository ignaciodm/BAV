package com.proyecto.bav;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.bav.models.Address;

public class DisplayAddressesActivity extends BaseSpiceActivity {
	
	private static AddressesAdapter adapter;
	private List<Address> addresses;
	ListView listView;
	
	protected void onCreate(Bundle savedInstanceState) {
		
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
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Address address = addresses.get(position);
				Toast toast = Toast.makeText(getApplicationContext(), address.getDescription() + " Seleccionada", Toast.LENGTH_SHORT);
				toast.show();
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
		startActivity(intent);
	}
	
	private class AddressesAdapter extends ArrayAdapter<Address> {
		
		final private List<Address> addresses;
		
		public AddressesAdapter(Context context, int resource,
				int textViewResourceId, List<Address> addresses) {
			super(context, resource, textViewResourceId, addresses);
			// TODO Auto-generated constructor stub
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
                	
            	TextView descripcionText = (TextView) view.findViewById(R.id.address_descripcion);
                descripcionText.setText(address.getDescription());
                    
                TextView streetText = (TextView) view.findViewById(R.id.address_street);
                if(address.getNumber() == null)
                	streetText.setText(address.getStreet());
                else
                	streetText.setText(address.getStreet() + " " + address.getNumber());
            }
            
            return view;
            
		}
	}
	
}
