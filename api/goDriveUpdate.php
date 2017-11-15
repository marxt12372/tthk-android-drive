<?php

define('IN_API', '1');
define('API', 'goDriveUpdate');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

$key = $mysqli->escape_string($_GET['apikey']);
$time = time() - (60*60*24);
$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `apitoken` = '" . $key . "' AND `apilastuse` > '" . $time . "' LIMIT 1");
$kasutajaData = $query->fetch_assoc();
if($query->num_rows == 1)
{
	$lat = $mysqli->escape_string($_GET['lat']);
	$lng = $mysqli->escape_string($_GET['lng']);

	$mysqli->query("UPDATE " . $mysql['pref'] . "kasutajad SET `aktiivne` = '1', `lat` = '" . $lat . "', `lng` = '" . $lng . "' WHERE `apitoken` = '" . $key . "' AND `apilastuse` > '" . $time . "'");

	$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud WHERE `s6idutaja` = '" . $kasutajaData['sqlid'] . "' AND `staatus` = '1'");
	while($row = $query->fetch_assoc())
	{
		echo 'uus_soitja;' . $row['alguslat'] . ';' . $row['alguslng'];
		$mysqli->query("UPDATE " . $mysql['pref'] . "soidud SET `staatus` = '2' WHERE sqlid = '" . $row['sqlid'] . "'");
		break;
	}
}

$mysqli->close();

?>
