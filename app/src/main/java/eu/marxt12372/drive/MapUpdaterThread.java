package eu.marxt12372.drive;

import com.google.android.gms.maps.GoogleMap;

public class MapUpdaterThread extends Thread
{
    private GoogleMap _map;

    public MapUpdaterThread(GoogleMap map)
    {
        _map = map;
    }

    public void run()
    {
        //Update the map
    }
}
