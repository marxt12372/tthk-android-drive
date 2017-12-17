<?php

define('IN_API', '1');
define('API', 'orderUpdate');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

$key = $mysqli->escape_string($_GET['apikey']);
$type = $mysqli->escape_string($_GET['type']);
$time = time() - (60*60*24);
$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `apitoken` = '" . $key . "' AND `apilastuse` > '" . $time . "'");
if($query->num_rows == 1)
{
	$kasutaja = $query->fetch_assoc();
	if($type == 1) //S6it on vastu v6etud
	{
		$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud WHERE `s6idutaja` = '" . $kasutaja['sqlid'] . "' AND `staatus` = '2'");
		if($query->num_rows > 0)
		{
			$s6it = $query->fetch_assoc();
			$mysqli->query("UPDATE " . $mysql['pref'] . "soidud SET `staatus` = '3', `lastUpdate` = '" . time() . "' WHERE `sqlid` = '" . $s6it['sqlid'] . "'");
			echo 'success';
		}
		else
		{
			echo 'fail';
		}
	}
	else if($type == 2) //S6it on katkestatud
	{
		$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud WHERE `s6idutaja` = '" . $kasutaja['sqlid'] . "' AND `staatus` NOT IN (997,998,999)");
		if($query->num_rows > 0)
		{
			$s6it = $query->fetch_assoc();
			$list = "";
			if(empty($s6it['driversTryed']))
			{
				$list = $kasutaja['sqlid'];
			}
			else
			{
				$list = $s6it['driversTryed'] . "," . $kasutaja['sqlid'];
			}
			$mysqli->query("UPDATE " . $mysql['pref'] . "soidud SET `staatus` = '0', `driversTryed` = '" . $list . "' WHERE `sqlid` = '" . $s6it['sqlid'] . "'");
		}
	}
	else if($type == 3) //S6it on l6ppenud
	{
		$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud WHERE `s6idutaja` = '" . $kasutaja['sqlid'] . "' AND `staatus` NOT IN (997,998,999)");
		if($query->num_rows > 0)
		{
			$s6it = $query->fetch_assoc();
			$mysqli->query("UPDATE " . $mysql['pref'] . "soidud SET `staatus` = '999' WHERE `sqlid` = '" . $s6it['sqlid'] . "'");
		}
	}
	else if($type == 4) //Klient on peale v6etud
	{
		$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud WHERE `s6idutaja` = '" . $kasutaja['sqlid'] . "' AND `staatus` = '3'");
		if($query->num_rows > 0)
		{
			$s6it = $query->fetch_assoc();
			$mysqli->query("UPDATE " . $mysql['pref'] . "soidud SET `staatus` = '4' WHERE `sqlid` = '" . $s6it['sqlid'] . "'");
		}
	}
}

$mysqli->close();

?>
