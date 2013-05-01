package com.proyecto.bav;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayAddressesActivity extends Activity {
	private static ItemsAdapter adapter;
	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_addresses);
		listView = (ListView) findViewById(R.id.addresses_list);
		List<String> addresses = new ArrayList<String>();
		addresses.add("String 1");
		addresses.add("String 2");
		DisplayAddressesActivity.adapter = new ItemsAdapter(DisplayAddressesActivity.this, R.layout.address,0, addresses );
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
	
	private class ItemsAdapter extends ArrayAdapter<String> {
		private List<String> addressesStringList;
		public ItemsAdapter(Context context, int resource,
				int textViewResourceId, List<String> addresses) {
			super(context, resource, textViewResourceId, addresses);
			// TODO Auto-generated constructor stub
			this.addressesStringList = addresses;
		}
		
		// Fills the list view with product details
//		@Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//                View v = convertView;
//                if (v == null) {
//                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    v = vi.inflate(R.layout.dailypurchasecustomlist, null);
//            }
//                Product product =  productObjList1.get(position);
//                if (product != null) {
//                    ImageView productImage = (ImageView) v.findViewById(R.id.purchasedimage);
//                    TextView productName = (TextView) v.findViewById(R.id.purchasedname);
//                    TextView price = (TextView) v.findViewById(R.id.purchasedcost);
//                    TextView purchaseId = (TextView) v.findViewById(R.id.purchaseid);
//                    if (productImage != null) {
//                    	Bitmap bp = BitmapFactory.decodeByteArray(product.getImage(),0, product.getImage().length);
//                    	productImage.setImageBitmap(bp);
//                    	productName.setText(product.getName());
//                    	price.setText(Integer.toString(product.getPrice()));
//                    	purchaseId.setText(purchaseIdList[position]);
//                    }
//            }
//            return v;
//		}
	}
	
}
