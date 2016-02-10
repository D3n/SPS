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
    <script>
        function avancement(ava, mtm, prc) {
            prc.innerHTML = Math.round((ava.value / mtm.value * 100) * 100) / 100 + "%";
        }
    </script>
    <%@ include file="/WEB-INF/includes/stylesheets.jsp" %>
    <title>Categories</title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix" id="cat_art">
        <header>
            <h3 class="post-title">
                Categories
                <form method="post" action="categories">
                    <select name="idCateg">
                        <optgroup label="Catégories">
                            <c:forEach items="${ categories }" var="category">
                                <option value="${ category.id }">${ category.name }</option>
                            </c:forEach>
                        </optgroup>
                    </select>
                    <input id="sub_cat" class="button" type="submit" value="Filtrer">
                </form>
            </h3>
        </header>
        <c:if test="${fn:length( categories ) != 0 && modShow == 'all' }" var="hasResults">
            <c:forEach items="${categories}" var="c">
                <section>
                    <fieldset>
                        <legend><c:out value=" ${c.name} "></c:out></legend>
                        <c:if test="${fn:length(c.projects) != 0 }" var="hasProjects">
                            <c:set var="now" value="<%=new java.util.Date()%>"/>
                            <fmt:formatDate var="checkDate" pattern="dd/MM/yyyy HH:mm" value="${now}"/>
                            <c:forEach items="${c.projects}" var="p">
                                <c:if test="${checkDate  ge p.startDate}">
                                    <div class="project_tiles">
                                        <h3>${p.name}</h3>

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
                        <c:if test="${ !hasProjects }">
                            <p>Aucun projet actuellement dans cette catégorie.</p>
                        </c:if>
                    </fieldset>
                </section>
            </c:forEach>
        </c:if>
        <c:if test="${ category != null && modShow == 'one' }" var="hasResults">
            <section>
                <fieldset>
                    <legend><c:out value=" ${category.name} "></c:out></legend>
                    <c:if test="${fn:length(category.projects) != 0 }" var="CategHasProjects">
                        <c:set var="now" value="<%=new java.util.Date()%>"/>
                        <fmt:formatDate var="checkDate" pattern="dd/MM/yyyy HH:mm" value="${now}"/>
                        <c:forEach items="${category.projects}" var="p">
                            <c:if test="${checkDate  ge p.startDate}">
                                <div class="project_tiles">
                                    <h3>${p.name}</h3>

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
                                        <c:if test="${checkDate  gt p.endDate}">
                                            <p class="finished_p">Le financement de ce projet est terminé.</p>
                                            <c:if test="${contribTotal ge p.amountP }">
                                                <p class="success_p">Ce projet est un succès !</p>
                                            </c:if>
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
                    <c:if test="${ !CategHasProjects }">
                        <p>Aucun projet actuellement dans cette catégorie.</p>
                    </c:if>
                </fieldset>
            </section>
        </c:if>
    </article>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
</body>
</html>
