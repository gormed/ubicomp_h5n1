<?php
require 'functions.php';
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();

if (isset($_POST['deviceid']) && isset($_POST['eventid']) && isset($_POST['receiverid'])) {
	$table = "d" . get_id($_POST['deviceid']);
	$deviceid = $_POST['deviceid'];
	$eventid = $_POST['eventid'];
	$receiverid = $_POST['receiverid'];

	if ($receiverid.length > 0) {
		$sql = "SELECT * FROM $table WHERE eventid = $eventid AND deviceid = '$deviceid' AND receiverid = '$receiverid'";
	} else {
		$sql = "SELECT * FROM $table WHERE eventid = $eventid AND deviceid = '$deviceid'";
	}

	$result = mysql_query($sql) or die(mysql_error());
 
	if (!empty($result)) {
		if (mysql_num_rows($result) > 0) {
 
			$result = mysql_fetch_array($result);
 
			$event = array();
			$event ["id"] = $row["id"];
			$event ["eventid"] = $row["eventid"];
			$event ["receiverid"] = $row["receiverid"];
			$event ["deviceid"] = $row["deviceid"];
			$event ["type"] = $row["type"];
			$event ["content"] = $row["content"];
			$event ["time"] = $row["time"];

			$response["event"] = array();
			array_push($response["event"], $event);
 
			echo json_encode($response);
		} else {
			$response["message"] = "No event found";
			echo json_encode($response);
		}
	} else {
		$response["message"] = "No event found";
 		$response["event"] = array();
		echo json_encode($response);
	}
} else {
	$response["message"] = "Required field(s) missing";
	echo json_encode($response);
}
?>