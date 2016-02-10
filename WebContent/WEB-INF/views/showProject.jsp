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
    <title><c:out value="${project.name}"/></title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix" id="project_art">
        <header>
            <h3 class="post-title"><c:out value="${project.name}"/>
                <c:if test="${project.creatorUser.mailAddress == sessionScope.sessionUser.mailAddress}">
                    <input class="button" id="editP" type="button" value="Editer le projet"
                           onclick="self.location.href='<c:url value="/user/editProject?id=${project.id}"></c:url>'"/>
                </c:if>
            </h3>

            <p>
                Créé par <c:out value="${project.creatorUser.firstname} ${project.creatorUser.name}"/><br/><br/>
                Projet lancé le : <c:out value="${project.startDate}"/><br/>
                Projet se terminant le : <c:out value="${project.endDate}"/>
            </p>
        </header>
        <c:set var="total" value="0"/>
        <p id="project_desc"><c:out value="${project.description}"/></p>
        <c:set var="contribTotal" value="${0}"/>
        <c:forEach var="contrib" items="${project.contributions}">
            <c:set var="contribTotal" value="${contribTotal + contrib.amountC}"/>
        </c:forEach>
        <div class="progress_bloc">
            <p>Progression :</p>
            <input id="max_amount" type="hidden" value="${project.amountP}"/>
            <input id="cur_amount" type="hidden" value="${contribTotal}"/>
            <progress id="project_progress" class="project_progress"
                      value="${contribTotal}" max="${project.amountP}"></progress>
            <span id="percentage"></span>

            <p id="value_display"><c:out value="${contribTotal}"/> € / <c:out value="${project.amountP}"/> €</p>
            <c:set var="now" value="<%=new java.util.Date()%>"/>
            <fmt:formatDate var="checkDate" pattern="dd/MM/yyyy HH:mm" value="${now}"/>
            <c:if test="${contribTotal ge project.amountP }">
                <p class="success_p">Ce projet est un succès !</p>
            </c:if>
            <c:if test="${checkDate  gt project.endDate}">
                <p class="finished_p">Le financement de ce projet est terminé.</p>
            </c:if>
        </div>
        <c:if test="${sessionScope.sessionUser != null && checkDate  ge project.startDate && checkDate  le project.endDate}">
            <div id="form_contrib">
                <form action="user/makeContribution" method="POST">
                    <input type="hidden" name="idP" value="${project.id}"/>
                    <input type="number" name="amountC" min="1" placeholder="xxx €"/>
                    <input class="button" id="form_ctb" type="submit" value="Contribuer"/>
                </form>
                <span class="form_error">${form.errors['amountC']}</span>

                <p>${form.result}</p>
            </div>
        </c:if>
    </article>
    <script>
        function avancement() {
            var ava = document.getElementById("cur_amount");
            var mtm = document.getElementById("max_amount");
            var prc = document.getElementById("percentage");
            prc.innerHTML = Math.round((ava.value / mtm.value * 100) * 100) / 100 + "%";
        }
        avancement();
    </script>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
</body>
</html>
