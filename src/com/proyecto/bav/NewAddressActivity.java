package com.proyecto.bav;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.listeners.AddressRequestListener;
import com.proyecto.bav.listeners.ProvinceRequestListener;
import com.proyecto.bav.listeners.ProvincesRequestListener;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Locality;
import com.proyecto.bav.models.Match;
import com.proyecto.bav.models.Province;
import com.proyecto.bav.models.PoliceStation;
import com.proyecto.bav.requests.GetProvinceRequest;
import com.proyecto.bav.requests.GetProvincesRequest;
import com.proyecto.bav.requests.GetSpiceRequest;
import com.proyecto.bav.requests.PostAddressSpiceRequest;
import com.proyecto.bav.requests.SimpleSpiceRequest;
import com.proyecto.bav.results.AddressResult;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewAddressActivity extends BaseSpiceActivity {

	public EditText provinceEditText;
	public EditText matchEditText;
//	public EditText localityEditText;
//	public EditText policeStationEditText;
	public Province province;
	public Match match;
	public Locality locality;
	public PoliceStation policeStation;
	
	public List<Province> provinces = new ArrayList<Province>();
	public List<Match> matches = new ArrayList<Match>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature( Window.FEATURE_PROGRESS );
		setContentView(R.layout.activity_new_address);
		
		provinceEditText = (EditText) findViewById(R.id.address_province);
		matchEditText = (EditText) findViewById(R.id.address_match);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_address, menu);
		return true;
	}
	
    public void displayProvinces(View view) {
    	getSpiceManager().execute( new GetProvincesRequest(), 
									"provincias",
									DurationInMillis.ONE_MINUTE,
									new ProvincesRequestListener(this));
    }
    
    public void displayMatches(View view) {
    	getSpiceManager().execute( new GetProvinceRequest(province), 
				"provincia/" + province.getId(),
				DurationInMillis.ONE_MINUTE,
				new ProvinceRequestListener(this));
    }
	
	public void saveAddress(View view) {
	    EditText descriptionEditText = (EditText) findViewById(R.id.address_description);
	    EditText streetEditText = (EditText) findViewById(R.id.address_street);
	    
	    String description = descriptionEditText.getText().toString();
	    String street = streetEditText.getText().toString();
	    int number = 10; //TODO exponer edicion
		Address address = new Address(description, street, number);
	    getSpiceManager().execute( new PostAddressSpiceRequest(address), 
	    							"json",
	    							DurationInMillis.ONE_MINUTE,
	    							new AddressRequestListener(address, this));
	}
	
}
