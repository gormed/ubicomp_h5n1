<?php
require 'functions.php';
    $response = array();
    require_once __DIR__ . '/db_connect.php';
    $db = new DB_CONNECT();

if (isset($_POST['deviceid'])) {
    $deviceid = $_POST['deviceid'];
    $table = "d" . get_device_id($_POST['deviceid']);

    $sql = mysql_query("CREATE TABLE $table(id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), deviceid VARCHAR(128) NOT NULL, receiverid VARCHAR(128) NOT NULL, eventid INT NOT NULL, type VARCHAR(128) NOT NULL, time TIMESTAMP, content VARCHAR(256) NOT NULL)");

    if ($sql) {
        $response["message"] = "Table d" . get_device_id($_POST['deviceid']) . " created successfully";
        echo json_encode($response);
    } else {
        $response["message"] = "Error creating table: " . mysql_error();
        echo json_encode($response);
    }
}else{
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}
?>