<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="inc/header.jsp" %>

<div style="display: flex; justify-content: center; align-items: center; min-height: 70vh;">
    <div style="background: rgba(255, 255, 255, 0.95); padding: 40px; border-radius: 15px; box-shadow: 0 15px 35px rgba(0,0,0,0.1); width: 350px; border-top: 5px solid #f06292; backdrop-filter: blur(10px);">
        
        <div style="text-align: center; margin-bottom: 30px;">
            <h2 style="font-family: 'Georgia', serif; margin: 0; color: #1a1a1a;">MaBiblioTech</h2>
            <small style="color: #f06292; text-transform: uppercase; letter-spacing: 2px; font-weight: bold; font-size: 0.7rem;">Espace Privé</small>
        </div>

        <form action="admin" method="post">
            <div style="margin-bottom: 20px;">
                <label style="display: block; font-size: 0.75rem; font-weight: bold; margin-bottom: 8px; color: #555; text-transform: uppercase;">Identifiant</label>
                <input type="text" name="user" required 
                       style="width: 100%; padding: 12px; border: 1px solid #fce4ec; border-radius: 30px; box-sizing: border-box; outline: none; transition: 0.3s;"
                       onfocus="this.style.borderColor='#f06292'">
            </div>

            <div style="margin-bottom: 25px;">
                <label style="display: block; font-size: 0.75rem; font-weight: bold; margin-bottom: 8px; color: #555; text-transform: uppercase;">Mot de passe</label>
                <input type="password" name="pass" required 
                       style="width: 100%; padding: 12px; border: 1px solid #fce4ec; border-radius: 30px; box-sizing: border-box; outline: none; transition: 0.3s;"
                       onfocus="this.style.borderColor='#f06292'">
            </div>

            <button type="submit" 
                    style="width: 100%; background: #1a1a1a; color: white; border: none; padding: 12px; border-radius: 30px; font-weight: bold; cursor: pointer; transition: 0.3s; text-transform: uppercase; letter-spacing: 1px;">
                Se connecter
            </button>
        </form>

        <%-- Affichage de l'erreur si les identifiants sont faux --%>
        <c:if test="${not empty erreur}">
            <div style="color: #e91e63; font-size: 0.8rem; text-align: center; margin-top: 15px; background: #fdf2f5; padding: 8px; border-radius: 10px;">
                ${erreur}
            </div>
        </c:if>
    </div>
</div>

<%@ include file="inc/footer.jsp" %>