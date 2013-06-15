<?php
 
/*
 * Following code will update a product information
 * A product is identified by product id (id)
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['deviceid']) && isset($_POST['receiverid']) && isset($_POST['eventid']) && isset($_POST['content'])) {
 
    $deviceid = $_POST['deviceid'];
    $eventid = $_POST['eventid'];
    $content = $_POST['content'];
    $receiverid = $_POST['receiverid'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql update row with matched id
    $result = mysql_query("UPDATE events SET content = '$content', receiverid = '$receiverid' WHERE eventid = $eventid AND deviceid = '$deviceid'");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["message"] = "Event successfully updated.";
        $response['deviceid'] = $deviceid;
        $response['eventid'] = $eventid;
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to update row
        $response["message"] = "Oops! An error occurred.";
        $response['deviceid'] = $deviceid;
        $response['eventid'] = $eventid;
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>