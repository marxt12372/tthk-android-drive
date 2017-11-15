package eu.marxt12372.godrive;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import eu.marxt12372.godrive.R;

public class DriveAcceptActivity extends FragmentActivity implements OnMapReadyCallback {

	private GoogleMap mMap;
	private float markerLocationLat;
	private float markerLocationLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drive_accept);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		Intent intent = getIntent();
		markerLocationLat = intent.getFloatExtra("drive_lat", 0.0f);
		markerLocationLng = intent.getFloatExtra("drive_lng", 0.0f);
	}


	/**
	 * Manipulates the map once available.
	 * This callback is triggered when the map is ready to be used.
	 * This is where we can add markers or lines, add listeners or move the camera. In this case,
	 * we just add a marker near Sydney, Australia.
	 * If Google Play services is not installed on the device, the user will be prompted to install
	 * it inside the SupportMapFragment. This method will only be triggered once the user has
	 * installed Google Play services and returned to the app.
	 */
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
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(17f);
		mMap.animateCamera(zoom);
		/*LatLng sydney = new LatLng(-34, 151);
		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
	}
}
