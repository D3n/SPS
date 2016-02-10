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
    <title>A Propos</title>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<main role="main" id="content">
    <article class="post clearfix" id="about_art">
        <header>
            <h3 class="post-title">Le financement participatif</h3>
        </header>
        <h3>C'est quoi?</h3>

        <p>Le financement participatif (ou crowdfunding en anglais) consiste à faire financer son projet par la foule.
            Sur SupCrownFunding, des créateurs de projets de tous horizons peuvent présenter leur projet à des milliers
            d’internautes et collecter les fonds nécessaires à leur réalisation.
            Ce procédé a permis à des dizaines de milliers de projets de voir le jour à travers le monde.</p>

        <h3>Comment ça marche ?</h3>

        <p>Chaque créateur définit le montant dont il a besoin pour réaliser son projet et la durée de sa collecte.
            L'objectif du porteur de projet est de remplir son objectif avant sa date de fin de collecte.
            Pour convaincre les internautes de le soutenir, il présente son projet et fixe les contreparties
            qu'il offrira à ses contributeurs si son objectif de collecte est atteint.</p>

        <h3>Que se passe-t’il si le projet n’arrive pas à 100% à la fin de sa période de collecte ?</h3>

        <p>Si le projet n’a pas réuni le montant de sa jauge à la fin de sa période de collecte, les contributeurs
            récupèrent leur participation. Elle est automatiquement versée sur leur Crédit.
            Ils peuvent utiliser ce crédit pour participer à un autre projet ou en demander le remboursement sur leur
            compte en banque.
            (Seuls les frais de gestion ne sont pas remboursés).</p>

        <h3>Et si le projet atteint son objectif avant la fin de sa période de collecte ?</h3>

        <p>Les internautes peuvent soutenir un projet tout au long de sa période de collecte même si l'objectif est
            atteint.
            Il est fréquent que des projets dépassent donc leur objectifs.
            La plupart du temps, le porteur du projet explique comment sera dépensé le montant allant au-delà de son
            objectif.
            Ce dernier doit également s’assurer qu’il pourra délivrer les contreparties associées aux participations
            supplémentaires.</p>

        <h3>Puis-je annuler ma participation en cours de route ?</h3>

        <p>Tant qu’un projet n'a pas atteint son objectif de collecte (les 100% de sa jauge), ses contributeurs peuvent
            à tout moment annuler leur participation.
            Elle est versée sur leur Crédit et ils peuvent en demander le remboursement sur leur compte en banque
            (Seuls les frais de gestion ne sont pas remboursés). A contrario, lorsque l'objectif de collecte est
            atteint,
            les contributions deviennent définitives et ne peuvent donner lieu à un remboursement à posteriori.</p>

        <h3>Qui est responsable de la réalisation du projet et de la livraison des contreparties ?</h3>

        <p>C'est le porteur de projet qui est responsable de la bonne réalisation du projet et de la livraison de ses
            contreparties.
            Son nom est systématiquement affiché en haut de sa page projet.</p>

        <h3>Peut-on gagner de l'argent en soutenant des projets ?</h3>

        <p>Le retour financier est une des contreparties possibles sur SupCrownFunding.
            Les projets qui en proposent sont indiqués par un bandeau orange "Retour financier".
            Seuls des projets portés par SupCrownFunding ou ses partenaires peuvent en offrir afin que les contributeurs
            soient
            garantis du bon calcul et du bon versement de leurs droits. Attention ! Ne voyez pas le financement
            participatif comme un moyen
            de vous enrichir.
            L'objectif du financement participatif est avant tout de permettre à des projets que vous aimez d'exister
            grâce à votre soutien.</p>

        <h3>Le crowdfunding est-il encadré par une législation ?</h3>

        <p>Le 14 mai 2013, l’AMF et l’ACP ont publié deux guides chargés d’offrir un cadre législatif clair au
            financement
            participatif français. L’un à destination du grand public, l’autre à destination des plates-formes et des
            porteurs
            de projet. Nous vous encourageons à les consulter !</p>
    </article>
</main>
<%@ include file="/WEB-INF/includes/sideNav.jsp" %>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
</body>
</html>
