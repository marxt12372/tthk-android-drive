package eu.marxt12372.godrive;


import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;


public class GPSThread implements LocationListener
{
	LocationManager _locationManager;
	private static Location _location;
	public static boolean _providerStatus;

	public GPSThread(Context context)
	{
		_locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		try {
			_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1500, 2.5f, this);

			List<String> providers = _locationManager.getProviders(true);
			Location bestLocation = null;
			for (String provider : providers) {
				Location l = _locationManager.getLastKnownLocation(provider);
				if (l == null) {
					continue;
				}
				if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
					bestLocation = l;
				}
			}
			_location = bestLocation;
		}
		catch(SecurityException e)
		{
			//TODO: GPS ei ole lubatud. Tee midagi.
			Log.i("GPS Error", "GPS pole lubatud");
			e.printStackTrace();
		}
		Log.i("GPSThread", "GPSThread is now started");
	}

	public void onStatusChanged(String str, int num, Bundle bundle)
	{
	}

	public void onProviderEnabled(String str)
	{
		_providerStatus = true;
	}

	public void onProviderDisabled(String str)
	{
		_providerStatus = false;
	}

	public void onLocationChanged(Location location)
	{
		Log.i("LocationUpdater", "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
		_location = location;
	}

	public static Location getLocation()
	{
		return _location;
	}
}
