package eu.marxt12372.godrive;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import eu.marxt12372.godrive.R;

public class DriveAcceptActivity extends FragmentActivity implements OnMapReadyCallback {

	private GoogleMap mMap;
	private float markerLocationLat;
	private float markerLocationLng;

	private Button drive_accept;
	private Button drive_deny;
	private TextView drive_accept_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drive_accept);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		Intent intent = getIntent();
		markerLocationLat = intent.getFloatExtra("drive_lat", 0.0f);
		markerLocationLng = intent.getFloatExtra("drive_lng", 0.0f);

		drive_accept = (Button) findViewById(R.id.drive_accept);
		drive_deny = (Button) findViewById(R.id.drive_deny);
		drive_accept_text = (TextView) findViewById(R.id.drive_accept_text);

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

			drive_accept_text.setText(country + "\n" + state + "\n" + city + "\n" + tanav + " " + knownName + "\n" + postalCode);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		drive_accept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				APIContactor.acceptCurrentDrive(markerLocationLat, markerLocationLng);
				finish();
			}
		});

		drive_deny.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				APIContactor.cancelCurrentDrive();
				finish();
			}
		});
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

		//TODO: Lisa marker sinna kus teda oodatakse....
		// Add a marker in Sydney and move the camera
		LatLng marker = new LatLng(markerLocationLat, markerLocationLng);
		MarkerOptions murker = new MarkerOptions();
		murker.position(marker);
		murker.title("Klient");
		mMap.addMarker(murker);
		mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(15f);
		mMap.animateCamera(zoom);
		/*LatLng sydney = new LatLng(-34, 151);
		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
	}
}
