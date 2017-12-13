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
	else if($type == 2) //S6it on katkestatud/Mitte vastu v6etud
	{
		$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "soidud WHERE `s6idutaja` = '" . $kasutaja['sqlid'] . "' AND (`staatus` != '999' AND `staatus` != '998')");
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
	//TODO: Uuenda orderite asju. Nt:
	//TODO: Kui sÃit on l6ppenud, katkestatud, s6itja peale v6etud
}

$mysqli->close();

?>
