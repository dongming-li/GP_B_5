<?php 
 
	$host="mysql.cs.iastate.edu";
	$port=3306;
	$socket="";
	$user="dbu309gpb5";
	$password="!tvDzTDx";
	$dbname="db309gpb5";

	$conn = new mysqli($host, $user, $password, $dbname, $port, $socket);
	$response = array();
	
	if($conn->connect_error){
		die("Connection Failed: " . $conn->connect_error);
	}
	
	if(isset($_GET['apicall'])){
		switch($_GET['apicall']){
			case 'kills_desc':
				$query = "SELECT userName, kills FROM Leaderboard order by kills desc";
				
				if($result = $conn->query($query)) {
					while($row = $result->fetch_assoc()) {
						$response['users'][] = $row;
					}
				}
			break;
			
			case 'kills_asc':
				$query = "SELECT userName, kills FROM Leaderboard order by kills asc";
				
				if($result = $conn->query($query)) {
					while($row = $result->fetch_assoc()) {
						$response['users'][] = $row;
					}
				}
			break;
			
			case 'level_desc':
				$query = "SELECT userName, level FROM Leaderboard order by level desc";
				
				if($result = $conn->query($query)) {
					while($row = $result->fetch_assoc()) {
						$response['users'][] = $row;
					}
				}
			break;
			
			case 'level_asc':
				$query = "SELECT userName, level FROM Leaderboard order by level asc";
				
				if($result = $conn->query($query)) {
					while($row = $result->fetch_assoc()) {
						$response['users'][] = $row;
					}
				}
			break;
			
			case 'experience_desc':
				$query = "SELECT userName, experience FROM Leaderboard order by experience desc";
				
				if($result = $conn->query($query)) {
					while($row = $result->fetch_assoc()) {
						$response['users'][] = $row;
					}
				}
			break;
			
			case 'experience_asc':
				$query = "SELECT userName, experience FROM Leaderboard order by experience asc";
				
				if($result = $conn->query($query)) {
					while($row = $result->fetch_assoc()) {
						$response['users'][] = $row;
					}
				}
			break;
			
			default:	 
				$response['error'] = true; 
				$response['message'] = 'Invalid API Call';
				echo json_encode($response);
		}
	} else {
		$response['error'] = true; 
		$response['message'] = 'You must provide a proper Leaderboard Call';
		echo json_encode($response);
	}
	echo json_encode($response);
	$conn->close();
		
	function areParamsValid($params){
 
	foreach($params as $param){
		if(!isset($_POST[$param])){
			return false; 
			}
		}
		return true; 
	}
 ?>