package eu.marxt12372.godrive;


import android.content.Intent;
import android.os.HandlerThread;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class APIContactor
{
	public static String apiToken = "";
	private static final String APIUrl = "http://marxt12372.eu/tthk-android-drive/api";
	public static String out;

	public APIContactor()
	{
	}

	public static void pullUpdates()
	{
		if(GPSThread.getLocation() != null) {
			double lat = GPSThread.getLocation().getLatitude();
			double lng = GPSThread.getLocation().getLongitude();
			String uri = APIUrl + "/goDriveUpdate.php?apikey=" + apiToken + "&lat=" + lat + "&lng=" + lng;
			String string = sendRequest(uri);
			if(string.contains("uus_soitja"))
			{
				String[] info = string.split(";");
				Intent intent = new Intent(MainActivity.context, DriveAcceptActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				float drive_lat = Float.parseFloat(info[1]);
				float drive_lng = Float.parseFloat(info[2]);
				intent.putExtra("drive_lat", drive_lat);
				intent.putExtra("drive_lng", drive_lng);
				MainActivity.context.startActivity(intent);
				Log.i("APIContactor", "Uus sõitja määratud. " + string);
			}
		}
	}

	public static void cancelCurrentDrive()
	{
		String uri = APIUrl + "/orderUpdate.php?apikey=" + apiToken + "&type=2";
		String string = sendRequest(uri);
	}

	public static void endCurrentDrive()
	{
		String uri = APIUrl + "/orderUpdate.php?apikey=" + apiToken + "&type=3";
		String string = sendRequest(uri);
	}

	public static void acceptCurrentDrive(float lat, float lng)
	{
		String uri = APIUrl + "/orderUpdate.php?apikey=" + apiToken + "&type=1";
		String string = sendRequest(uri);
		if(string.equals("success"))
		{
			Intent intent = new Intent(MainActivity.context, CurrentDrive.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("drive_lat", lat);
			intent.putExtra("drive_lng", lng);
			MainActivity.context.startActivity(intent);
		}
		else if(string.equals("fail"))
		{
		}
	}

	public static boolean attemptLogin(String username, String password)
	{
		String uri = APIUrl + "/login.php?user=" + username + "&pass=" + password;
		String string = sendRequest(uri);
		if(!string.equals("false"))
		{
			apiToken = string;

			return true;
		}
		return false;
	}

	public static void createAccount(String username, String password, String email, String realname)
	{
		String uri = APIUrl + "/register.php?user=" + username + "&pass=" + password + "&email=" + email + "&realname=" + realname;
		sendRequest(uri);
	}

	public static String getRealName()
	{
		String uri = APIUrl + "/getUserInfo.php?apikey=" + apiToken;
		String string = sendRequest(uri);
		String[] data = string.split(";");
		return data[0];
	}

	public static String getPictureUrl()
	{
		String uri = APIUrl + "/getUserInfo.php?apikey=" + apiToken;
		String string = sendRequest(uri);
		String[] data = string.split(";");
		return data[2];
	}

	public static String sendRequest(String uri)
	{
		out = "wait";
		Thread networkThread = new NetworkThread(uri);
		networkThread.start();

		boolean wait = true;

		while(wait)
		{
			if(!out.equals("wait")) {
				wait = false;
			}
		}
		return out;
	}
}
