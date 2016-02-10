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
    <title>Gestion des utilisateurs</title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix" id="mua_art">
        <header>
            <h3 class="post-title">Gestion des utilisateurs
                <input class="button" id="createU" type="button" value="Créer un utilisateur"
                       onclick="self.location.href='<c:url value="/admin/createUser"></c:url>'"/>
            </h3>
        </header>
        <section id="categorie_bloc">
            <fieldset>
                <legend>Liste des Utilisateurs</legend>
                <c:if test="${fn:length(users) != 0 }" var="hasResults">
                    <table id="mua_table">
                        <thead>
                        <tr>
                            <td class="mua_email">Email</td>
                            <td class="mua_name">Nom</td>
                            <td class="mua_firstname">Prénom</td>
                            <td class="mua_actions">Actions</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${users}" var="u">
                            <tr>
                                <td class="mua_email">
                                    <c:out value="${ u.mailAddress }"></c:out>
                                </td>
                                <td class="mua_name">
                                    <c:out value="${ u.name }"></c:out>
                                </td>
                                <td class="mua_firstname">
                                    <c:out value="${ u.firstname }"></c:out>
                                </td>
                                <td class="mua_actions">
                                    <input class="button" id="editU" type="button" value="Editer"
                                           onclick="self.location.href='<c:url
                                                   value="/admin/editUser?mailAddress=${ u.mailAddress }"></c:url>'"/>
                                    <input class="button" id="deleteU" type="button" value="Delete"
                                           onclick="if(confirm('Voulez-vous vraiment supprimer cet utilisateur ?')) self.location.href='
                                               <c:url value="/admin/deleteUser?mailAddress=${ u.mailAddress }"></c:url>'"/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="!hasResults">
                    <p>Il n'y a aucun utilisateur.</p>
                </c:if>
            </fieldset>
        </section>
    </article>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
</body>
</html>