package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// O "/*" indica que este filtro será aplicado a TODAS as rotas e servlets da API
@WebFilter("/*")
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Método obrigatório da interface, pode ficar vazio
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	request.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 1. Permite o acesso do seu frontend local
        String origin = req.getHeader("Origin");
        if ("https://roleta-russa.netlify.app".equals(origin) || "http://localhost:5173".equals(origin)) {
            res.setHeader("Access-Control-Allow-Origin", origin);
        }
        
        // 2. Define os métodos permitidos
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        
        // 3. Define os cabeçalhos permitidos (essencial para requisições do Axios)
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");

        // 4. Trata a requisição "Preflight" (OPTIONS)
        // O navegador envia um OPTIONS antes do POST real para verificar as regras de CORS
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return; // Interrompe aqui mesmo, não precisa ir para o Servlet
        }

        // Se não for OPTIONS, segue o fluxo normal para o Servlet correspondente
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Método obrigatório da interface, pode ficar vazio
    }
}