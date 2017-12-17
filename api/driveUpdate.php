<?php

define('IN_API', '1');
define('API', 'driveUpdate');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

$key = $mysqli->escape_string($_GET['apikey']);
$time = time() - (60*60*24);
$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `apitoken` = '" . $key . "' AND `apilastuse` > '" . $time . "'");
if($query->num_rows == 1)
{
	$kasutaja = $query->fetch_assoc();
	$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud WHERE `kasutaja` = '" . $kasutaja['sqlid'] . "' AND `staatus` NOT IN (997,998,999)");
	if($query->num_rows > 0)
	{
		$andmed = $query->fetch_assoc();
		if($andmed['staatus'] == 3)
		{
			echo 'driver_found';
		}
		else if($andmed['staatus'] == 997)
		{
			echo 'driver_cancel';
			$mysqli->query("UPDATE " . $mysql['pref'] . "soidud SET `staatus` = '998' WHERE `sqlid` = '" . $andmed['sqlid'] . "'");
		}
	}
	//TODO: Kui ta on taksojuhi requestind, siis vaata, kas juht on leitud. Kui on, siis ytle talle seda.
}

$mysqli->close();

?>
