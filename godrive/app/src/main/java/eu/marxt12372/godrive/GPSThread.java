package eu.marxt12372.godrive;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSThread implements LocationListener
{
	LocationManager _locationManager;
	private static Location _location;

	public GPSThread(Context context)
	{
		_locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		try {
			_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		}
		catch(SecurityException e)
		{
			//TODO:GPS ei ole lubatud. Tee midagi.
			e.printStackTrace();
		}
	}

	public void onStatusChanged(String str, int num, Bundle bundle)
	{
	}

	public void onProviderEnabled(String str)
	{
	}

	public void onProviderDisabled(String str)
	{
	}

	public void onLocationChanged(Location location)
	{
		_location = location;
	}

	public static Location getLocation()
	{
		return _location;
	}
}
