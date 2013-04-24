<?php
 
/*
 * Following code will list all the events
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all events from events table
$result = mysql_query("SELECT *FROM events") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // events node
    $response["events"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $event = array();
        $event ["id"] = $row["id"];
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
 
    // echo no users JSON
    echo json_encode($response);
}
?>