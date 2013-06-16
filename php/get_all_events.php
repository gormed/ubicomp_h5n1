<?php
require 'functions.php';
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();


if (!isset($_GET['deviceid']) || !isset($_GET['receiverid'])) {
    $response["message"] = "Required field(s) missing";
    echo json_encode($response);
    exit();
}

if ($_GET['deviceid'] != "0") {
    $table = "d" . get_device_id($_GET['deviceid']);
    $deviceid = $_GET['deviceid'];
    $sql = "SELECT * FROM $table WHERE deviceid = '$deviceid'";
} else if ($_GET['receiverid'] != "0"){
    $table = "d" . get_device_id($_GET['receiverid']);
    $receiverid = $_GET['receiverid'];
    $sql = "SELECT * FROM $table WHERE receiverid = '$receiverid'";
} 

$result = mysql_query($sql) or die(mysql_error());
 

if (mysql_num_rows($result) > 0) {
    $response[$table] = array();
 
    while ($row = mysql_fetch_array($result)) {
        $event = array();
        $event ["id"] = $row["id"];
        $event ["eventid"] = $row["eventid"];
        $event ["receiverid"] = $row["receiverid"];
        $event ["deviceid"] = $row["deviceid"];
        $event ["type"] = $row["type"];
        $event ["content"] = $row["content"];
        $event ["time"] = $row["time"];

        array_push($response[$table], $event);
    }

    $response["message"]= "Events successfully displayed.";

    echo json_encode($response);
} else {

    $response["message"] = "No events found";
    $response[$table] = array();

    echo json_encode($response);
}
?>