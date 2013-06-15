<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();

if (isset($_POST['deviceid'])) {
	$deviceid = $_POST['deviceid'];
	$result = mysql_query("INSERT INTO devices(deviceid) VALUES('$deviceid')");
	
	if ($result) {
		$response["message"] = "Device successfully registered.";
		$response['deviceid'] = $deviceid;
		echo json_encode($response);
	} else {
		$response["message"] = "An error occurred.";
		$response['deviceid'] = $deviceid;
		echo json_encode($response);
	}
} else {
	$response["message"] = "Required field(s) is missing";
	echo json_encode($response);
}
?>