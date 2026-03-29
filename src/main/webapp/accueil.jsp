<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="inc/header.jsp" %>

<style>
    /* ... (Tes styles CSS restent les mêmes, je les omets pour la clarté) ... */
    .card-livre { background: rgba(255, 255, 255, 0.85); backdrop-filter: blur(10px); border-radius: 20px; box-shadow: 0 8px 25px rgba(0,0,0,0.05); transition: all 0.4s ease-in-out; overflow: hidden; display: flex; flex-direction: column; border: 1px solid rgba(255, 255, 255, 0.4); height: 100%; }
    .card-livre:hover { transform: translateY(-10px); box-shadow: 0 15px 35px rgba(240, 98, 146, 0.2); background: rgba(255, 255, 255, 0.98); border-color: #fce4ec; }
    .btn-consulter { background-color: #1a1a1a; color: #ffffff; padding: 10px 25px; text-decoration: none; border-radius: 30px; font-size: 0.85rem; font-weight: 600; transition: all 0.3s ease; display: inline-block; }
    .btn-consulter:hover { background-color: #f06292; transform: scale(1.05); color: white; }
    .category-tag { background: rgba(240, 98, 146, 0.1); color: #f06292; padding: 5px 15px; font-size: 0.75rem; font-weight: bold; border-radius: 20px; }
    .select-domaine { padding: 12px 25px; border-radius: 30px; border: 2px solid #fce4ec; background: white; font-weight: 600; color: #1a1a1a; cursor: pointer; outline: none; transition: 0.3s; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 100%; max-width: 400px; }
    .img-container { height: 250px; overflow: hidden; display: flex; align-items: center; justify-content: center; background: linear-gradient(to bottom, rgba(252, 228, 236, 0.6), rgba(255, 255, 255, 0.1)); }
</style>

<div style="text-align: center; margin-bottom: 50px; padding-top: 20px;">
    <h1 style="color: #1a1a1a; font-size: 3.2rem; font-family: 'Georgia', serif; font-weight: bold; margin-bottom: 15px;">
        MaBiblioTech
    </h1>
    
    <div style="background: rgba(255, 255, 255, 0.45); backdrop-filter: blur(8px); display: inline-block; padding: 20px 40px; border-radius: 20px; border: 1px solid rgba(255,255,255,0.3);">
        <p style="color: #000000; font-size: 1.25rem; font-style: italic; font-weight: 600;">
            "La lecture est une amitié." — <span style="color: #f06292; font-weight: 800;">Marcel Proust</span>
        </p>
    </div>

    <div style="margin-top: 40px;">
        <form action="accueil" method="get">
            <select name="domaine" onchange="this.form.submit()" class="select-domaine">
                <option value="">Toutes les Collections</option>
                
                <optgroup label="Sciences & Tech">
                    <option value="Informatique" ${param.domaine == 'Informatique' ? 'selected' : ''}>Informatique</option>
                    <option value="Réseaux & Sécurité" ${param.domaine == 'Réseaux & Sécurité' ? 'selected' : ''}>Réseaux & Sécurité</option>
                    <option value="Mathématiques" ${param.domaine == 'Mathématiques' ? 'selected' : ''}>Mathématiques</option>
                    <option value="Physique / Chimie" ${param.domaine == 'Physique / Chimie' ? 'selected' : ''}>Physique / Chimie</option>
                    <option value="Biologie / Médecine" ${param.domaine == 'Biologie / Médecine' ? 'selected' : ''}>Biologie / Médecine</option>
                </optgroup>

                <optgroup label="Lettres & Sciences Humaines">
                    <option value="Littérature Classique" ${param.domaine == 'Littérature Classique' ? 'selected' : ''}>Littérature Classique</option>
                    <option value="Philosophie" ${param.domaine == 'Philosophie' ? 'selected' : ''}>Philosophie</option>
                    <option value="Histoire / Géographie" ${param.domaine == 'Histoire / Géographie' ? 'selected' : ''}>Histoire / Géographie</option>
                    <option value="Psychologie" ${param.domaine == 'Psychologie' ? 'selected' : ''}>Psychologie</option>
                    <option value="Sociologie" ${param.domaine == 'Sociologie' ? 'selected' : ''}>Sociologie</option>
                    <option value="Langues" ${param.domaine == 'Langues' ? 'selected' : ''}>Langues</option>
                </optgroup>

                <optgroup label="Droit & Gestion">
                    <option value="Droit Civil" ${param.domaine == 'Droit Civil' ? 'selected' : ''}>Droit Civil</option>
                    <option value="Droit Public" ${param.domaine == 'Droit Public' ? 'selected' : ''}>Droit Public</option>
                    <option value="Économie" ${param.domaine == 'Économie' ? 'selected' : ''}>Économie</option>
                    <option value="Management / RH" ${param.domaine == 'Management / RH' ? 'selected' : ''}>Management / RH</option>
                    <option value="Comptabilité" ${param.domaine == 'Comptabilité' ? 'selected' : ''}>Comptabilité</option>
                </optgroup>

                <optgroup label="Art & Culture">
                    <option value="Beaux-Arts" ${param.domaine == 'Beaux-Arts' ? 'selected' : ''}>Beaux-Arts</option>
                    <option value="Architecture" ${param.domaine == 'Architecture' ? 'selected' : ''}>Architecture</option>
                    <option value="Musique" ${param.domaine == 'Musique' ? 'selected' : ''}>Musique</option>
                    <option value="Bandes Dessinées" ${param.domaine == 'Bandes Dessinées' ? 'selected' : ''}>Bandes Dessinées</option>
                    <option value="Religion / Spiritualité" ${param.domaine == 'Religion / Spiritualité' ? 'selected' : ''}>Religion / Spiritualité</option>
                </optgroup>
            </select>
        </form>
    </div>
</div>

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 35px; padding: 0 20px 50px 20px;">
    <c:forEach var="doc" items="${documents}">
        <div class="card-livre">
            <div class="img-container">
                <c:choose>
                    <c:when test="${not empty doc.vignette}">
                        <img src="${pageContext.request.contextPath}/img/vignettes/${doc.vignette}" 
                             alt="${doc.titre}" 
                             style="width: 100%; height: 100%; object-fit: cover;">
                    </c:when>
                    <c:otherwise>
                        <span style="font-size: 5rem; opacity: 0.7;">📖</span>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <div style="padding: 25px; flex-grow: 1; display: flex; flex-direction: column;">
                <div style="margin-bottom: 15px;">
                    <span class="category-tag">${doc.domaine}</span>
                </div>
                
                <h3 style="margin: 0 0 10px 0; color: #1a1a1a; font-size: 1.3rem; font-weight: 700;">
                    ${doc.titre}
                </h3>
                
                <p style="color: #444; font-size: 0.9rem; margin-bottom: 25px; flex-grow: 1; line-height: 1.5;">
                    ${doc.description}
                </p>
                
                <div style="display: flex; justify-content: space-between; align-items: center; padding-top: 15px; border-top: 1px solid #fce4ec;">
                    <span style="font-size: 0.8rem; color: #666;">Par <b>${doc.auteur.nom}</b></span>
                    <a href="details?isbn=${doc.isbn}" class="btn-consulter">Consulter</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<%@ include file="inc/footer.jsp" %>