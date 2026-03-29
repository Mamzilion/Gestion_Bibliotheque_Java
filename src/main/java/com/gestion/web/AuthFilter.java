package com.gestion.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/admin", "/modifier", "/supprimer", "/ajouter-livre"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // 1. Vérification de la session admin
        boolean estConnecte = (session != null && session.getAttribute("adminConnecte") != null);

        // 2. Analyse précise de l'URI demandée
        String uri = req.getRequestURI();
        
        // On vérifie si l'utilisateur essaie d'accéder au login (Servlet ou JSP)
        // Cela inclut le cas où AdminServlet fait un forward vers login_admin.jsp
        boolean pageLogin = uri.endsWith("/admin") || uri.contains("login_admin.jsp");

        if (estConnecte || pageLogin) {
            // On laisse passer vers la ressource
            chain.doFilter(request, response);
        } else {
            // Si on tente d'accéder à /modifier, /supprimer etc sans être connecté
            res.sendRedirect(req.getContextPath() + "/accueil");
        }
    }

    @Override
    public void destroy() {}
}