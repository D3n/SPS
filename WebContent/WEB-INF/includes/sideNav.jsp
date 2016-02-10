<aside id="sidebar">
    <c:if test="${sessionScope.sessionUser != null }" var="connected">
        <section class="widget">
            <h4 class="widgettitle">Bienvenue</h4>
            <ul>
                <li>
                    <p>
                        <c:out value="${sessionScope.sessionUser.firstname } ${sessionScope.sessionUser.name }"/>
                    </p>
                </li>
                <li>
                    <a href="<c:url value="/user/showProfile"></c:url>">Voir mon profil</a>
                </li>
                <li>
                    <a href="<c:url value="/user/editProfile"></c:url>">Editer mon profil</a>
                </li>
                <li>
                    <input class="button" id="disconnect" type="button" value="Me Deconnecter"
                           onclick="self.location.href='<c:url value="/logout"></c:url>'"/>
                </li>
            </ul>
        </section>
    </c:if>
    <c:if test="${ !connected }">
        <section class="widget">
            <h4 class="widgettitle">Connexion</h4>

            <form class="widget clearfix" action="login" method="POST">
                <ul>
                    <li>
                        <label class="form_log" for="email">Adresse email</label>
                        <input class="form_log" type="text" name="email" placeholder="Email"
                               value="<c:out value="${user.mailAddress}"/>" required autofocus>
                        <span class="form_error">${form.errors['email_error_log']}</span><br/>
                        <label class="form_log" for="password">Mot de passe</label>
                        <input class="form_log" type="password" name="password" placeholder="Mot de Passe" required>
                        <span class="form_error">${form.errors['password_error_log']}</span><br/><br/>
                    </li>
                    <li>
                        <input class="button" id="form_log" type="submit" value="Me connecter"/><br/>
                    </li>
                    <li>
                        <a href="<c:url value="/signin"></c:url>">Creer un compte</a>
                    </li>
                </ul>
            </form>
        </section>
    </c:if>
</aside>
