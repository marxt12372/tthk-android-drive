<?php

define('IN_API', '1');
define('API', 'getDrivers');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

$key = $mysqli->escape_string($_GET['apikey']);
$time = time() - (60*60*24);
$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `apitoken` = '" . $key . "' AND `apilastuse` > '" . $time . "'");
if($query->num_rows == 1)
{
	$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `aktiivne` = '1'");
	while($row = $query->fetch_assoc())
	{
		echo $row['lat'] . "-" . $row['lng'] . "-0;"; //TODO: Viimane number 1 tuleb panna selleks, et kas on tema viimasele sÃidul aktiivne juht
	}
	$mysqli->query("UPDATE " . $mysql['pref'] . "kasutajad SET `apilastuse` = '" . time() . "' WHERE `apitoken` = '" . $key . "'");
}

$mysqli->close();

?>
