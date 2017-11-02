package eu.marxt12372.drive;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class MapUpdaterThread extends Thread
{
	private Handler mainHandler;
	private GoogleMap _map;

    public MapUpdaterThread(GoogleMap map)
    {
	    mainHandler = new Handler();
	    _map = map;
    }

    public void run()
    {
    	final Random random = new Random();
		while(true)
		{
			mainHandler.post(new Runnable(){public void run(){
				_map.clear();

				String driverlist = APIContactor.getDrivers();
				if(driverlist.contains(";"))
				{
					String[] drivers = driverlist.split(";");
					for (String driver : drivers)
					{
						if (driver.contains("-"))
						{
							String[] data = driver.split("-");
							MarkerOptions marker = new MarkerOptions();
							marker.position(new LatLng(Double.parseDouble(data[0]), Double.parseDouble(data[1])));
							marker.title("Takso");
							if(data[2].equals("1"))
							{
								marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.mytaxi));
							}
							else
							{
								marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.taxi));
							}
							marker.anchor(0.5f, 0.5f);
							marker.rotation(random.nextInt(359));
							_map.addMarker(marker);
						}
					}
				}
			}});

			try
			{
				Thread.sleep(10000);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
    }
}
