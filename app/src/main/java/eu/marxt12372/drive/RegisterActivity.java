package eu.marxt12372.drive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

	Button registerbtn;
	Button haveAccountbtn;

	EditText username;
	EditText password;
	EditText password2;
	EditText email;
	EditText realname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		registerbtn = (Button) findViewById(R.id.registerbtn);
		haveAccountbtn = (Button) findViewById(R.id.haveaccountbtn);

		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		password2 = (EditText) findViewById(R.id.password2);
		email = (EditText) findViewById(R.id.email);
		realname = (EditText) findViewById(R.id.name);

		registerbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(password.getText().toString().equals(password2.getText().toString()))
				{
					String user = username.getText().toString();
					String pass = password.getText().toString();
					String mail = email.getText().toString();
					String name = realname.getText().toString();
					DBHandler dbHandler = new DBHandler(getApplicationContext());
					dbHandler.setLoginInfo(user, pass);
					APIContactor.createAccount(user, pass, mail, name);
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);
					finish();
				}
				else
				{
					TextView errormsg = (TextView) findViewById(R.id.errormsg);
					errormsg.setText(getString(R.string.passwdmatch));
				}
			}
		});

		haveAccountbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
