<?php
function device_exists($deviceid) {
	$sql = mysql_query("SELECT *FROM devices WHERE deviceid = '$deviceid'") or die (mysql_error());
	if (mysql_num_rows($sql) > 0) {
		return true;
	}else{
		return false;
	}
}

$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();


	if (isset($_POST['deviceid'])) {
		$deviceid = $_POST['deviceid'];
		
		if(device_exists($deviceid) == true){
			$response["message"] = "Device is already registered.";
			echo json_encode($response);
		}else{
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
		}
	} else {
			$response["message"] = "Required field(s) is missing";
			echo json_encode($response);
		}
?>