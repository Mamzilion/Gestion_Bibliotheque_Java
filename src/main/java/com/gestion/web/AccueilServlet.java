package com.gestion.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestion.dao.DocumentDAO;
import com.gestion.model.Document;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DocumentDAO docDAO;

    public void init() {
        docDAO = new DocumentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. On récupère le domaine depuis l'URL (ex: ?domaine=Sciences)
        String domaine = request.getParameter("domaine");
        List<Document> liste;

        try {
            // 2. LOGIQUE DE FILTRAGE : On utilise la nouvelle méthode du DAO
            if (domaine != null && !domaine.isEmpty()) {
                // On appelle la méthode avec le WHERE SQL
                liste = docDAO.listerParDomaine(domaine); 
            } else {
                // Si aucun domaine n'est choisi (ou "Toutes les Collections")
                liste = docDAO.listerDocuments();
            }

            // 3. On transmet la liste à la JSP
            request.setAttribute("documents", liste);

            // 4. On affiche la page accueil.jsp
            request.getRequestDispatcher("accueil.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur de chargement des documents.");
        }
    }
}