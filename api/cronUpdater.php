<?php

define('IN_API', '1');
define('API', 'cronUpdater');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);
global $mysqli;

$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud");
while($row = $query->fetch_assoc())
{
	if($row['staatus'] == 0)
	{
		//TODO: Leia talle s6itja, kes ei ole proovitud s6itjate listis.
		//TODO: Vaata, et s6itjal juba poleks aktiivset s6itu.
		//TODO: Seadista staatus yheks.
		//TODO: MÃÃra viimase uuenduse aeg.
		$proovitudsoitjad = explode(',', $row['driversTryed']);
		$driver = getClosestActiveDriverNotInList($row['alguslat'], $row['alguslng'], $proovitudsoitjad);
		if($driver > 0)
		{
			$mysqli->query("UPDATE " . $mysql['pref'] . "soidud SET `s6idutaja` = '" . $driver . "', `staatus` = '1', `lastUpdate` = '" . time() . "' WHERE `sqlid` = '" . $row['sqlid'] . "'");
		}
		else
		{
			$mysqli->query("UPDATE " . $mysql['pref'] . "soidud SET `staatus` = '998', `lastUpdate` = '" . time() . "' WHERE `sqlid` = '" . $row['sqlid'] . "'");
		}
	}
}

$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad");
while($row = $query->fetch_assoc())
{
	if($row['aktiivne'] == 1)
	{
		if(time() - $row['apilastuse'] > 10)
		{
			$mysqli->query("UPDATE " . $mysql['pref'] . "kasutajad SET `aktiivne` = '0' WHERE `sqlid` = '" . $row['sqlid'] . "'");
		}
	}
}

function getClosestActiveDriverNotInList($lat, $lng, $list)
{
	global $mysqli, $mysql;
	$lastDistance = 10000000;
	$lastDriver = -1;
	$driverQuery = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `aktiivne` = '1'");
	while($row = $driverQuery->fetch_assoc())
	{
		$aktiivseidS6ite = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud WHERE (`kasutaja` = '" . $row['sqlid'] . "' OR `s6idutaja` = '" . $row['sqlid'] . "') AND (`staatus` != '999' AND `staatus` != '998')");
		if($aktiivseidS6ite->num_rows == 0)
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
