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
    <link href='<c:url value="/assets/css/jquery-ui-1.10.3.custom.css"></c:url>' rel="stylesheet" type="text/css"/>
    <%@ include file="/WEB-INF/includes/stylesheets.jsp" %>
    <title>Create project</title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix">
        <header>
            <h3 class="post-title">Creez un projet</h3>
        </header>
        <form action="createProject" method="post" id="form_project">
            <fieldset>
                <legend>Projet</legend>
                <p>Ce formulaire permet de créer un nouveau projet, cependant un utilisateur
                    (non administrateur) doit en être déclaré créateur.</p>

                <div id="project_creator">
                    <label for="creator">Créateur</label>
                    <br/>
                    <select id="creator_selector" name="creator">
                        <c:forEach items="${ users }" var="user">
                            <option value="${ user.mailAddress }">${ user.firstname } ${ user.name }</option>
                        </c:forEach>
                    </select>
                    <span class="form_error">${form.errors['creator']}</span>
                </div>
                <div id="container_nNc">
                    <div id="project_name">
                        <label for="name">Nom de votre projet</label>
                        <br/>
                        <input placeholder="Nom" type="text" id="name" name="name"
                               value="<c:out value="${project.name}"/>" size="20" maxlength="20" required/>
                        <br/>
                        <span class="form_error">${form.errors['name']}</span>
                    </div>
                    <div id="project_cat">
                        <label for="category">Catégorie</label>
                        <br/>
                        <select name="category">
                            <c:forEach items="${ categories }" var="category">
                                <option value="${ category.id }" <c:if
                                        test="${ project.category.id == category.id }"> selected="selected" </c:if> >
                                        ${ category.name }
                                </option>
                            </c:forEach>
                        </select>
                        <span class="form_error">${form.errors['category']}</span>
                    </div>
                </div>
                <br/>
                <label for="description">Description</label>
                <br/>
                <textarea name="description" placeholder="Décrivez votre projet" maxlength="1000" rows=10 required>
                    <c:out value="${project.description}"/>
                </textarea>
                <span class="form_error">${form.errors['description']}</span>
                <br/>

                <div id="container_aNl">
                    <div id="project_am">
                        <label for="amount">Montant à atteindre pour la réalisation de votre projet</label>
                        <br/>
                        <input type="number" name="amount" placeholder="xxxx €"
                               value="<c:out value="${project.amountP}"/>">
                        <span class="form_error">${form.errors['amount']}</span>
                    </div>
                    <div id="project_ml">
                        <img id="money_logo" alt="money logo" src="<c:url value="/assets/img/cf_money.jpg"></c:url>">
                    </div>
                </div>
                <br/>

                <div id="container_dNd">
                    <div id="project_dstart">
                        <label for="start_datepicker">Date et heure de début de la campagne de financement</label>
                        <input type="text" name="dateStart" id="start_datepicker"
                               value="<c:out value="${project.startDate}"/>" required>
                        <span class="form_error">${form.errors['dateStart']}</span>
                    </div>
                    <div id="project_dend">
                        <label for="end_datepicker">Date et heure de fin de la campagne de financement</label>
                        <input type="text" name="dateEnd" id="end_datepicker"
                               value="<c:out value="${project.endDate}"/>" required>
                        <span class="form_error">${form.errors['dateEnd']}</span>
                    </div>
                </div>
                <span class="form_error">${form.result}</span>
                <br/>
                <input class="button" type="submit" value="Créer mon Projet"/>
            </fieldset>
        </form>
    </article>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
<script src="<c:url value="/assets/js/jquery-1.9.1.js"></c:url>">
</script>
<script src="<c:url value="/assets/js/jquery-ui-1.10.3.custom.js"></c:url>">
</script>
<script>
    $(function () {
        $("#start_datepicker").datetimepicker({
            regional: ["fr"],
            dateFormat: 'dd/mm/yy',
            minDate: 0
        });
        $("#end_datepicker").datetimepicker({
            regional: ["fr"],
            dateFormat: 'dd/mm/yy',
            minDate: 0
        });
    });
</script>
</body>
</html>