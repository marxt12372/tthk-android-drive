<?php

define('IN_API', '1');
define('API', 'cronUpdater');

require 'inc/config.php';

$mysqli = new mysqli($mysql['host'], $mysql['user'], $mysql['pass'], $mysql['base']);

//TODO: Tee midagi eluga.

$mysqli->close();

?>
