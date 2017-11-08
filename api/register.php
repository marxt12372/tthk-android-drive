<?php

define('IN_API', '1');
define('API', 'register');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

$user = $mysqli->escape_string($_GET['user']);
$pass = hashString($mysqli->escape_string($_GET['pass']));
$email = $mysqli->escape_string($_GET['email']);
$name = $mysqli->escape_string($_GET['realname']);

$query = $mysqli->query("INSERT INTO " . $mysql['pref'] . "kasutajad (`kasutajanimi`, `parool`, `email`, `realname`, `aktiivne`) VALUES ('" . $user . "', '" . $pass . "', '" . $email . "', '" . $name . "', '0')");
$row = $query->fetch_assoc();

$apiKey = generateAPIKey();
echo $apiKey;

$mysqli->close();

?>
