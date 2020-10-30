<%@page import="java.sql.PreparedStatement"%>
<%@page import="webapp.DBConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="webapp.CrawlerThread"%>
<%@page import="java.util.concurrent.Executors"%>
<%@page import="java.util.concurrent.Executor"%>
<%@page import="java.util.concurrent.ExecutorService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Page Ranking</title>
</head>
<body>
<%
/* Connection con = DBConnection.getDBConnection();
PreparedStatement ps = con.prepareStatement("DELETE FROM contain");
ps.executeUpdate();
PreparedStatement ps1 = con.prepareStatement("DELETE FROM links");
ps1.executeUpdate();
PreparedStatement ps2 = con.prepareStatement("DELETE FROM linkto");
ps2.executeUpdate();
PreparedStatement ps3 = con.prepareStatement("DELETE FROM page");
ps3.executeUpdate();
PreparedStatement ps4 = con.prepareStatement("DELETE FROM words");
ps4.executeUpdate(); */
ExecutorService executorService = Executors.newFixedThreadPool(10);
CrawlerThread ct = new CrawlerThread();
executorService.submit(ct);

RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
rd.forward(request, response);
%>
</body>
</html>