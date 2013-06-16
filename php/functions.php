<?php
function device_exists($deviceid) {
	$sql = mysql_query("SELECT *FROM devices WHERE deviceid = '$deviceid'") or die (mysql_error());
	if (mysql_num_rows($sql) > 0) {
		return true;
	}else{
		return false;
	}
}
function get_device_id($deviceid) {
    $sql = mysql_query("SELECT *FROM devices WHERE deviceid = '$deviceid'") or die (mysql_error());
        if (mysql_num_rows($sql) > 0) {
            $sql = mysql_fetch_array($sql);
            return $sql['id'];
        }
}
?>