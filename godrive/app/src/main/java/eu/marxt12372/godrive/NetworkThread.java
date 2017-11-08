package eu.marxt12372.godrive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkThread extends Thread
{
	private String _uri;

	public NetworkThread(String uri)
	{
		_uri = uri;
	}

	public void run()
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			URL url = new URL(_uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String nextLine = "";
			while ((nextLine = reader.readLine()) != null) {
				sb.append(nextLine);
			}
		}
		catch(MalformedURLException e) { e.printStackTrace(); }
		catch(IOException e) { e.printStackTrace(); }

		eu.marxt12372.godrive.APIContactor.out = sb.toString();
	}
}
