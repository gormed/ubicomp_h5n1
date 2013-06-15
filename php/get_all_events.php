<?php
 
/*
 * Following code will list all the events
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';

if (!isset($_GET['deviceid']) || !isset($_GET['receiverid'])) {
        // required field is missing
    $table = "d".$_POST['deviceid'];
    $response["message"] = "Required field(s) missing";
 
    // echoing JSON response
    echo json_encode($response);
    exit();
}


if ($_GET['deviceid'] != "0") {
    $deviceid = $_GET['deviceid'];
    $sql = "SELECT * FROM $table WHERE deviceid = '$deviceid'";
} else if ($_GET['receiverid'] != "0"){
    $receiverid = $_GET['receiverid'];
    $sql = "SELECT * FROM $table WHERE receiverid = '$receiverid'";
} else {
    $sql = "SELECT * FROM $table";
}

// connecting to db
$db = new DB_CONNECT();
 
// get all events from events table
$result = mysql_query($sql) or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // events node
    $response["events"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $event = array();
        $event ["id"] = $row["id"];
        $event ["eventid"] = $row["eventid"];
        $event ["receiverid"] = $row["receiverid"];
        $event ["deviceid"] = $row["deviceid"];
        $event ["type"] = $row["type"];
        $event ["content"] = $row["content"];
        $event ["time"] = $row["time"];
 
        // push single product into final response array
        array_push($response["events"], $event);
    }
    // success
    $response["message"]= "Events successfully displayed.";
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no events found
    $response["message"] = "No events found";
    $response["events"] = array();
    // echo no users JSON
    echo json_encode($response);
}
?>