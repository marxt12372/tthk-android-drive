package eu.marxt12372.drive;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

	private GoogleMap mMap;
	ImageView nav_header_picture;
	TextView nav_header_text;

	Button findgps;
	Button orderTaxi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		View header=navigationView.getHeaderView(0);
		nav_header_picture = (ImageView) header.findViewById(R.id.nav_header_picture);
		nav_header_text = (TextView) header.findViewById(R.id.nav_header_text);

		findgps = (Button) findViewById(R.id.findgpsbutton);
		orderTaxi = (Button) findViewById(R.id.tellibtn);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		login();

		findgps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			try {
				LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

				LocationListener locationListener = new LocationListener() {
					public void onLocationChanged(Location location) {
						setCameraPosition(location, 17f);
					}

					public void onStatusChanged(String provider, int status, Bundle extras) {}
					public void onProviderEnabled(String provider) {}
					public void onProviderDisabled(String provider) {}
				};

				//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
				Criteria crit = new Criteria();
				crit.setAccuracy(Criteria.ACCURACY_FINE);
				locationManager.requestSingleUpdate(crit, locationListener, null);

			}
			catch (SecurityException e) {
				e.printStackTrace();
			}
			}
		});

		orderTaxi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			double mylat = mMap.getCameraPosition().target.latitude;
			double mylng = mMap.getCameraPosition().target.longitude;
			Log.i("ORDER_BTN", "Lat: " + mylat + ", Lng: " + mylng);
			}
		});
	}

	public void setCameraPosition(Location loc, float zoomlvl)
	{
		if(loc != null)
		{
			CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(), loc.getLongitude()));
			CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomlvl);

			mMap.moveCamera(center);
			mMap.animateCamera(zoom);
		}
	}

	public void login()
	{
		DBHandler dbHandler = new DBHandler(getApplicationContext());
		String username = dbHandler.getLoginInfo(DBHandler.LOGIN_INFO_USERNAME);
		String password = dbHandler.getLoginInfo(DBHandler.LOGIN_INFO_PASSWORD);
		boolean success = APIContactor.attemptLogin(username, password);
		if(success)
		{
			nav_header_text.setText(APIContactor.getRealName());
			new DownloadImageTask(nav_header_picture).execute(APIContactor.getPictureUrl());
		}
		else
		{
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_myaccount)
		{
			Intent intent = new Intent(getApplicationContext(), MyAccount.class);
			startActivity(intent);
		}
		else if (id == R.id.nav_mydrives)
		{
			Intent intent = new Intent(getApplicationContext(), MyDrives.class);
			startActivity(intent);
		}
		else if (id == R.id.nav_logout)
		{
			DBHandler dbHandler = new DBHandler(getApplicationContext());
			dbHandler.setLoginInfo("NULL", "NULL");
			login();
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;
		LatLng tallinn = new LatLng(59.437041, 24.753463); //Tallinn
		//mMap.addMarker(new MarkerOptions().position(pos).title("nimi"));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tallinn, 13f));
		Thread updaterThread = new MapUpdaterThread(mMap);
		updaterThread.start();
	}
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;

	public DownloadImageTask(ImageView bmImage) {
		this.bmImage = bmImage;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		bmImage.setImageBitmap(result);
	}
}
