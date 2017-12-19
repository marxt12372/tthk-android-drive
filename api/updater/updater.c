#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main()
{
	pid_t pid = fork();
	if(pid == 0)
	{
		while(1)
		{
			//printf("Tere\n");
			system("php /var/www/marxt12372.eu/tthk-android-drive/api/cronUpdater.php");
			sleep(5);
		}
	}
	return 0;
}
