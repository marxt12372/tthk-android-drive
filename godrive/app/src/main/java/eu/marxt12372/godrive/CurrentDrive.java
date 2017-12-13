package eu.marxt12372.godrive;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CurrentDrive extends AppCompatActivity {

	private Button drive_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_drive);
		//TODO: Pane ekraanile mingi info sellest, kuhu peab jõudma?
		drive_cancel = (Button) findViewById(R.id.drive_cancel);

		drive_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				APIContactor.cancelCurrentDrive();
				finish();
			}
		});
	}

	protected void onDestroy()
	{
		APIContactor.cancelCurrentDrive();
		super.onDestroy();
	}

}
