package com.gestion.web;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.gestion.dao.DocumentDAO;
import com.gestion.model.Auteur;
import com.gestion.model.Document;
import com.gestion.model.MaisonEdition;
import com.gestion.model.TypeDocument;

@WebServlet("/ajouter-livre")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,  // 1MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 15      // 15MB
)
public class AjouterLivreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DocumentDAO docDAO;

    public void init() {
        docDAO = new DocumentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Vérification du rôle admin
        if ("ADMIN".equals(session.getAttribute("role"))) {
            request.getRequestDispatcher("WEB-INF/form_ouvrage.jsp").forward(request, response);
        } else {
            response.sendRedirect("admin");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            // 1. Récupération des données du formulaire
            String isbn = request.getParameter("isbn");
            String titre = request.getParameter("titre");
            String description = request.getParameter("description");
            String domaine = request.getParameter("domaine");
            
            // --- RÉCUPÉRATION DU CHEMIN PDF ---
            String urlDoc = request.getParameter("urlDocument"); 

            // 2. Gestion de l'image (Vignette)
            Part part = request.getPart("file_vignette");
            String fileName = isbn + ".jpg"; 
            String uploadPath = getServletContext().getRealPath("/") + "img" + File.separator + "vignettes";
            
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            
            if (part != null && part.getSize() > 0) {
                part.write(uploadPath + File.separator + fileName);
            } else {
                fileName = "default.jpg";
            }

            // 3. Création et remplissage de l'objet Document
            Document doc = new Document();
            doc.setIsbn(isbn);
            doc.setTitre(titre);
            doc.setDescription(description);
            doc.setDomaine(domaine);
            doc.setVignette(fileName);
            
            // --- ENREGISTREMENT DE L'URL DANS L'OBJET ---
            doc.setUrlDocument(urlDoc); 

            // --- GESTION DE L'AUTEUR ---
            String nomAut = request.getParameter("nomAuteur");
            Auteur a = new Auteur();
            a.setNom(nomAut);
            doc.setAuteur(a); 

            // --- GESTION DE L'ÉDITEUR ---
            String nomMai = request.getParameter("nomMaison");
            MaisonEdition m = new MaisonEdition();
            m.setNomMaison(nomMai);
            doc.setMaisonEdition(m);

            // --- GESTION DE LA QUANTITÉ ---
            try {
                String qteStr = request.getParameter("quantite");
                int qte = (qteStr != null) ? Integer.parseInt(qteStr) : 1;
                TypeDocument t = new TypeDocument();
                t.setId(1); 
                t.setDesignation("Stock: " + qte); 
                doc.setTypeDocument(t);
            } catch (NumberFormatException e) {
                throw new Exception("La quantité doit être un nombre entier valide.");
            }

            // 4. Envoi au DAO pour insertion en base de données
            docDAO.ajouterDocument(doc);
            
            // Succès : retour au dashboard
            response.sendRedirect("admin");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Erreur lors de l'ajout : " + e.getMessage());
            response.sendRedirect("ajouter-livre"); 
        }
    }
}