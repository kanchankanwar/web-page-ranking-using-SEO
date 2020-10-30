<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="webapp.UserCredentials"%>
<!DOCTYPE html>
<html lang="en">
    
<head>
        <title>Web Page Ranking</title><meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="css/bootstrap.min.css" />
		<link rel="stylesheet" href="css/bootstrap-responsive.min.css" />
        <link rel="stylesheet" href="css/matrix-login.css" />
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>



    </head>
    <body>
    <%
    UserCredentials.setUsername("");
	UserCredentials.setPassword("");
	UserCredentials.setEmailId("");
    %>
        <div id="loginbox">            
            <form id="loginform" class="form-vertical" action="Login" method="post">
				 <div class="control-group normal_text"> <h3>Login Here</h3></div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_lg"><i class="icon-user"> </i></span><input type="text" name="username" placeholder="Username" required/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_ly"><i class="icon-lock"></i></span><input type="password" name="password" placeholder="Password" required="required" />
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <span class="pull-left"><a href="#" class="flip-link btn btn-info" id="to-recover">Lost password?</a></span>
                    <span class="pull-left"><a href="Register.jsp" class="flip-link btn btn-info">Register</a></span>
                    <span class="pull-right"><input type="submit" class="btn btn-success" value = "Login" /></span>
                </div>
            </form>
            <form id="recoverform" action="Recover" class="form-vertical" method="post">
				<p class="normal_text">Enter your e-mail address below and we will send you instructions how to recover a password.</p>
				
                    <div class="controls"> 
                        <div class="main_input_box">
                            <span class="add-on bg_lo"><i class="icon-envelope"></i></span><input type="text" name="email1" placeholder="E-mail address" required="required" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"/>
                        </div>
                    </div>
               
                <div class="form-actions">
                    <span class="pull-left"><a href="Login.jsp" class="flip-link btn btn-success" id="to-login">&laquo; Back to login</a></span>
                    <span class="pull-right"><input type = "submit" class="btn btn-info" value="Recover"/></span>
                </div>
            </form>
        </div>
        
        <script src="js/jquery.min.js"></script>  
        <script src="js/matrix.login.js"></script> 
    </body>

</html>