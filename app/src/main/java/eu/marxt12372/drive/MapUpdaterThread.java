package eu.marxt12372.drive;

import android.content.Context;
import android.os.Handler;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
		while(true)
		{
			mainHandler.post(new Runnable(){public void run(){
				_map.clear();

				MarkerOptions marker = new MarkerOptions();
				marker.position(new LatLng(0,0));
				marker.title("abcd");
				_map.addMarker(marker);
				//Marker add teha siin.
				//Network koodi siin jooksutada ei saa tõenäoliselt.
			}});

			//TODO: Noh, tee midagi siin siis?
			//TODO: http://marxt12372.eu/tthk-android-drive/api/
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
    }
}
