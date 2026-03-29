<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../inc/header.jsp" %>

<div style="display: flex; flex-direction: column; align-items: center; padding: 40px; background: #fdfdfd;">
    
    <%-- Affichage des erreurs --%>
    <c:if test="${not empty sessionScope.errorMsg or not empty requestScope.errorMsg}">
        <div style="background: #ffebee; color: #c62828; padding: 20px; border-radius: 10px; border: 1px solid #ef9a9a; margin-bottom: 20px; width: 100%; max-width: 600px; font-family: monospace;">
            <strong>⚠️ ERREUR :</strong> ${sessionScope.errorMsg} ${requestScope.errorMsg}
        </div>
        <% session.removeAttribute("errorMsg"); %>
    </c:if>

    <div style="background: white; padding: 40px; border-radius: 20px; box-shadow: 0 15px 35px rgba(0,0,0,0.05); width: 650px; border-top: 6px solid #f06292;">
        
        <h2 style="font-family: 'Georgia', serif; text-align: center; color: #1a1a1a; margin-bottom: 30px;">
            Nouvel Ouvrage en Bibliothèque
        </h2>
        
        <form action="ajouter-livre" method="post" enctype="multipart/form-data">
            
            <%-- Ligne 1 : ISBN & Titre --%>
            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 20px;">
                <div>
                    <label style="display:block; font-weight:bold; color:#f06292; font-size:0.75rem; text-transform: uppercase; margin-bottom: 5px;">ISBN</label>
                    <input type="text" name="isbn" placeholder="Code unique" required style="width:100%; padding:12px; border:1px solid #fce4ec; border-radius:10px; outline: none;">
                </div>
                <div>
                    <label style="display:block; font-weight:bold; color:#f06292; font-size:0.75rem; text-transform: uppercase; margin-bottom: 5px;">Titre de l'ouvrage</label>
                    <input type="text" name="titre" placeholder="Titre complet" required style="width:100%; padding:12px; border:1px solid #fce4ec; border-radius:10px; outline: none;">
                </div>
            </div>

            <%-- Ligne 2 : Auteur & Éditeur --%>
            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 20px;">
                <div>
                    <label style="display:block; font-weight:bold; color:#f06292; font-size:0.75rem; text-transform: uppercase; margin-bottom: 5px;">Nom de l'Auteur</label>
                    <input type="text" name="nomAuteur" placeholder="Ex: Marcel Proust" required style="width:100%; padding:12px; border:1px solid #fce4ec; border-radius:10px; outline: none;">
                </div>
                <div>
                    <label style="display:block; font-weight:bold; color:#f06292; font-size:0.75rem; text-transform: uppercase; margin-bottom: 5px;">Maison d'Édition</label>
                    <input type="text" name="nomMaison" placeholder="Ex: Gallimard" required style="width:100%; padding:12px; border:1px solid #fce4ec; border-radius:10px; outline: none;">
                </div>
            </div>

            <div style="margin-bottom: 20px;">
                <label style="display:block; font-weight:bold; color:#f06292; font-size:0.75rem; text-transform: uppercase; margin-bottom: 5px;">Description / Résumé</label>
                <textarea name="description" rows="3" placeholder="Bref résumé de l'ouvrage..." style="width:100%; padding:12px; border:1px solid #fce4ec; border-radius:10px; outline: none; font-family: sans-serif; resize: vertical;"></textarea>
            </div>

            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 20px;">
                <div>
                    <label style="display:block; font-weight:bold; color:#f06292; font-size:0.75rem; text-transform: uppercase; margin-bottom: 5px;">Domaine</label>
                    <select name="domaine" required style="width:100%; padding:12px; border:1px solid #fce4ec; border-radius:10px; background: white; cursor: pointer;">
                        <option value="">-- Choisir un domaine --</option>
                        <optgroup label="Sciences & Tech">
                            <option>Informatique</option>
                            <option>Réseaux & Sécurité</option>
                            <option>Mathématiques</option>
                        </optgroup>
                        <optgroup label="Lettres & Humaines">
                            <option>Littérature Classique</option>
                            <option>Philosophie</option>
                            <option>Histoire / Géographie</option>
                        </optgroup>
                        <optgroup label="Droit & Gestion">
                            <option>Droit Civil</option>
                            <option>Économie</option>
                            <option>Management / RH</option>
                        </optgroup>
                        <optgroup label="Art & Culture">
                            <option>Architecture</option>
                            <option>Musique</option>
                            <option>Bandes Dessinées</option>
                        </optgroup>
                    </select>
                </div>
                <div>
                    <label style="display:block; font-weight:bold; color:#f06292; font-size:0.75rem; text-transform: uppercase; margin-bottom: 5px;">Quantité en Stock</label>
                    <input type="number" name="quantite" value="1" min="1" required style="width:100%; padding:12px; border:1px solid #fce4ec; border-radius:10px; outline: none;">
                </div>
            </div>

            <%-- --- NOUVEAU : CHAMP URL DOCUMENT --- --%>
            <div style="margin-bottom: 20px;">
                <label style="display:block; font-weight:bold; color:#f06292; font-size:0.75rem; text-transform: uppercase; margin-bottom: 5px;">
                    Chemin complet du fichier PDF
                </label>
                <input type="text" name="urlDocument" 
                       placeholder="Ex: C:/Users/Admin/Desktop/cours_reseau.pdf" 
                       style="width:100%; padding:12px; border:1px solid #fce4ec; border-radius:10px; outline: none; background: #fffcfd;">
                <p style="font-size: 0.7rem; color: #888; margin-top: 5px;">
                    * Ce chemin permettra l'ouverture directe du document en bibliothèque.
                </p>
            </div>

            <%-- Champ Vignette --%>
            <div style="margin-bottom: 30px;">
                <label style="display:block; font-weight:bold; color:#f06292; font-size:0.75rem; text-transform: uppercase; margin-bottom: 5px;">Image de couverture (Vignette)</label>
                <div style="position: relative; border: 2px dashed #fce4ec; padding: 20px; border-radius: 12px; text-align: center; background: #fff9fb;">
                    <input type="file" name="file_vignette" accept="image/*" required style="opacity: 0; position: absolute; width: 100%; height: 100%; top:0; left:0; cursor: pointer;">
                    <span style="color: #f06292; font-size: 0.8rem;">📸 Cliquer pour importer la photo</span>
                </div>
            </div>

            <button type="submit" style="width:100%; background:#1a1a1a; color:white; border:none; padding:18px; border-radius:35px; font-weight:bold; cursor:pointer; text-transform: uppercase; letter-spacing: 1px; transition: 0.3s; box-shadow: 0 5px 15px rgba(240, 98, 146, 0.3);">
                Enregistrer l'Ouvrage
            </button>
            
            <div style="text-align: center; margin-top: 20px;">
                <a href="admin" style="color: #999; text-decoration: none; font-size: 0.75rem; font-weight: 600;">Annuler l'opération</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="../inc/footer.jsp" %>