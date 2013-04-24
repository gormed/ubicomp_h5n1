<?php
 
/*
 * Following code will get single event details
 * A event is identified by event id (id)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET["id"])) {
    $id = $_GET['id'];
 
    // get a event from events table
    $result = mysql_query("SELECT *FROM events WHERE id = $id");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $event = array();
            $event["id"] = $result["id"];
            $event["type"] = $result["type"];
            $event["content"] = $result["content"];
            $event["time"] = $result["time"];

            // user node
            $response["event"] = array();
 
            array_push($response["event"], $event);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no event found
            $response["message"] = "No event found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no event found
        $response["message"] = "No event found";
 
        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>