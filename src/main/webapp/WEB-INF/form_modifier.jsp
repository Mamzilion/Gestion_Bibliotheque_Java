<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

<style>
    .form-container {
        background: rgba(255, 255, 255, 0.95);
        border-radius: 20px;
        padding: 40px;
        box-shadow: 0 15px 35px rgba(0,0,0,0.1);
        max-width: 800px;
        margin: 30px auto;
        border: 1px solid #fce4ec;
    }
    .form-group { margin-bottom: 20px; }
    label { display: block; margin-bottom: 8px; font-weight: 600; color: #1a1a1a; }
    input[type="text"], textarea, select {
        width: 100%;
        padding: 12px;
        border: 1px solid #ddd;
        border-radius: 10px;
        font-size: 1rem;
        outline: none;
    }
    input:focus, textarea:focus { border-color: #f06292; }
    input[readonly] { background-color: #f5f5f5; cursor: not-allowed; }
    .btn-submit {
        background: #f06292;
        color: white;
        border: none;
        padding: 15px 30px;
        border-radius: 30px;
        font-weight: bold;
        cursor: pointer;
        transition: 0.3s;
        width: 100%;
        text-transform: uppercase;
        letter-spacing: 1px;
    }
    .btn-submit:hover { background: #1a1a1a; transform: translateY(-2px); }
    .current-img {
        width: 100px;
        border-radius: 5px;
        margin-top: 10px;
        display: block;
        border: 2px solid #fce4ec;
    }
    .info-help {
        font-size: 0.75rem;
        color: #f06292;
        margin-top: 5px;
        font-style: italic;
    }
</style>

<div class="form-container">
    <h2 style="text-align: center; margin-bottom: 30px; font-family: 'Georgia', serif;">
        Modifier l'Ouvrage : <span style="color: #f06292;">${doc.titre}</span>
    </h2>

    <form action="modifier" method="post" enctype="multipart/form-data">
        
        <%-- Champ caché pour conserver le nom de l'ancienne image si on n'en télécharge pas de nouvelle --%>
        <input type="hidden" name="oldVignette" value="${doc.vignette}">

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
            <div class="form-group">
                <label>ISBN (Non modifiable)</label>
                <input type="text" name="isbn" value="${doc.isbn}" readonly>
            </div>
            <div class="form-group">
                <label>Titre du document</label>
                <input type="text" name="titre" value="${doc.titre}" required>
            </div>
        </div>

        <div class="form-group">
            <label>Description / Résumé</label>
            <textarea name="description" rows="4" required>${doc.description}</textarea>
        </div>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
            <div class="form-group">
                <label>Auteur</label>
                <input type="text" name="nomAuteur" value="${doc.auteur.nom}" required>
            </div>
            <div class="form-group">
                <label>Maison d'Édition</label>
                <input type="text" name="nomMaison" value="${doc.maisonEdition.nomMaison}" required>
            </div>
        </div>

        <div class="form-group">
            <label>Domaine</label>
            <select name="domaine">
                <option value="Informatique" ${doc.domaine == 'Informatique' ? 'selected' : ''}>Informatique</option>
                <option value="Réseaux & Sécurité" ${doc.domaine == 'Réseaux & Sécurité' ? 'selected' : ''}>Réseaux & Sécurité</option>
                <option value="Développement Mobile" ${doc.domaine == 'Développement Mobile' ? 'selected' : ''}>Développement Mobile</option>
                <option value="Management de Projet" ${doc.domaine == 'Management de Projet' ? 'selected' : ''}>Management de Projet</option>
            </select>
        </div>

        <%-- --- AJOUT : CHEMIN DU DOCUMENT PDF --- --%>
        <div class="form-group" style="padding: 15px; background: #fff9fb; border-radius: 10px; border: 1px dashed #fce4ec;">
            <label style="color: #f06292;">Lien vers le fichier PDF (Lecture)</label>
            <input type="text" name="urlDocument" value="${doc.urlDocument}" 
                   placeholder="Ex: C:/MesDocuments/Livre.pdf">
            <p class="info-help">💡 Ce chemin permet d'ouvrir le document directement via le bouton "Lire".</p>
        </div>

        <div class="form-group">
            <label>Changer la Vignette (Laisser vide pour garder l'ancienne)</label>
            <input type="file" name="file_vignette" accept="image/*" style="border: none; padding: 0;">
            
            <c:if test="${not empty doc.vignette}">
                <p style="font-size: 0.8rem; color: #666; margin-top: 15px;">Image actuelle :</p>
                <img src="${pageContext.request.contextPath}/img/vignettes/${doc.vignette}" class="current-img" alt="Couverture">
            </c:if>
        </div>

        <button type="submit" class="btn-submit">Mettre à jour l'ouvrage</button>
    </form>

    <div style="text-align: center; margin-top: 20px;">
        <a href="admin" style="color: #666; text-decoration: none; font-size: 0.85rem;">&larr; Annuler et retourner au Dashboard</a>
    </div>
</div>

<%@ include file="../inc/footer.jsp" %>