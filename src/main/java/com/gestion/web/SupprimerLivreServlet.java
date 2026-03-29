package com.gestion.web;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gestion.dao.DocumentDAO;

@WebServlet("/supprimer")
public class SupprimerLivreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String isbn = request.getParameter("isbn");
        DocumentDAO dao = new DocumentDAO();
        
        if (isbn != null && !isbn.isEmpty()) {
            try {
                // 1. Suppression physique de l'image (Optionnel mais propre pour ton stage)
                String uploadPath = getServletContext().getRealPath("/") + "img" + File.separator + "vignettes";
                File file = new File(uploadPath + File.separator + isbn + ".jpg");
                if (file.exists()) {
                    file.delete();
                }

                // 2. Suppression dans la base de données
                dao.supprimerDocument(isbn);
                
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("errorMsg", "Erreur lors de la suppression : " + e.getMessage());
            }
        }
        
        // Redirection vers le dashboard admin
        response.sendRedirect("admin");
    }
}