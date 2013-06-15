<?php
 
/*
 * Following code will delete a product from table
 * A product is identified by product id (id)
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['deviceid'])) {
    $table = "d".$_POST['deviceid'];
    $deviceid = $_POST['deviceid'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql update row with matched id
    $result = mysql_query("DELETE FROM $table WHERE deviceid = '$deviceid'");
 
    // check if row deleted or not
    if (mysql_affected_rows() > 0) {
        // successfully updated
        $response["message"] = "Events successfully deleted";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // no product found
        $response["message"] = "No Event found";
 
        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["message"] = "Required field(s) missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>