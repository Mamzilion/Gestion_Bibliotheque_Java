<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="inc/header.jsp" %>

<div style="max-width: 1000px; margin: 50px auto; padding: 20px;">
    <%-- Bouton retour --%>
    <a href="accueil" style="text-decoration: none; color: #f06292; font-weight: bold; display: flex; align-items: center; margin-bottom: 30px; transition: 0.3s;">
        <span style="margin-right: 10px;">←</span> Retour à la collection
    </a>

    <div style="display: flex; gap: 50px; background: white; padding: 40px; border-radius: 30px; box-shadow: 0 20px 50px rgba(0,0,0,0.08); border: 1px solid rgba(252, 228, 236, 0.5);">
        
        <%-- Colonne de gauche : Image --%>
        <div style="flex: 1; text-align: center;">
            <div style="border-radius: 20px; overflow: hidden; box-shadow: 0 10px 30px rgba(0,0,0,0.15); background: #fdfdfd; min-height: 380px; display: flex; align-items: center; justify-content: center;">
                <c:choose>
                    <c:when test="${not empty doc.vignette}">
                        <img src="${pageContext.request.contextPath}/img/vignettes/${doc.vignette}" 
                             alt="${doc.titre}"
                             style="width: 100%; height: auto; display: block; object-fit: cover;">
                    </c:when>
                    <c:otherwise>
                        <div style="background: #fce4ec; width: 100%; height: 380px; display: flex; align-items: center; justify-content: center; font-size: 6rem;">📖</div>
                    </c:otherwise>
                </c:choose>
            </div>
            <p style="margin-top: 20px; color: #888; font-size: 0.85rem; letter-spacing: 1px;">ISBN : <b>${doc.isbn}</b></p>
        </div>

        <%-- Colonne de droite : Infos --%>
        <div style="flex: 1.5; display: flex; flex-direction: column;">
            <div style="margin-bottom: 10px;">
                <span style="background: #fce4ec; color: #f06292; padding: 5px 15px; border-radius: 20px; font-size: 0.75rem; font-weight: bold; text-transform: uppercase;">
                    ${doc.domaine}
                </span>
            </div>
            
            <h1 style="font-family: 'Georgia', serif; font-size: 2.6rem; margin: 10px 0; color: #1a1a1a; line-height: 1.2;">
                ${doc.titre}
            </h1>
            
            <h3 style="color: #666; font-weight: 400; margin-bottom: 30px; font-size: 1.2rem;">
                Par <span style="color: #1a1a1a; font-weight: 700;">${doc.auteur.nom}</span>
            </h3>

            <div style="margin-bottom: 30px; flex-grow: 1;">
                <h4 style="color: #f06292; text-transform: uppercase; font-size: 0.8rem; letter-spacing: 1px; margin-bottom: 10px;">Résumé</h4>
                <p style="line-height: 1.8; color: #444; font-size: 1.05rem; text-align: justify;">
                    <c:out value="${not empty doc.description ? doc.description : 'Aucune description disponible.'}" />
                </p>
            </div>

            <%-- Action Principale : LECTURE --%>
            <div style="margin-bottom: 25px;">
                <a href="${pageContext.request.contextPath}/lire?isbn=${doc.isbn}" 
                   target="_blank" 
                   style="display: inline-block; background: #f06292; color: white; padding: 15px 35px; border-radius: 50px; text-decoration: none; font-weight: bold; transition: 0.3s; box-shadow: 0 10px 20px rgba(240, 98, 146, 0.3);"
                   onmouseover="this.style.transform='scale(1.05)'"
                   onmouseout="this.style.transform='scale(1)'">
                    📖 LIRE LE DOCUMENT COMPLET
                </a>
            </div>

            <%-- Détails techniques --%>
            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; padding: 20px 0; border-top: 1px solid #eee;">
                <div>
                    <h4 style="color: #f06292; text-transform: uppercase; font-size: 0.7rem; margin-bottom: 5px; font-weight: 800;">Édition</h4>
                    <p style="font-weight: 600; color: #1a1a1a;">${not empty doc.maisonEdition.nomMaison ? doc.maisonEdition.nomMaison : 'N/A'}</p>
                </div>
                <div>
                    <h4 style="color: #f06292; text-transform: uppercase; font-size: 0.7rem; margin-bottom: 5px; font-weight: 800;">Format</h4>
                    <p style="font-weight: 600; color: #1a1a1a;">Ouvrage Numérique / Papier</p>
                </div>
            </div>

            <%-- SECTION TÉLÉCHARGEMENT --%>
            <div style="margin-top: 20px; background: #f9f9f9; padding: 25px; border-radius: 20px; border: 1px solid #eee;">
                <h4 style="color: #1a1a1a; font-size: 0.8rem; text-transform: uppercase; margin-bottom: 15px; letter-spacing: 1px; font-weight: 800;">
                    📥 Autres options
                </h4>
                <div style="display: flex; gap: 10px; flex-wrap: wrap;">
                    <a href="#" class="btn-dl" style="flex: 1; text-align: center; background: #ef5350; color: white; padding: 12px; border-radius: 10px; text-decoration: none; font-weight: bold; font-size: 0.8rem; transition: 0.3s;">📄 PDF</a>
                    <a href="#" class="btn-dl" style="flex: 1; text-align: center; background: #42a5f5; color: white; padding: 12px; border-radius: 10px; text-decoration: none; font-weight: bold; font-size: 0.8rem; transition: 0.3s;">📱 EPUB</a>
                </div>
                <p style="font-size: 0.7rem; color: #999; margin-top: 12px; text-align: center; font-style: italic;">
                    * Accès sécurisé réservé aux étudiants de l'IUT / ENSAI
                </p>
            </div>
        </div>
    </div>
</div>

<%@ include file="inc/footer.jsp" %>