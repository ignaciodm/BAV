package com.proyecto.bav;

import java.util.Calendar;

import com.google.common.base.CaseFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint({ "NewApi", "ValidFragment" })
public class DatosPersonalesActivity extends Activity {
	
	int diaNacimiento;
	int mesNacimiento;
	int anioNacimiento;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datos_personales);
		fetchDatosPersonales();
	}

	private void fetchDatosPersonales() {
		// Email
		EditText editTextEmail = (EditText) findViewById(R.id.et_email);
		editTextEmail.setText("pabloserra89@gmail.com");		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.datos_personales, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.btn_guardar:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/** Called when the user clicks the Fecha de Nacimiento EditText */
	@SuppressLint("NewApi")
	public void selectDate(View view) {
		DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	@SuppressLint("NewApi")
	public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			diaNacimiento = day;
			mesNacimiento = month;
			anioNacimiento = year;
			((TextView) getActivity().findViewById(R.id.et_fecha_nacimiento)).setText(day + "/" + getMonthName(month) + "/" + year);
		}

		private String getMonthName(int month) {
			
			switch (month) {
			case 0:
				return "Ene";
			case 1:
				return "Feb";
			case 2:
				return "Mar";
			case 3:
				return "Abr";
			case 4:
				return "May";
			case 5:
				return "Jun";
			case 6:
				return "Jul";
			case 7:
				return "Ago";
			case 8:
				return "Sep";
			case 9:
				return "Oct";
			case 10:
				return "Nov";
			case 11:
				return "Dic";
			default:
				return "Mes";
			}			
			
		}
		
	}

}
