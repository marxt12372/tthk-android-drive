<?php

define('IN_API', '1');
define('API', 'login');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

$user = $mysqli->escape_string($_GET['user']);
$pass = hashString($mysqli->escape_string($_GET['pass']));

$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `kasutajanimi` = '" . $user . "' LIMIT 1");
$row = $query->fetch_assoc();
if($row['parool'] == $pass)
{
	$apiKey = generateAPIKey();
	echo $apiKey;
	$mysqli->query("UPDATE " . $mysql['pref'] . "kasutajad SET `apitoken` = '" . $apiKey . "', `apilastuse` = '" . time() . "' WHERE `sqlid` = '" . $row['sqlid'] . "'");
}
else
{
	echo 'false';
}

$mysqli->close();

?>
