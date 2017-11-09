package eu.marxt12372.godrive;


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
		double lat = GPSThread.getLocation().getLatitude();
		double lng = GPSThread.getLocation().getLongitude();

		String uri = APIUrl + "/goDriveUpdate.php?apikey=" + apiToken + "&lat=" + lat + "&lng=" + lng;
		String string = sendRequest(uri);
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
