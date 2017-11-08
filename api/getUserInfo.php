<?php

define('IN_API', '1');
define('API', 'getUserInfo');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

$key = $mysqli->escape_string($_GET['apikey']);
$time = time() - (60*60*24);
$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `apitoken` = '" . $key . "' AND `apilastuse` > '" . $time . "'");
if($query->num_rows == 1)
{
	$row = $query->fetch_assoc();
	echo $row['realname'] . ';' . $row['email'] . ';' . $row['picurl'];
	$mysqli->query("UPDATE " . $mysql['pref'] . "kasutajad SET `apilastuse` = '" . time() . "' WHERE `apitoken` = '" . $key . "'");
}

$mysqli->close();

?>
