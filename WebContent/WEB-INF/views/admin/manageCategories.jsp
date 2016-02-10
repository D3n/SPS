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
    <title>Gestion des catégories</title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix" id="mca_art">
        <header>
            <h3 class="post-title">Gestion des categories</h3>
        </header>
        <section id="mca_form_bloc">
            <c:choose>
                <c:when test="${modForm == 'add'}">
                    <c:set var="formAction" value="manageCategories"/>
                </c:when>
                <c:otherwise>
                    <c:set var="formAction" value="editCategory"/>
                </c:otherwise>
            </c:choose>
            <form action="${ formAction }" method="post" id="form_category">
                <fieldset>
                    <legend>Ajouter une catégorie</legend>
                    <input type="hidden" name="id" value="<c:out value="${category.id}"/>"/>
                    <label for="name">Nom</label><br/>
                    <input placeholder="Nom de la catégorie" type="text" id="name" name="name"
                           value="<c:out value="${category.name}"/>" size="20" maxlength="20"/><br/>
                    <span class="form_error">${form.errors['name']}</span>
                    <br/>
                    <c:choose>
                        <c:when test="${modForm == 'add'}">
                            <input class="button" type="submit" value="Ajouter">
                        </c:when>
                        <c:otherwise>
                            <input class="button" type="submit" value="Modifier">
                        </c:otherwise>
                    </c:choose>
                    <br/>

                    <p>${form.result}</p>
                </fieldset>
            </form>
        </section>
        <section id="mca_bloc">
            <fieldset>
                <legend>Liste des Categories</legend>
                <c:if test="${fn:length(categories) != 0 }" var="hasResults">
                    <table id="mca_table">
                        <thead>
                        <tr>
                            <td class="mca_name">Nom</td>
                            <td class="mca_category">Actions</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${categories}" var="c">
                            <tr>
                                <td class="mca_name"><a
                                        href="<c:url value="/categories?id=${c.id}"></c:url>">${ c.name }</a></td>
                                <td class="mca_actions">
                                    <input class="button" id="editC" type="button" value="Editer"
                                           onclick="self.location.href='<c:url
                                                   value="/admin/editCategory?id=${ c.id }"></c:url>'"/>
                                    <input class="button" id="deleteC" type="button" value="Delete"
                                           onclick="if(confirm('Voulez-vous vraiment supprimer cette catégorie ?')) self.location.href='
                                               <c:url value="/admin/deleteCategory?id=${ c.id }"></c:url>'"/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="!hasResults">
                    <p>Il n'y a aucune catégorie.</p>
                </c:if>
            </fieldset>
        </section>
    </article>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
</body>
</html>