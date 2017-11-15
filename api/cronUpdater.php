<?php

define('IN_API', '1');
define('API', 'cronUpdater');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud");
while($row = $query->fetch_assoc())
{
	if($row['staatus'] == 0)
	{
		//TODO: Leia talle s6itja, kes ei ole proovitud s6itjate listis.
		//TODO: Vaata, et s6itjal juba poleks aktiivset s6itu.
		//TODO: Seadista staatus yheks.
		//TODO: MÃÃra viimase uuenduse aeg.
	}
}

$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad");
while($row = $query->fetch_assoc())
{
	if($row['aktiivne'] == 1)
	{
		if(time() - $row['lastUpdate'] > 10)
		{
			$mysqli->query("UPDATE " . $mysql['pref'] . "kasutajad SET `aktiivne` = '0', `lastUpdate` = '" . time() . "' WHERE `sqlid` = '" . $row['sqlid'] . "'");
		}
	}
}

function getClosestDriverNotInList($lat, $lng, $list)
{
	$lastDistance = 10000000;
	$lastDriver = -1;
	$driverQuery = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `aktiivne` = '1'");
	while($row = $driverQuery->fetch_assoc())
	{
		if(!in_array($row['sqlid'], $list))
		{
			$distance = getDistanceBetweenPoints($lat, $lng, $row['lat'], $row['lng']);
			if($distance < $lastDistance)
			{
				$lastDistance = $distance;
				$lastDriver = $row['sqlid'];
			}
		}
	}
	return $lastDriver;
}

function getDistanceBetweenPoints($lat1, $lng1, $lat2, $lng2)
{
	$dlat = deg2rad($lat2 - $lat1);
	$dlng = deg2rad($lng2 - $lng1);
	$a = sin($dlat/2) * sin($dlat/2) + sin($dlng/2) * sin($dlng/2) * cos(deg2rad($lat1)) * cos(deg2rad($lat2));
	$c = 2 * atan2(sqrt($a), sqrt(1 - $a));
	return 6371 * $c;
}

$mysqli->close();

?>
