<%@page import="java.util.Map"%>
<%@page import="webapp.ConstantsVar"%>
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
    <li> <a href="search.jsp"><i class="icon icon-signal"></i> <span>Search</span></a> </li>
    <li class="active"> <a href="displaylinks.jsp"><i class="icon icon-signal"></i> <span>Display All</span></a> </li>
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
  <li class="bg_lo"> <a href="index.jsp"> <i class="icon-home"></i>icon-home </a> </li>
 


	<div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-ok"></i></span>
            <h5>Pages And Links</h5>
          </div>
          <div class="widget-content">
          <table border="1" background="#F9F9F9">
          <thead>
          <th>Page</th>
          <th>Links</th>
          </thead>
          <tbody>
          
          
            <%
            Connection con = DBConnection.getDBConnection();
            PreparedStatement ps = con.prepareStatement("select url from page");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
            	String pages = rs.getString("url");
            	PreparedStatement ps1 = con.prepareStatement("SELECT links.url FROM links, linkto, page WHERE page.ID = linkto.source AND links.ID = linkto.destination AND page.url = '" + pages + "'");
            	ResultSet rs1 = ps1.executeQuery();
            	rs1.last();
            	int count = rs1.getRow();
            	rs1.beforeFirst();
            	%><tr>
    			<td rowspan="<%=count+1 %>" valign="top"><a href="<%=pages %>"><%=pages %><a></a></td>
    			<td></td>
    			</tr><%
            	while(rs1.next())
            	{
            		%>
            			<tr>
            			<td><a href="<%=rs1.getString("url") %>"><%=rs1.getString("url") %></a></td>
            			</tr>
            		<%
            	}
            }
            %>
            
            </tbody>
          </table>
          </div>
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