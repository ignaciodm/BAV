package com.proyecto.bav;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class DatePickerActivity extends Activity {
	
	public final static String DAY = "com.proyecto.bav.DIA";
	public final static String MONTH = "com.proyecto.bav.MES";
	public final static String YEAR = "com.proyecto.bav.ANIO";
	
	private int date_month;
	private DatePickerActivity activity;
	private EditText mesEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_date_picker);
		
		activity = this;		
		mesEditText = (EditText) findViewById(R.id.date_month);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.date_picker, menu);
		return true;
	}
	
	/** Called when the user clicks the Aceptar button */
	public void aceptar(View view) {
		
		if(date_month == 0){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese el Mes", Toast.LENGTH_LONG).show();
			return;
		}
		
		EditText editTextDateDay = (EditText) findViewById(R.id.date_day);
		int date_day;
		try {
			date_day = Integer.parseInt(editTextDateDay.getText().toString());
		} catch (Exception e) {
			date_day = 0;
		}		
		
		EditText editTextDateYear = (EditText) findViewById(R.id.date_year);
		int date_year;
		try {
			date_year = Integer.parseInt(editTextDateYear.getText().toString());
		} catch (Exception e) {
			date_year = 0;
		}
		editTextDateYear = null;
		
		if(!fechaValida(date_day, date_month, date_year)){
			Toast.makeText(getApplicationContext(), "Fecha Invalida", Toast.LENGTH_LONG).show();
			return;
		}
		
		// Con esto cierro el Teclado si queda abierto
		InputMethodManager mgr = (InputMethodManager) 
		getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(editTextDateDay.getWindowToken(), 0);

		Intent returnIntent = new Intent();
		returnIntent.putExtra(DAY, date_day);
		returnIntent.putExtra(MONTH, date_month);
		returnIntent.putExtra(YEAR, date_year);
		setResult(RESULT_OK, returnIntent);
		this.finish();
		
	}

	private boolean fechaValida(int date_day, int date_month, int date_year) {
		
		if(date_day <= 0)
			return false;
		
		if(date_month <= 0)
			return false;
		
		if(date_year <= 0)
			return false;
		
		switch (date_month) {
		case 1:
			if(date_day > 31)
				return false;
		case 2:
			if(esBisiesto(date_year)){
				if(date_day > 29)
					return false;
			}
			else {
				if(date_day > 28)
					return false;	
			}
		case 3:
			if(date_day > 31)
				return false;	
		case 4:
			if(date_day > 30)
				return false;	
		case 5:
			if(date_day > 31)
				return false;	
		case 6:
			if(date_day > 30)
				return false;	
		case 7:
			if(date_day > 31)
				return false;	
		case 8:
			if(date_day > 31)
				return false;	
		case 9:
			if(date_day > 30)
				return false;	
		case 10:
			if(date_day > 31)
				return false;	
		case 11:
			if(date_day > 30)
				return false;	
		case 12:
			if(date_day > 31)
				return false;	
		default:
			break;			
		}
		
		return true;
	}
	
	private boolean esBisiesto(int year) {		
		return ( ( year %4 == 0 && year % 100 !=0) || (year % 400 == 0));
	}

	/** Called when the user clicks the Mes Field */
	public void selectDate(View view) {
		
		final List<String> monthNames = new ArrayList<String>();
		monthNames.add("Enero");
		monthNames.add("Febrero");
		monthNames.add("Marzo");
		monthNames.add("Abril");
		monthNames.add("Mayo");
		monthNames.add("Junio");
		monthNames.add("Julio");
		monthNames.add("Agosto");
		monthNames.add("Septiembre");
		monthNames.add("Octubre");
		monthNames.add("Noviembre");
		monthNames.add("Diciembre");
		
		CharSequence[] charMonthNames = monthNames.toArray(new CharSequence[monthNames.size()]);
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
		
		alertDialog.setItems(charMonthNames, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int monthIndex) {
				activity.date_month = monthIndex + 1;
				mesEditText.setText(monthNames.get(monthIndex));
			}
		});
		
		AlertDialog alert = alertDialog.create();
		alert.show();
		
	}

}
