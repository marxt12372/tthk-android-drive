package eu.marxt12372.drive;


public class UpdatePuller extends Thread
{
	public void run()
	{
		while(true)
		{
			APIContactor.pullUpdates();
			try { this.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
}