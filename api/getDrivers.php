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
	$kasutaja = $query->fetch_assoc();
	$andmed;
	$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud WHERE `kasutaja` = '" . $kasutaja['sqlid'] . "' AND `staatus` NOT IN (0,1,2,998,999)");
	if($query->num_rows > 0)
	{
		$andmed = $query->fetch_assoc();
	}


	$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `aktiivne` = '1'");
	while($row = $query->fetch_assoc())
	{
		if(isset($andmed) && !empty($andmed))
		{
			if($row['sqlid'] == $andmed['s6idutaja'])
			{
				echo $row['lat'] . "-" . $row['lng'] . "-1;";
			}
			else
			{
				echo $row['lat'] . "-" . $row['lng'] . "-0;";
			}
		}
		else
		{
			echo $row['lat'] . "-" . $row['lng'] . "-0;";
		}
	}
	$mysqli->query("UPDATE " . $mysql['pref'] . "kasutajad SET `apilastuse` = '" . time() . "' WHERE `apitoken` = '" . $key . "'");
}

$mysqli->close();

?>
