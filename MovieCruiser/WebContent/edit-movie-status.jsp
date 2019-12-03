<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.cognizant.moviecruiser.model.MovieList"  %>
<!Doctype html>
<html>
<head>
<title>Edit book</title>
<link rel="stylesheet" href="style/style.css">
<style type="text/css">
.body-main th
{
text-align:left;
padding-top:20px;
}
.body-main td
{
text-align:left;
}
button[type="submit"]
{
padding-top:15px;
padding-bottom:15px;
padding-left:15px;
padding-right:15px;
background-color:#004080;
color:white;
border:0;

}
b
{
color:green;
padding-left:25px;
}
h1
{
font-size:30px;
padding-left:25px;
}              
</style>
</head>
<body>
<header class="header container-fluid">
<h2 class="header-itemleft">Movie Crusier</h2>
<img src="images/movie-crusier.png" class="center">
<a class="header-itemright" href="ShowMovieListAdminServlet"  style="margin:15px">Movies</a>
</header>


<section class="body-main" style="overflow-x:auto;">
  <h1 >Edit Movie status</h1><br>
  <p style="color:z#00cc66 ;font-size:20px;"><b>Movie details saved successfully</b></p>
</section>
<div class="footer container-fluid">
                <p>&nbsp;&nbsp;Copyright &copy;2019</p>
</div>
</body>
</html>
