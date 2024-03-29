package eu.marxt12372.godrive;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentDrive extends AppCompatActivity {

	Button drive_cancel;
	Button drive_end;
	TextView current_drive_text;
	float markerLocationLat;
	float markerLocationLng;
	int driveStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_drive);

		Intent intent = getIntent();
		markerLocationLat = intent.getFloatExtra("drive_lat", 0.0f);
		markerLocationLng = intent.getFloatExtra("drive_lng", 0.0f);

		drive_cancel = (Button) findViewById(R.id.drive_cancel);
		drive_end = (Button) findViewById(R.id.drive_end);
		current_drive_text = (TextView) findViewById(R.id.current_drive_text);
		driveStatus = 3;

		Geocoder geocoder;
		List<Address> addresses;
		geocoder = new Geocoder(this, Locale.getDefault());

		try {
			addresses = geocoder.getFromLocation(markerLocationLat, markerLocationLng, 1);

			String city = addresses.get(0).getLocality();
			String state = addresses.get(0).getAdminArea();
			String country = addresses.get(0).getCountryName();
			String postalCode = addresses.get(0).getPostalCode();
			String tanav = addresses.get(0).getThoroughfare();
			String knownName = addresses.get(0).getFeatureName();

			current_drive_text.setText(country + "\n" + state + "\n" + city + "\n" + tanav + " " + knownName + "\n" + postalCode);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		drive_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				APIContactor.cancelCurrentDrive();
				finish();
			}
		});

		drive_end.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(driveStatus == 3)
				{
					drive_end.setText(R.string.end_drive);
					driveStatus = 4;
					APIContactor.clientPickup();
				}
				else if(driveStatus == 4)
				{
					APIContactor.endCurrentDrive();
					finish();
				}
			}
		});
	}

	protected void onDestroy()
	{
		APIContactor.cancelCurrentDrive();
		super.onDestroy();
	}

}
