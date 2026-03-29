package com.gestion.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet gérant la déconnexion de l'utilisateur.
 * Elle invalide la session actuelle et redirige vers l'accueil.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // On récupère la session actuelle sans en créer une nouvelle (false)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // On vide et on détruit la session
            session.invalidate();
        }
        
        // Redirection vers la page d'accueil publique
        response.sendRedirect("accueil");
    }

    // On redirige aussi le POST vers le GET par sécurité
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}