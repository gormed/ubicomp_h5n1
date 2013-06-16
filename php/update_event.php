<?php
require 'functions.php';
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();

if (isset($_POST['deviceid']) && isset($_POST['receiverid']) && isset($_POST['eventid']) && isset($_POST['content'])) {
    $table = "d" . get_device_id($_POST['deviceid']);   
    $deviceid = $_POST['deviceid'];
    $eventid = $_POST['eventid'];
    $content = $_POST['content'];
    $receiverid = $_POST['receiverid'];
 
    $result = mysql_query("UPDATE $table SET content = '$content', receiverid = '$receiverid' WHERE eventid = $eventid AND deviceid = '$deviceid'");
 
    if ($result) {
        $response["message"] = "Event successfully updated.";
        $response['deviceid'] = $deviceid;
        $response['eventid'] = $eventid;
        echo json_encode($response);
    } else {
        $response["message"] = "Oops! An error occurred.";
        $response['deviceid'] = $deviceid;
        $response['eventid'] = $eventid;
        echo json_encode($response);
    }
} else {
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}
?>