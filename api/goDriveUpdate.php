<?php

define('IN_API', '1');
define('API', 'goDriveUpdate');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

$key = $mysqli->escape_string($_GET['apikey']);
$time = time() - (60*60*24);
$query = $mysqli->query("SELECT * FROM " . $mysql['pref'] . "kasutajad WHERE `apitoken` = '" . $key . "' AND `apilastuse` > '" . $time . "'");
if($query->num_rows == 1)
{
	//TODO: Idee on selline: KÃik goDrived requestivad seda lehte iga sekund? viis sekundit?
	//TODO: Otsitakse kas talle on s6itusi, mis ootavad uuendusi. Kui on siis n2ita talle seda.
	//TODO: Teha mingi teine leht, mis haldab s6idu asju
	//TODO: Tee midagi
}

$mysqli->close();

?>
