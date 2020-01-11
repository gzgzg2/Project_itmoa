<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
${findIdOk }
<c:choose>
	<c:when test="${fn:length(findIdOk) == 0 }">
		<script>
			alert("아이디 찾기 실패");
			history.back();
		</script>  
	</c:when>
	<c:when test="${fn:length(findIdOk) == 1 }">
		<script>
			alert("아이디 찾기 성공");
			//location.href = "/Project_itmoa/user/findIdView.do";
		</script>
	</c:when>
</c:choose>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>FindIdView</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/pe-icons.css" rel="stylesheet">
    <link href="css/prettyPhoto.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link href="css/style3.css" rel="stylesheet">
    
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <script src="js/jquery.js"></script>
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144x144.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114x114.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/images/ico/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57x57.png">

</head><!--/head-->
<body>
<div id="preloader"></div>
    <header class="navbar navbar-inverse navbar-fixed-top opaqued" role="banner">
    <div id="search-wrapper">
        <div class="container">
            <input id="search-box" placeholder="Search">
        </div>
    </div>
    </div>
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand" href="index.html"><h1><span class="pe-7s-gleam bounce-in"></span> IMPACT</h1></a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="index.html">메인</a></li>
                    <li><a href="portfolio copy 4.html">국비</a></li>
                    <li><a href="portfolio copy 4.html">정규</a></li>
                    <li><a href="portfolio copy 4.html">단과</a></li>
                   <!-- <li><a href="blog.html">커뮤니티</a></li>-->
                    <li><a href="contact-us.html">Contact</a></li>
                    <li class="dropdown active">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">커뮤니티 <i class="icon-angle-down"></i></a>
                        <ul class="dropdown-menu">
                            <li><a href="blog copy 2.html">it뉴스</a></li>
                            <li><a href="blog copy 3.html">리뷰게시판</a></li>
                            <li><a href="blog copy 4.html">Project Single</a></li>
                        </ul>
                    </li>
                    <li><span class="search-trigger"><i class="fa fa-search"></i></span></li>
                </ul>
            </div>
        </div>
    </header><!--/header-->
    <div class="a" id="div-find-id">
        <div class="find-id">
        	<h4>아이디 찾기</h4>
        </div>
        <div id="find-id" class="find-id">
        	<div id="find-id-info" class="find-id">
        		<div name="mb_name" class="find-id-info" type="text" placeholder="이름" required="required">아이디 : ${findIdOk[0].mb_id }</div><br>
        	</div>
        	<button id="find-id-btn" onclick="location.href='login.do'">로그인</button>
        </div>
    </div>
    
		
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.prettyPhoto.js"></script>
    <script src="js/plugins.js"></script>
    <script src="js/init.js"></script>
</body>
</html>