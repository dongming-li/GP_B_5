<?php 
 
	$host="mysql.cs.iastate.edu";
	$port=3306;
	$socket="";
	$user="dbu309gpb5";
	$password="!tvDzTDx";
	$dbname="db309gpb5";

	$conn = new mysqli($host, $user, $password, $dbname, $port, $socket);

	if($conn->connect_error){
		die("Connection Failed: " . $conn->connect_error);
	}
 
	$response = array();
	
	if(isset($_GET['apicall'])){
		switch($_GET['apicall']){
			case 'register':		 
				if(areParamsValid(array('userName', 'userPass'))){
					$username = $_POST['userName'];
					$userpass = $_POST['userPass'];
					$userAccountType = $_POST['accountType'];
					
					$experience  = 0;
					$level 		 = 0;
					$kills		 = 0;
					$accountType = 0;
					
					 $queryUsers = $conn->prepare("SELECT userName FROM Users WHERE userName = ?");
					 $queryUsers->bind_param("s", $username);
					 $queryUsers->execute();
					 $queryUsers->store_result();
					 
					 if($queryUsers->num_rows > 0){
					 $response['error'] = true;
					 $response['message'] = 'User already registered';
					 $queryUsers->close();
					 }else{
						$insertUser = $conn->prepare("Insert into Users(userName, userPass, accountType) Values (?, ?, ?)");
						$insertUser->bind_param("sss", $username, $userpass, $userAccountType);
						if($insertUser->execute()){
							
							$insertLeaderboard = $conn->prepare("Insert into Leaderboard(userName) Values (?)");
							$insertLeaderboard->bind_param("s", $username);
							
							if($insertLeaderboard->execute()) {
								$response['error'] = false;
								$response['message'] = 'User Registered';
							} else {
								$response['error'] = false;
								$response['message'] = 'User could not be added to the Leaderboard';
							}
						}else{
							$response['error'] = true;
							$response['message'] = 'Failed User Registration';
						}
					}
				}
			break;
			
			case 'login':
					if(areParamsValid(array('userName', 'userPass'))){
						$username = $_POST['userName'];
						$userpass = $_POST['userPass'];
						
						$queryUsers = $conn->prepare("SELECT userName FROM Users WHERE userName = ? AND userPass = ?");
						$queryUsers->bind_param("ss", $username, $userpass);
						$queryUsers->execute();
						$queryUsers->store_result();
						
						if($queryUsers->num_rows>0){
							$queryUsers->bind_result($username, $password);
							$queryUsers->fetch();

							$queryUserLeader = $conn->prepare("select level, experience, kills from Leaderboard where userName = ?");
							$queryUserLeader->bind_param("s", $username);
							$queryUserLeader->execute();
							$queryUserLeader->store_result();
							$queryUserLeader->bind_result($uLevel, $uExperience, $uKills);
							$queryUserLeader->fetch();
							
							$queryUserAccountType = $conn->prepare("Select accountType from Users where userName = ?");
							$queryUserAccountType->bind_param("s", $username);
							$queryUserAccountType->execute();
							$queryUserAccountType->store_result();
							$queryUserAccountType->bind_result($accountType);
							$queryUserAccountType->fetch();
							
							$response['error'] = false;
							$response['message'] = 'Login successfull';
							$response['UserName'] = $username;
							$response['Level'] = $uLevel;
							$response['Experience'] = $uExperience;
							$response['Kills'] = $uKills;
							$response['AccountType'] = $accountType;

							$queryUserAccountType->close();
							$queryUserLeader->close();
						}
						$queryUsers->close();
					} else {
						$response['error'] = true; 
						$response['message'] = 'Invalid username or password'; 
					}
			break;
			
			default:	 
				$response['error'] = true; 
				$response['message'] = 'Invalid API Call';
		}
	}else{
		$response['error'] = true; 
		$response['message'] = 'You must provide an API Call';
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