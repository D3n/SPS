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
    <title>Edit Profile</title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix">
        <header>
            <h3 class="post-title">Edition</h3>
        </header>
        <form action="editProfile" method="post" id="form_edit">
            <fieldset>
                <legend> Editer mon profil</legend>
                <label for="name">Nom</label><br/>
                <input placeholder="name" type="text" id="name" name="name"
                       value="<c:out value="${user.name}"/>" size="20" maxlength="20"/><br/>
                <span class="form_error">${form.errors['name']}</span>
                <br/>
                <label for="firstname">Prénom</label><br/>
                <input placeholder="prenom" type="text" id="firstname" name="firstname"
                       value="<c:out value="${user.firstname}"/>" size="20" maxlength="20"/><br/>
                <span class="form_error">${form.errors['firstname']}</span>
                <br/>
                <label for="password">Entrez votre nouveau mot de passe</label><br/>
                <input placeholder="Mot de Passe" type="password" id="password" name="password"
                       value="" size="20" maxlength="20"/><br/>
                <span class="form_error">${form.errors['password']}</span>
                <br/>
                <label for="confirmation">Confirmez votre nouveau mot de passe</label><br/>
                <input placeholder="Confirmation" type="password" id="confirmation" name="confirmation"
                       value="" size="20" maxlength="20"/><br/>
                <span class="form_error">${form.errors['confirmation']}</span>
                <br/>
                <input class="button" type="submit" value="Modifier mon profil">
                <br/>

                <p>${form.result}</p>
            </fieldset>
        </form>
    </article>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
</body>
</html>
