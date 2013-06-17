<?php
function device_exists($deviceid) {
	$sql = mysql_query("SELECT *FROM devices WHERE deviceid = '$deviceid'") or die (mysql_error());
	if (mysql_num_rows($sql) > 0) {
		return true;
	}else{
		return false;
	}
}
function get_id($deviceid) {
    $sql = mysql_query("SELECT *FROM devices WHERE deviceid = '$deviceid'") or die (mysql_error());
        if (mysql_num_rows($sql) > 0) {
            $sql = mysql_fetch_array($sql);
            return $sql['id'];
        }
}
function get_events($deviceid) {
	$table = "d" . get_id($deviceid);
    $sql = "SELECT *FROM $table WHERE deviceid = '$deviceid'";
    $result = mysql_query($sql) or die(mysql_error());

	if (mysql_num_rows($result) > 0) {
   		$response['events'] = array();
 
   		while ($row = mysql_fetch_array($result)) {
       		$event = array();
        	$event ["id"] = $row["id"];
        	$event ["eventid"] = $row["eventid"];
        	$event ["receiverid"] = $row["receiverid"];
        	$event ["deviceid"] = $row["deviceid"];
        	$event ["type"] = $row["type"];
        	$event ["content"] = $row["content"];
        	$event ["time"] = $row["time"];

        	array_push($response['events'], $event);
    	}

    $response["message"]= "Devices successfully displayed.";
    echo json_encode($response);

    }else{
    $response["message"] = "No events found";
    $response['events'] = array();
    echo json_encode($response);
	}
}
?>