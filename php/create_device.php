<?php
function get_device_id($deviceid) {
    $sql = mysql_query("SELECT *FROM devices WHERE deviceid = '$deviceid'") or die (mysql_error());
        if (mysql_num_rows($sql) > 0) {
            $sql = mysql_fetch_array($sql);
            return $sql['deviceid'];
        }
}

    $response = array();
    require_once __DIR__ . '/db_connect.php';
    $db = new DB_CONNECT();

if (isset($_POST['deviceid'])) {
    $table = "d".(string)get_device_id($_POST['deviceid']);

    $sql = mysql_query("CREATE TABLE $table(id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), deviceid VARCHAR(256) NOT NULL, receiverid VARCHAR(256) NOT NULL, eventid INT NOT NULL, type VARCHAR(256) NOT NULL, time TIMESTAMP, content VARCHAR(256) NOT NULL)");

    if ($sql) {
        $response["message"] = "Table d" . $deviceid . " created successfully";
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