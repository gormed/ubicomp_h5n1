<?php
require 'functions.php';
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
 
if (isset($_POST['type']) && isset($_POST['content']) && isset($_POST['receiverid']) && isset($_POST['deviceid'])&& isset($_POST['eventid'])) {
 	$table = "d" . get_id($_POST['deviceid']);
	$deviceid = $_POST['deviceid'];
	$eventid = $_POST['eventid'];
	$type = $_POST['type'];
	$content = $_POST['content'];
    $receiverid = $_POST['receiverid'];

	$result = mysql_query("INSERT INTO  $table(deviceid, receiverid, eventid, type, content) VALUES('$deviceid', '$receiverid' ,$eventid, '$type', '$content')");
	
	if ($result) {
		$response["message"] = "Event successfully created.";
		$response['deviceid'] = $deviceid;
		$response['eventid'] = $eventid;
		$response['receiverid'] = $receiverid;
		echo json_encode($response);
	} else {
		$response["message"] = "An error occurred, while creating an Event.";
		$response['deviceid'] = $deviceid;
		$response['eventid'] = $eventid;
		$response['receiverid'] = $receiverid;
		echo json_encode($response);
	}
} else {
	$response["message"] = "Required field(s) is missing";
	echo json_encode($response);
}
?>