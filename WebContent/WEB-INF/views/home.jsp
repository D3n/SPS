<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <script>
        function avancement(ava, mtm, prc) {
            prc.innerHTML = Math.round((ava.value / mtm.value * 100) * 100) / 100 + "%";
        }
    </script>
    <%@ include file="/WEB-INF/includes/stylesheets.jsp" %>
    <title>Home</title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix">
        <header>
            <h3 class="post-title">Actuellement sur Sup Project Starter</h3>
        </header>
        <c:if test="${fn:length(projects) != 0 }" var="hasResults">
            <c:set var="now" value="<%=new java.util.Date()%>"/>
            <fmt:formatDate var="checkDate" pattern="dd/MM/yyyy HH:mm" value="${now}"/>
            <c:forEach items="${projects}" var="p">
                <c:if test="${checkDate ge p.startDate}">
                    <div class="project_tiles">
                        <h3><a href="<c:url value="/showProject?id=${p.id}"></c:url>">${p.name}</a></h3>

                        <p>${fn:substring(p.description, 0, 200)}...</p>
                        <c:set var="contribTotal" value="${0}"/>
                        <c:forEach var="contrib" items="${p.contributions}">
                            <c:set var="contribTotal" value="${contribTotal + contrib.amountC}"/>
                        </c:forEach>
                        <div class="progress_bloc">
                            <input id="max_amount_${p.id}" type="hidden" value="${p.amountP}"/>
                            <input id="cur_amount_${p.id}" type="hidden" value="${contribTotal}"/>
                            <progress id="project_progress_${p.id}" class="project_progress"
                                      value="${contribTotal}" max="${p.amountP}"></progress>
                            <span id="percentage_${p.id}"></span><br/>
                            <c:if test="${contribTotal ge p.amountP }" var="success">
                                <p class="success_p">Ce projet est un succès !</p>
                            </c:if>
                            <c:if test="${checkDate  gt p.endDate}" var="finished">
                                <p class="finished_p">Le financement de ce projet est terminé.</p>
                            </c:if>
                            <c:if test="${ !success }">
                                <p class="notASuccess"></p>
                            </c:if>
                            <c:if test="${ !finished }">
                                <p class="notFinished"></p>
                            </c:if>
                        </div>
                        <script type="text/javascript">
                            var ava = document.getElementById("cur_amount_${p.id}");
                            var mtm = document.getElementById("max_amount_${p.id}");
                            var prc = document.getElementById("percentage_${p.id}");
                            avancement(ava, mtm, prc);
                        </script>
                        <br/>
                        <a href="<c:url value="/showProject?id=${p.id}"></c:url>">Plus de détails</a>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>
        <c:if test="${ !hasResults }">
            <p>Aucun projet</p>
        </c:if>
    </article>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
</body>
</html>