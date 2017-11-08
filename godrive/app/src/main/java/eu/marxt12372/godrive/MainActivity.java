package eu.marxt12372.godrive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//TODO: Tee actionbari menüü, kus saab välja logida

		login();
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
