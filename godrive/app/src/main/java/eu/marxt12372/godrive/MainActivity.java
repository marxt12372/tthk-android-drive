package eu.marxt12372.godrive;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{
	static Thread updateThread = new UpdatePuller();
	WebView spinner_webview;
	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = getApplicationContext();

		spinner_webview = (WebView) findViewById(R.id.spinner_webview);
		spinner_webview.setBackgroundColor(Color.TRANSPARENT);
		String html = "<html><head><style>@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }</style></head><body style=\"margin: 0;\">";
		html += "<div style=\"width: 50%; height: 50%; position: absolute; left: calc(25% - 20px); top: calc(25% - 20px); border: 20px solid #f3f3f3;  border-top: 20px solid #3498db; border-radius: 50%; animation: spin 2s linear infinite;\"></div>";
		html += "</body></html>";
		spinner_webview.loadData(html, "text/html", "UTF-8");
		spinner_webview.setFocusableInTouchMode(false);

		//TODO: Tee actionbari menüü, kus saab välja logida

		login();

		GPSThread gpsThread = new GPSThread(getApplicationContext());

		spinner_webview.setVisibility(WebView.VISIBLE);

		while(GPSThread.getLocation() == null)
		{
			Log.i("Main", "Ootame gepsu järgi");
			try {
				Thread.sleep(10000);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		spinner_webview.setVisibility(WebView.INVISIBLE);

		updateThread.start();
	}

	public void login()
	{
		DBHandler dbHandler = new DBHandler(getApplicationContext());
		String username = dbHandler.getLoginInfo(DBHandler.LOGIN_INFO_USERNAME);
		String password = dbHandler.getLoginInfo(DBHandler.LOGIN_INFO_PASSWORD);
		boolean success = APIContactor.attemptLogin(username, password);
		if(success)
		{
			//TODO: Panna ta aktiivseks
		}
		else
		{
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
