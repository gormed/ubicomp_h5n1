<?php
require 'functions.php';
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();

if (isset($_POST['deviceid']) && isset($_POST['eventid'])) {
    $table = "d" . get_id($_POST['deviceid']);
    $deviceid = $_POST['deviceid'];
    $eventid = $_POST['eventid'];
 
    $result = mysql_query("DELETE FROM  $table WHERE eventid = $eventid AND deviceid = '$deviceid'");
 
    if (mysql_affected_rows() > 0) {
        $response["message"] = "Event successfully deleted";
        echo json_encode($response);
    } else {
        $response["message"] = "No Event found";
        echo json_encode($response);
    }
} else {
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}
?>