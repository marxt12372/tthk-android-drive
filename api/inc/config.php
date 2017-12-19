<?php

if(!(defined('IN_API') || defined('IN_PAGE')))
{
	die("Dafuuq?");
}

require_once 'functions.php';

$mysql['host'] = 'localhost';
$mysql['user'] = 'drive';
$mysql['pass'] = 'drive';
$mysql['base'] = 'drive';
$mysql['pref'] = 'drive_';

$dbSalt = 'ASj2flR3dB6hfj';
$dbPassHash = "SHA256";

?>
