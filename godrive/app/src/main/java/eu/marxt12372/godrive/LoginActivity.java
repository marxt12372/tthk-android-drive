package eu.marxt12372.godrive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity
{
	EditText username;
	EditText password;
	Button login;
	Button noaccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.loginbtn);
		noaccount = (Button) findViewById(R.id.noaccountbtn);

		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String user = username.getText().toString();
				String pass = password.getText().toString();
				boolean success = APIContactor.attemptLogin(user, pass);
				if(success)
				{
					DBHandler dbHandler = new DBHandler(getApplicationContext());
					dbHandler.setLoginInfo(user, pass);
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);
					finish();
				}
				else
				{
					username.setText("");
					password.setText("");
				}
			}
		});

		noaccount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}
}
