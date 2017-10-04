package eu.marxt12372.drive;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIContactor
{
	private static String apiToken = "";
	private static final String APIUrl = "http://marxt12372.eu/tthk-android-drive";

	public APIContactor()
	{
	}

	public static boolean attemptLogin(String username, String password)
	{
		String string = sendRequest(APIUrl + "/login.php?user=" + username + "&pass=" + password);
		if(string != "false")
		{
			apiToken = string;
			return true;
		}
		return false;
	}

	public static String sendRequest(String uri)
	{
		StringBuilder sb = new StringBuilder(); //TODO: Os:NetworkOnMainThreadException

		try
		{
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String nextLine = "";
			while ((nextLine = reader.readLine()) != null) {
				sb.append(nextLine);
			}
		}
		catch(MalformedURLException e) { e.printStackTrace(); }
		catch(IOException e) { e.printStackTrace(); }

		return sb.toString();
	}
}
