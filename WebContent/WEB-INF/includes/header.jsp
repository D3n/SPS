<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header id="header">
    <div>
        <h1 id="site-logo">
            <a href="
                <c:url value="/home"></c:url>">Sup Project Starter
            </a>
        </h1>

        <h2 id="site-description">Participez dés aujourd'hui aux réalités de demain !</h2>

        <h3 id="site-image">
            <img alt="crowdfunding logo" src="
                <c:url value="/assets/img/crowdfunding.jpg"></c:url>">
        </h3>
    </div>
    <nav>
        <ul id="main-nav" class="clearfix">
            <li>
                <a href="
                        <c:url value="/home"></c:url>">Accueil
                </a>
            </li>
            <li>
                <a href="
                        <c:url value="/categories"></c:url>">Catégories
                </a>
            </li>
            <c:if test="${sessionScope.sessionUser.group.label == 'User'}">
                <li>
                    <a href="
                            <c:url value="/user/addProject"></c:url>">Créer mon Projet
                    </a>
                </li>
            </c:if>
            <li>
                <a href="
                        <c:url value="/about"></c:url>">A Propos
                </a>
            </li>
            <c:if test="${sessionScope.sessionUser.group.label == 'Admin'}">
                <li id="admin">
                    <a href="
                            <c:url value="/admin/showDashboard"></c:url>">Administration
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>
</header>
