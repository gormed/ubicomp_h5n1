<?php
 
/*
 * Following code will get single event details
 * A event is identified by event id (id)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_POST['deviceid']) && isset($_POST['eventid']) && isset($_POST['receiverid'])) {
	$table = "d".$_POST['deviceid'];
	$deviceid = $_POST['deviceid'];
	$eventid = $_POST['eventid'];
	$receiverid = $_POST['receiverid'];

	if ($receiverid.length > 0) {
		$sql = "SELECT * FROM $table WHERE eventid = $eventid AND deviceid = '$deviceid' AND receiverid = '$receiverid'";
	} else {
		$sql = "SELECT * FROM $table WHERE eventid = $eventid AND deviceid = '$deviceid'";
	}

	// get a event from events table
	$result = mysql_query($sql);
 
	if (!empty($result)) {
		// check for empty result
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

			// user node
			$response["event"] = array();
 
			array_push($response["event"], $event);
 
			// echoing JSON response
			echo json_encode($response);
		} else {
			// no event found
			$response["message"] = "No event found";
 
			// echo no users JSON
			echo json_encode($response);
		}
	} else {
		// no event found
		$response["message"] = "No event found";
 		$response["event"] = array();
		// echo no users JSON
		echo json_encode($response);
	}
} else {
	// required field is missing
	$response["message"] = "Required field(s) missing";
 
	// echoing JSON response
	echo json_encode($response);
}
?>