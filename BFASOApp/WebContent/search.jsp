<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="webapp.DBConnection"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Web Page Ranking</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="css/fullcalendar.css" />
<link rel="stylesheet" href="css/matrix-style.css" />
<link rel="stylesheet" href="css/matrix-media.css" />
<link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="css/jquery.gritter.css" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>
</head>
<body>

<!--Header-part-->
<div id="header">
  
</div>
<!--close-Header-part--> 


<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li class=""><a title="" href="Login.jsp"><i class="icon icon-share-alt"></i> <span class="text">Logout</span></a></li>
  </ul>
</div>
<!--close-top-Header-menu-->
<!--start-top-serch-->

<!--close-top-serch-->
<!--sidebar-menu-->
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>
  <ul>
    <li><a href="index.jsp"><i class="icon icon-home"></i> <span>Dashboard</span></a> </li>
    <li class="active"> <a href="#"><i class="icon icon-signal"></i> <span>Search</span></a> </li>
     <li> <a href="displaylinks.jsp"><i class="icon icon-signal"></i> <span>Display All</span></a> </li>
<!--     <li> <a href="widgets.html"><i class="icon icon-inbox"></i> <span>Widgets</span></a> </li> -->
  </ul>
</div>
<!--sidebar-menu-->

<!--main-container-part-->
<div id="content">
<!--breadcrumbs-->
  <div id="content-header">
    <div id="breadcrumb"> <a href="index.jsp" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
  </div>
<!--End-breadcrumbs-->
<div class="container-fluid">
<div class="row-fluid">
    <div class="span6">
 <div class="widget-box">
<form action="SearchServlet" method="post"> 
	<div class="control-group">
              <label class="control-label">Search Here</label>
              <div class="controls">
              <%
              String source = "";
              Connection con = DBConnection.getDBConnection();
              PreparedStatement ps = con.prepareStatement("select distinct word from words");
              ResultSet rs = ps.executeQuery();
              while(rs.next())
              {
            	  source += "&quot;" + rs.getString("word") + "&quot;,";
              }
              source = source.substring(0, (source.length() - 1));
              %>
                <input type="text" placeholder="Type here for auto complete..." style="margin: 0 auto;" id="searchstr" name="searchstr" data-provide="typeahead" data-items="4" data-source="[<%=source %>]" class="span11">
              </div>
            </div>

			<div class="form-actions">
              <button type="submit" class="btn btn-success" id="sub">Search</button>
            </div>
            <div id="responseContent">
            
            </div>
 </form>
 </div>
</div>
</div>
</div>
        
        </div>
     

<!--end-main-container-part-->
<!-- <script type="text/javascript">
	$(document).ready(function(){
		var value1 = document.getElementById('searchstr').value;
		$('#sub').click(function(){
			$.ajax({
				url: 'SearchServlet',
				type:'POST',
				data:{searchstr: value1},
				success: function(responseText){
					$('#responseContent').html(responseText);
				},
				error: function(){
					alert('Something went wrong, please try again');
					window.location.href='index.jsp';
				}
			});
		});
	});
	</script>
 -->

<script src="js/excanvas.min.js"></script> 
<script src="js/jquery.min.js"></script> 
<script src="js/jquery.ui.custom.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/jquery.flot.min.js"></script> 
<script src="js/jquery.flot.resize.min.js"></script> 
<script src="js/jquery.peity.min.js"></script> 
<script src="js/fullcalendar.min.js"></script> 
<script src="js/matrix.js"></script> 
<script src="js/matrix.dashboard.js"></script> 
<script src="js/jquery.gritter.min.js"></script> 
<script src="js/matrix.interface.js"></script> 
<script src="js/matrix.chat.js"></script> 
<script src="js/jquery.validate.js"></script> 
<script src="js/matrix.form_validation.js"></script> 
<script src="js/jquery.wizard.js"></script> 
<script src="js/jquery.uniform.js"></script> 
<script src="js/select2.min.js"></script> 
<script src="js/matrix.popover.js"></script> 
<script src="js/jquery.dataTables.min.js"></script> 
<script src="js/matrix.tables.js"></script> 

<script type="text/javascript">
  // This function is called from the pop-up menus to transfer to
  // a different page. Ignore if the value returned is a null string:
  function goPage (newURL) {

      // if url is empty, skip the menu dividers and reset the menu selection to default
      if (newURL != "") {
      
          // if url is "-", it is this page -- reset the menu:
          if (newURL == "-" ) {
              resetMenu();            
          } 
          // else, send page to designated URL            
          else {  
            document.location.href = newURL;
          }
      }
  }

// resets the menu selection upon entry to this page:
function resetMenu() {
   document.gomenu.selector.selectedIndex = 2;
}
</script>
</body>
</html>
