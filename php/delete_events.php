<?php
require 'functions.php';
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
 
if (isset($_POST['deviceid'])) {
    $table = "d" . get_device_id($_POST['deviceid']);
    $deviceid = $_POST['deviceid'];

    $result = mysql_query("DELETE FROM $table WHERE deviceid = '$deviceid'");
 

    if (mysql_affected_rows() > 0) {
        $response["message"] = "Events successfully deleted";
        echo json_encode($response);
    } else {
        $response["message"] = "No Events found while trying to delete all events";
        echo json_encode($response);
    }
} else {
    $response["message"] = "Required field(s) missing";
    echo json_encode($response);
}
?>