package com.gestion.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestion.dao.DocumentDAO;
import com.gestion.model.Document;

/**
 * Servlet permettant de lire un document PDF stocké sur le serveur.
 */
@WebServlet("/lire")
public class LireDocumentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DocumentDAO docDAO = new DocumentDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Récupérer l'ISBN du document demandé
        String isbn = request.getParameter("isbn");
        
        if (isbn == null || isbn.isEmpty()) {
            response.sendRedirect("accueil");
            return;
        }

        // 2. Chercher le document en base de données pour avoir son URL
        Document doc = docDAO.trouverParIsbn(isbn);

        if (doc != null && doc.getUrlDocument() != null && !doc.getUrlDocument().isEmpty()) {
            File pdfFile = new File(doc.getUrlDocument());

            if (pdfFile.exists()) {
                // 3. Configurer la réponse HTTP pour un PDF
                response.setContentType("application/pdf");
                response.setContentLength((int) pdfFile.length());
                
                // "inline" pour ouvrir dans le navigateur, "attachment" pour télécharger
                response.setHeader("Content-Disposition", "inline; filename=\"" + pdfFile.getName() + "\"");

                // 4. Envoyer le contenu du fichier au navigateur (Flux binaire)
                try (FileInputStream fis = new FileInputStream(pdfFile);
                     OutputStream os = response.getOutputStream()) {
                    
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                    os.flush();
                }
            } else {
                // Erreur si le fichier physique n'est pas trouvé sur le disque
                response.setContentType("text/html");
                response.getWriter().println("<h3>Erreur : Le fichier PDF est introuvable au chemin : " + doc.getUrlDocument() + "</h3>");
                response.getWriter().println("<a href='accueil'>Retour à l'accueil</a>");
            }
        } else {
            response.sendRedirect("accueil");
        }
    }
}