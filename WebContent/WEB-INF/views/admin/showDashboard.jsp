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
    <title>Admin Dashboard</title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix" id="adb_art">
        <header>
            <h3 class="post-title">Dashboard Administrateur</h3>
        </header>
        <section id="adb_section1">
            <fieldset>
                <legend>Informations principales</legend>
                <table id="adb_table">
                    <thead>
                    <tr>
                        <th class="user_col">Nombre d'utilisateurs</th>
                        <th class="proj_col">Nombre de projets</th>
                        <th class="cont_col">Total des contributions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="user_col"><c:out value="${ nbUsers }"/></td>
                        <td class="proj_col"><c:out value="${ nbProjects }"/></td>
                        <c:set var="contributionTotal" value="${0}"/>
                        <c:if test="${fn:length(contributions) != 0 }" var="hasResults">
                            <c:forEach items="${contributions}" var="c">
                                <c:set var="contributionTotal" value="${contributionTotal + c.amountC}"/>
                            </c:forEach>
                        </c:if>
                        <td class="cont_col"><c:out value="${ contributionTotal }"/> €</td>
                    </tr>
                    </tbody>
                </table>
            </fieldset>
        </section>
        <section>
            <fieldset>
                <legend>Administration</legend>
                <ul>
                    <li>
                        <a href="<c:url value="/admin/manageUsers"></c:url>">
                            <img id="user_edit_logo" alt="user edit logo"
                                 src="<c:url value="/assets/img/user_edit.png"></c:url>">
                        </a><br/>Gérer les utilisateurs
                    </li>
                    <li>
                        <a href="<c:url value="/admin/manageProjects"></c:url>">
                            <img id="proj_edit_logo" alt="project edit logo"
                                 src="<c:url value="/assets/img/project_edit.png"></c:url>">
                        </a><br/>Gérer les projets
                    </li>
                    <li>
                        <a href="<c:url value="/admin/manageCategories"></c:url>">
                            <img id="category_edit_logo" alt="category edit logo"
                                 src="<c:url value="/assets/img/category_edit.png"></c:url>">
                        </a><br/>Gérer les catégories
                    </li>
                </ul>
            </fieldset>
        </section>
    </article>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
</body>
</html>
