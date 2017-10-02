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

$userName = $_GET["userName"];
$pass = $_GET["password"];

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
?>