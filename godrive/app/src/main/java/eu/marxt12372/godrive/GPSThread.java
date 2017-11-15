package eu.marxt12372.godrive;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

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
			_location = _locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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
