<?php

if(!(defined('IN_API') || defined('IN_PAGE')))
{
        die("Dafuuq?");
}

function hashString($string)
{
	require 'config.php';
	return strtoupper(hash($dbPassHash, $dbSalt . $string));
}

function generateAPIKey()
{
	$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	$randstring = '';
	for ($i = 0; $i < 64; $i++)
	{
		$randstring = $randstring . $characters[rand(0, strlen($characters)-1)];
	}
	return $randstring;
}

?>
