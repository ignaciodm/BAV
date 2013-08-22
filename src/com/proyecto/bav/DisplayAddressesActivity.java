package com.proyecto.bav;

import java.util.List;

import com.proyecto.bav.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.proyecto.bav.models.Address;

public class DisplayAddressesActivity extends BaseSpiceActivity {
	private static AddressesAdapter adapter;
	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_addresses);
		listView = (ListView) findViewById(R.id.addresses_list);
		List<Address> addresses = Address.getAddresses(this.getApplicationContext());
		DisplayAddressesActivity.adapter = new AddressesAdapter(DisplayAddressesActivity.this, R.layout.address,0, addresses );
		listView.setAdapter(DisplayAddressesActivity.adapter);
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
	        case R.id.new_address:
	        	newAddress();
	            return true;
	       /* case R.id.help:
	            showHelp();
	            return true;*/
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void newAddress() {
		Intent intent = new Intent(this, NewAddressActivity.class);
		startActivity(intent);
	}
	
	private class AddressesAdapter extends ArrayAdapter<Address> {
		private List<Address> addresses;
		public AddressesAdapter(Context context, int resource,
				int textViewResourceId, List<Address> addresses) {
			super(context, resource, textViewResourceId, addresses);
			// TODO Auto-generated constructor stub
			this.addresses = addresses;
		}
		
		// Fills the list view with addresses details
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.address, null);
            }
                Address address = this.addresses.get(position);
                if (address != null) {
                    TextView streetText = (TextView) v.findViewById(R.id.address_street);
                    streetText.setText(address.getStreet());
            }
            return v;
		}
	}
	
}
