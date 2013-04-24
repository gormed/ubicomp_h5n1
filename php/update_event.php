<?php
 
/*
 * Following code will update a product information
 * A product is identified by product id (id)
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['id']) && isset($_POST['type']) && isset($_POST['content'])) {
 
    $id = $_POST['id'];
    $type = $_POST['type'];
    $content = $_POST['content'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql update row with matched id
    $result = mysql_query("UPDATE events SET type = '$type', content = '$content' WHERE id = $id");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["message"] = "Event successfully updated.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
    }
} else {
    // required field is missing
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>