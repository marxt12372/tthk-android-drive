package eu.marxt12372.godrive;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CurrentDrive extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_drive);
		//TODO: Pane ekraanile mingi info sellest, kuhu peab jõudma?
	}

	protected void onDestroy()
	{
		APIContactor.cancelCurrentDrive();
		super.onDestroy();
	}

}
