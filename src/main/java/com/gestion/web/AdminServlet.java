package com.gestion.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Tes DAOs
import com.gestion.dao.DocumentDAO;
import com.gestion.dao.AuteurDAO;
import com.gestion.dao.MaisonEditionDAO;
import com.gestion.dao.TypeDocumentDAO;

// Tes Modèles
import com.gestion.model.Document;
import com.gestion.model.Auteur;
import com.gestion.model.MaisonEdition;
import com.gestion.model.TypeDocument;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DocumentDAO docDAO;
    private AuteurDAO autDAO;
    private MaisonEditionDAO maisonDAO;
    private TypeDocumentDAO typeDAO;

    public void init() {
        docDAO = new DocumentDAO();
        autDAO = new AuteurDAO();
        maisonDAO = new MaisonEditionDAO();
        typeDAO = new TypeDocumentDAO();
    }

    /**
     * Gère l'affichage du Dashboard Admin
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // On récupère la session sans en créer une nouvelle
        HttpSession session = request.getSession(false);
        
        // Vérification de sécurité : on cherche l'attribut "adminConnecte"
        if (session != null && session.getAttribute("adminConnecte") != null) {
            
            // 1. On récupère les données nécessaires
            List<Document> listeDocs = docDAO.listerDocuments();
            List<Auteur> auteurs = autDAO.listerTout();
            List<MaisonEdition> maisons = maisonDAO.listerTout();
            List<TypeDocument> types = typeDAO.listerTout();
            
            // 2. On met tout en attribut de requête pour la JSP
            request.setAttribute("documents", listeDocs);
            request.setAttribute("auteurs", auteurs);
            request.setAttribute("maisons", maisons);
            request.setAttribute("typesDoc", types);
            
            // 3. On affiche le Dashboard (situé dans WEB-INF pour plus de sécurité)
            request.getRequestDispatcher("WEB-INF/admin_dashboard.jsp").forward(request, response);
            
        } else {
            // Pas de session valide -> Redirection vers la page de login
            request.getRequestDispatcher("login_admin.jsp").forward(request, response);
        }
    }

    /**
     * Gère la soumission du formulaire de connexion
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");

        // Vérification des identifiants (admin / admin123)
        if ("admin".equals(user) && "admin123".equals(pass)) {
            
            // On crée la session et on ajoute le badge de connexion
            HttpSession session = request.getSession(true); 
            session.setAttribute("adminConnecte", true); 
            session.setAttribute("role", "ADMIN"); 

            // On redirige vers la méthode doGet de cette même servlet
            response.sendRedirect("admin"); 
            
        } else {
            // Identifiants faux : retour au login avec message d'erreur
            request.setAttribute("erreur", "Identifiants incorrects. Accès refusé.");
            request.getRequestDispatcher("login_admin.jsp").forward(request, response);
        }
    }
}