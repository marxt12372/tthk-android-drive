<?php

define('IN_API', '1');
define('API', 'orderTaxi');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

$key = $mysqli->escape_string($_GET['apikey']);
$time = time() - (60*60*24);
$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `apitoken` = '" . $key . "' AND `apilastuse` > '" . $time . "' LIMIT 1");
if($query->num_rows == 1)
{
	$andmed = $query->fetch_assoc();
	$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud WHERE `kasutaja` = '" . $andmed['sqlid'] . "' AND `staatus` NOT IN (997,998,999)");
	if($query->num_rows == 0)
	{
		$lat = $mysqli->escape_string($_GET['lat']);
		$lng = $mysqli->escape_string($_GET['lng']);

		$query2 = $mysqli->query("INSERT INTO " . $mysql['pref'] . "soidud (`kasutaja`, `s6idutaja`, `alguslat`, `alguslng`, `lopplat`, `lopplng`, `staatus`, `driversTryed`) VALUES ('" . $andmed['sqlid'] . "', '0', '" . $lat . "', '" . $lng . "', '0.0', '0.0', '0', '')");

		echo 'driver_requested';
	}
}
else
{
	echo 'error';
}

$mysqli->close();

?>
