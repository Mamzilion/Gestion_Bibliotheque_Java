<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../inc/header.jsp" %>

<style>
    /* Panneau d'administration */
    .admin-container {
        background: rgba(255, 255, 255, 0.9);
        backdrop-filter: blur(10px);
        border-radius: 20px;
        padding: 40px;
        box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        margin-top: 20px;
        border: 1px solid rgba(252, 228, 236, 0.5);
    }
    /* Styles pour la barre de recherche et les boutons */
    .admin-controls {
        display: flex; 
        justify-content: space-between; 
        align-items: center; 
        margin-bottom: 30px;
        gap: 20px;
        flex-wrap: wrap;
    }
    .search-box {
        position: relative;
        flex-grow: 1;
        max-width: 400px;
    }
    .search-box input {
        width: 100%;
        padding: 12px 15px 12px 40px;
        border-radius: 25px;
        border: 1px solid #ddd;
        outline: none;
        font-size: 0.9rem;
        transition: 0.3s;
    }
    .search-box input:focus { border-color: #f06292; box-shadow: 0 0 8px rgba(240, 98, 146, 0.2); }
    .search-icon {
        position: absolute;
        left: 15px;
        top: 12px;
        color: #aaa;
    }
    /* Table stylisée */
    .admin-table {
        width: 100%;
        border-collapse: separate;
        border-spacing: 0 10px;
        margin-top: 20px;
    }
    .admin-table th {
        background-color: #1a1a1a;
        color: white;
        padding: 15px;
        text-transform: uppercase;
        font-size: 0.8rem;
        letter-spacing: 1px;
        text-align: left;
    }
    .admin-table td {
        background: white;
        padding: 15px;
        color: #333;
        border-top: 1px solid #fce4ec;
        border-bottom: 1px solid #fce4ec;
    }
    .admin-table tr td:first-child { border-left: 1px solid #fce4ec; border-radius: 10px 0 0 10px; }
    .admin-table tr td:last-child { border-right: 1px solid #fce4ec; border-radius: 0 10px 10px 0; }
    /* Boutons */
    .btn-add {
        background-color: #f06292;
        color: white;
        padding: 12px 25px;
        text-decoration: none;
        border-radius: 30px;
        font-weight: bold;
        display: inline-block;
        transition: 0.3s;
        box-shadow: 0 4px 15px rgba(240, 98, 146, 0.3);
    }
    .btn-add:hover { transform: scale(1.05); background-color: #1a1a1a; }
    .btn-logout {
        background-color: #1a1a1a;
        color: white;
        padding: 10px 20px;
        text-decoration: none;
        border-radius: 25px;
        font-size: 0.85rem;
        font-weight: bold;
        transition: 0.3s;
    }
    .btn-logout:hover { background-color: #e91e63; }
    .action-link { text-decoration: none; font-weight: bold; margin-right: 10px; font-size: 0.9rem; }
    .edit { color: #1a1a1a; }
    .delete { color: #e91e63; }
</style>

<div class="admin-container">
    
    <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 10px;">
        <h2 style="color: #1a1a1a; font-family: 'Georgia', serif; font-size: 2rem; margin: 0;">
            Tableau de Bord <span style="color: #f06292;">Admin</span>
        </h2>
        <a href="${pageContext.request.contextPath}/logout" class="btn-logout" onclick="return confirm('Voulez-vous vous déconnecter ?');">Déconnexion</a>
    </div>

    <div class="admin-controls">
        <div class="search-box">
            <span class="search-icon">🔍</span>
            <input type="text" id="searchInput" placeholder="Rechercher un ouvrage (ISBN, titre, auteur)...">
        </div>
        <a href="ajouter-livre" class="btn-add">+ Ajouter un Document</a>
    </div>

    <table class="admin-table" id="tableOuvrages">
        <thead>
            <tr>
                <th>ISBN</th>
                <th>Titre</th>
                <th>Auteur</th>
                <th>Édition</th>
                <th>Domaine</th>
                <th style="text-align: center;">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="doc" items="${documents}">
                <tr>
                    <td style="font-weight: bold;">${doc.isbn}</td>
                    <td>${doc.titre}</td>
                    <td>${doc.auteur.nom}</td>
                    <td>${doc.maisonEdition.nomMaison}</td>
                    <td>
                        <span class="category-tag" style="background: #fce4ec; color: #f06292; padding: 4px 10px; border-radius: 15px; font-size: 0.75rem;">
                            ${doc.domaine}
                        </span>
                    </td>
                    <td style="text-align: center;">
                        <a href="modifier?isbn=${doc.isbn}" class="action-link edit">Modifier</a>
                        <a href="supprimer?isbn=${doc.isbn}" class="action-link delete" onclick="return confirm('Supprimer cet ouvrage ?');">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div style="margin-top: 30px; border-top: 1px solid #fce4ec; padding-top: 20px;">
        <a href="accueil" style="color: #666; text-decoration: none; font-weight: 500;">&larr; Retour à la bibliothèque publique</a>
    </div>
</div>

<script>
// Logique de recherche instantanée
document.getElementById('searchInput').addEventListener('keyup', function() {
    let filter = this.value.toLowerCase();
    let rows = document.querySelectorAll('#tableOuvrages tbody tr');
    rows.forEach(row => {
        let text = row.textContent.toLowerCase();
        row.style.display = text.includes(filter) ? '' : 'none';
    });
});
</script>

<%@ include file="../inc/footer.jsp" %>