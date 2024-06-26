
package linhtk.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhtk.registration.RegistrationDAO;
import linhtk.utils.MyApplicationConstant;


@WebServlet(name = "DeleteAccountServlet", urlPatterns = {"/DeleteAccountServlet"})
public class DeleteAccountServlet extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("pk");
        String lastSearchValue = request.getParameter("lastSearchValue");
        
        String url = MyApplicationConstant.DeleteFeatures.ERROR_PAGE;
        
        try {
            HttpSession session = request.getSession(false);
            
            if (session == null) {
                url = MyApplicationConstant.DeleteFeatures.LOGIN_PAGE;
                return;
            }
            
            RegistrationDAO dao = new RegistrationDAO();
            boolean result = dao.deleteAccount(username);
            
            if (result) {
                //call Search feature again
                //using url rewriting technique
//                url = "DispatchServlet"
//                        + "?btAction=Search"
//                        + "&txtSearchValue=" + lastSearchValue;
                url = MyApplicationConstant.DeleteFeatures.SEARCH_FULLNAME_CONTROLLER 
                        + "?txtSearchValue=" + lastSearchValue;
            }
        } catch (SQLException ex) {
            log("DeleteAccountServlet_SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("DeleteAccountServlet_Naming: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
