<?php
error_reporting(-1);
ini_set('display_errors','On');
$host="mysql.cs.iastate.edu";
$port=3306;
$socket="";
$user="dbu309amc1";
$password="XFsBvb1t";
$dbname="db309amc1";

$con = new mysqli($host, $user, $password, $dbname, $port, $socket)
or die ('Could not connect to the database server' . mysqli_connect_error());

if($con->connect_errno){
echo "<p>encountered an error connecting</p>";
}

$usern = $_GET["userName"];
$pass = $_GET["password"];

$query = "Select * from user where userName = " . $usern;

$result = $con->query($query)
or trigger_error($con->error);

if($result->num_rows != 0){
$data = ['result' => 0];
echo json_encode($data);
}
else{

$query = "INSERT INTO user (userName, password) VALUES (" . $usern . "," . $pass . ")";

$con->query($query);

$success = 1;

$data = ['result' => $success];
echo json_encode($data);
}

$con->close();
?>