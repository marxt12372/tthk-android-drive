<?php

define('IN_API', '1');
define('API', 'orderUpdate');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

$key = $mysqli->escape_string($_GET['apikey']);
$time = time() - (60*60*24);
$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `apitoken` = '" . $key . "' AND `apilastuse` > '" . $time . "'");
if($query->num_rows == 1)
{
	//TODO: Uuenda orderite asju. Nt:
	//TODO: Kui sÃit on l6ppenud, katkestatud, s6itja peale v6etud
}

$mysqli->close();

?>
