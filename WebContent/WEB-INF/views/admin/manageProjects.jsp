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
    <title>Gestion des projets</title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix" id="mpa_art">
        <header>
            <h3 class="post-title">Gestion des projets
                <input class="button" id="createP" type="button" value="Créer un projet"
                       onclick="self.location.href='<c:url value="/admin/createProject"></c:url>'"/>
            </h3>
        </header>
        <section>
            <fieldset>
                <legend>Liste des projets</legend>
                <c:if test="${fn:length(projects) != 0 }" var="hasResults">
                    <table id="mpa_table">
                        <thead>
                        <tr>
                            <td class="mpa_name">Nom</td>
                            <td class="mpa_amount">Montant</td>
                            <td class="mpa_creator">Créateur</td>
                            <td class="mpa_category">Catégorie</td>
                            <td class="mpa_actions">Actions</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${projects}" var="p">
                            <tr>
                                <td class="mpa_name">
                                    <c:out value="${ p.name }"></c:out>
                                </td>
                                <td class="mpa_amount">
                                    <c:out value="${ p.amountP }"></c:out> €
                                </td>
                                <td class="mpa_creator">
                                    <c:out value="${ p.creatorUser.name} ${ p.creatorUser.firstname }"></c:out>
                                </td>
                                <td class="mpa_category">
                                    <c:out value="${ p.category.name  }"></c:out>
                                </td>
                                <td class="mpa_actions">
                                    <input class="button" id="editU" type="button" value="Editer"
                                           onclick="self.location.href='<c:url
                                                   value="/admin/editProject?id=${ p.id }"></c:url>'"/>
                                    <input class="button" id="deleteU" type="button" value="Delete"
                                           onclick="if(confirm('Voulez-vous vraiment supprimer ce projet ?')) self.location.href='
                                               <c:url value="/admin/deleteProject?id=${ p.id }"></c:url>'"/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="!hasResults">
                    <p>Il n'y a aucun projet.</p>
                </c:if>
            </fieldset>
        </section>
    </article>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
</body>
</html>