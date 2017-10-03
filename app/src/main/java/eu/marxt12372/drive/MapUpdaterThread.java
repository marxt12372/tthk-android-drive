package eu.marxt12372.drive;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapUpdaterThread extends Thread
{
    private GoogleMap _map;

    public MapUpdaterThread(GoogleMap map)
    {
        _map = map;
    }

    public void run()
    {
		while(true)
		{
			MarkerOptions marker = new MarkerOptions();
			marker.position(new LatLng(0,0));
			marker.title("abcd");
			_map.addMarker(marker);
			//TODO: Noh, tee midagi siin siis?
			//TODO: http://marxt12372.eu/tthk-android-drive/api/
			try
			{
				Thread.sleep(5000);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
    }
}
