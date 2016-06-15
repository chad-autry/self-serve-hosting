<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
  <head>
    <meta charset="utf-8">
    <!-- The local favicon -->
    <link rel="icon" type="image/x-icon" href="./resources/favicon.ico">
    <!-- font awesome from BootstrapCDN -->
    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.css" rel="stylesheet">
    <!-- The compiled stylesheets -->
    <link rel="stylesheet" type="text/css" href="./spa/styles.css" />
    <title>Self Serve Hosting</title>
  </head>
  <body>
    <script type="text/javascript">
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);
%>


    window.nickname="${fn:escapeXml(user.nickname)}"
    window.logoutURL="<%= userService.createLogoutURL(request.getRequestURI()) %>"
    <%
        } else {
    %>
    window.loginURL="<%= userService.createLoginURL(request.getRequestURI()) %>"
    <%
        }
    %>
    </script>
    <div id="app" />
    <script src="./spa/bundle.js" type="text/javascript"></script>
  </body>
</html>