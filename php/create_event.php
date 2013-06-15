<?php
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['type']) && isset($_POST['content']) && isset($_POST['receiverid']) && isset($_POST['deviceid'])&& isset($_POST['eventid'])) {
 	$table = "d".$_POST['deviceid'];
	$deviceid = $_POST['deviceid'];
	$eventid = $_POST['eventid'];
	$type = $_POST['type'];
	$content = $_POST['content'];
    $receiverid = $_POST['receiverid'];
 
	// include db connect class
	require_once __DIR__ . '/db_connect.php';
 
	// connecting to db
	$db = new DB_CONNECT();
 
	// mysql inserting a new row
	$result = mysql_query("INSERT INTO  $table(deviceid, receiverid, eventid, type, content) VALUES('$deviceid', '$receiverid' ,$eventid, '$type', '$content')");
	

	// check if row inserted or not
	if ($result) {
		// successfully inserted into database
		$response["message"] = "Event successfully created.";
		$response['deviceid'] = $deviceid;
		$response['eventid'] = $eventid;
		$response['receiverid'] = $receiverid;
		// echoing JSON response
		echo json_encode($response);
	} else {
		// failed to insert row
		$response["message"] = "Oops! An error occurred.";
		$response['deviceid'] = $deviceid;
		$response['eventid'] = $eventid;
		$response['receiverid'] = $receiverid;
		// echoing JSON response
		echo json_encode($response);
	}
} else {
	// required field is missing
	$response["message"] = "Required field(s) is missing";
 
	// echoing JSON response
	echo json_encode($response);
}
?>