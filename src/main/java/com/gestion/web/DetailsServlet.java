package com.gestion.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gestion.dao.DocumentDAO;
import com.gestion.model.Document;

@WebServlet("/details")
public class DetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DocumentDAO docDAO;

    public void init() {
        docDAO = new DocumentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Récupérer l'ISBN depuis le lien
        String isbn = request.getParameter("isbn");

        if (isbn == null || isbn.isEmpty()) {
            response.sendRedirect("accueil");
            return;
        }

        try {
            // 2. Chercher le document complet dans la BD
            Document doc = docDAO.trouverParIsbn(isbn);

            if (doc != null) {
                // 3. Envoyer l'objet à la JSP
                request.setAttribute("doc", doc);
                request.getRequestDispatcher("details.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Livre introuvable");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}