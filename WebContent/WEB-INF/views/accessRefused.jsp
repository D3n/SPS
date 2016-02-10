<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- html5.js for IE less than 9 -->
    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <!-- css3-mediaqueries.js for IE less than 9 -->
    <!--[if lt IE 9]>
    <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
    <![endif]-->
    <%@ include file="/WEB-INF/includes/stylesheets.jsp" %>
    <title>Oups !</title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix" id="error_art">
        <header>
            <h3 class="post-title">Oups !
                <img id="error_logo" alt="error logo" src="<c:url value="/assets/img/error.png"></c:url>">
            </h3>
        </header>
        <p>Nous sommes désolés mais ...</p>

        <p>... il semblerait que vous n'ayez pas les droits nécessaires pour acceder à cette page
            ou que la page que vous essayez de consulter ne soit pas ou plus disponible.</p>

        <p>Veuillez essayer de vous connecter si vous possédez un compte ou de vous en créer un,
            ou bien de retourner à l'accueil.</p>
    </article>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
</body>
</html>