package com.gestion.web;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.gestion.dao.DocumentDAO;
import com.gestion.model.*;

@WebServlet("/modifier")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // Limite à 5Mo
public class ModifierLivreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DocumentDAO docDAO = new DocumentDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        
        if (isbn == null || isbn.isEmpty()) {
            response.sendRedirect("admin");
            return;
        }

        Document doc = docDAO.trouverParIsbn(isbn);
        
        if (doc != null) {
            request.setAttribute("doc", doc);
            // Vérifie bien que ton fichier s'appelle form_modifier.jsp ou modifier_livre.jsp
            request.getRequestDispatcher("WEB-INF/form_modifier.jsp").forward(request, response);
        } else {
            response.sendRedirect("admin");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        try {
            String isbn = request.getParameter("isbn");
            Document doc = new Document();
            
            // 1. Récupération des données textuelles
            doc.setIsbn(isbn);
            doc.setTitre(request.getParameter("titre"));
            doc.setDescription(request.getParameter("description"));
            doc.setDomaine(request.getParameter("domaine"));
            
            // --- AJOUT : RÉCUPÉRATION DU CHEMIN PDF ---
            String urlDoc = request.getParameter("urlDocument");
            doc.setUrlDocument(urlDoc); 

            // 2. Gestion de l'image (Vignette)
            Part part = request.getPart("file_vignette");
            if (part != null && part.getSize() > 0) {
                String fileName = isbn + ".jpg";
                String uploadPath = getServletContext().getRealPath("/") + "img" + File.separator + "vignettes";
                
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                
                part.write(uploadPath + File.separator + fileName);
                doc.setVignette(fileName);
            } else {
                // On garde l'ancienne vignette si aucune nouvelle n'est choisie
                doc.setVignette(request.getParameter("oldVignette"));
            }

            // 3. Objets liés (Auteur et Maison d'Édition)
            Auteur a = new Auteur(); 
            a.setNom(request.getParameter("nomAuteur"));
            doc.setAuteur(a);
            
            MaisonEdition m = new MaisonEdition(); 
            m.setNomMaison(request.getParameter("nomMaison"));
            doc.setMaisonEdition(m);

            TypeDocument t = new TypeDocument(); 
            t.setId(1); 
            doc.setTypeDocument(t);

            // 4. Exécution de la modification via le DAO
            docDAO.modifierDocument(doc);
            
            // Redirection vers le dashboard après succès
            response.sendRedirect("admin");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Erreur lors de la modif : " + e.getMessage());
            response.sendRedirect("admin");
        }
    }
}