<?php
include('dbConnection.php');

error_reporting(-1);
ini_set('display_errors','On');


$userName = $_GET["userName"];
$pass = $_GET["password"];

login($userName, $pass);

function login($userName, $pass){
    global $con;
    $query = "Select password from user where user.userName = " . $userName;

    $result = $con->query($query)
            or trigger_error($con->error);

    if($result->num_rows != 0){
        $rows = array();

        while($row = $result->fetch_array(MYSQL_BOTH)){
            $rows[] = $row;
        }

        $success = 0;
        $pass = str_replace("\"", "", $pass);

        if($rows[0]['password'] == $pass){
            $success = 1;
        }	
	
    }
    else{
        $success = 0;
    }		

    $data = [ 'result' => $success];

    echo json_encode($data);

    $con->close();
}
?>