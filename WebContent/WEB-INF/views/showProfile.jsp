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
    <title>View Profile</title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix" id="profile_page">
        <header>
            <h3 class="post-title">Profil et Contributions</h3>
        </header>
        <section id="profile_bloc">
            <fieldset>
                <legend> Mon profil</legend>
                <h5><c:out value="${user.firstname}"/> <c:out value="${user.name}"/></h5>

                <p><c:out value="${user.mailAddress}"/></p>
            </fieldset>
        </section>
        <section id="contribution_bloc">
            <fieldset>
                <legend> Mes contributions</legend>
                <c:if test="${fn:length(user.contributions) != 0 }" var="hasResults">
                    <table id="cp_table">
                        <thead>
                        <tr>
                            <td class="cp_name">Projet</td>
                            <td class="cp_category">Categorie</td>
                            <td class="cp_amount">Montant</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${user.contributions}" var="c">
                            <tr>
                                <td class="cp_name"><a
                                        href="<c:url value="/showProject?id=${c.project.id}"></c:url>">${ c.project.name }</a>
                                </td>
                                <td class="cp_category"><a
                                        href="<c:url value="/showCategory?id=${c.project.category.id}"></c:url>">${ c.project.category.name }</a>
                                </td>
                                <td class="cp_amount">${ c.amountC } €</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="!hasResults">
                    <p>Vous n'avez pas encore contribué à un projet.</p>
                </c:if>
            </fieldset>
        </section>
    </article>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
</body>
</html>