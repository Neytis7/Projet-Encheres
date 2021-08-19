package fr.eni.encheres.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.UsersManager;
import fr.eni.encheres.bo.User;

/**
 * Servlet implementation class Inscription
 */
@WebServlet(description = "Permet à l'utilisateur de s'inscrire", urlPatterns = {"/sign-up"})
public class signUp extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public signUp() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/sign-up.jsp");

    if (rd != null) {
      rd.forward(request, response);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String btnSignUp = request.getParameter("btnSignUp");
    List<String> errors = new ArrayList<>();
    UsersManager usersManager = new UsersManager();
    String redirectServlet = "WEB-INF/sign-up.jsp";
    HttpSession session = request.getSession();

    if (btnSignUp != null) {

      User user = new User(request.getParameter("pseudoUser"), request.getParameter("nameUser"),
          request.getParameter("firstNameUser"), request.getParameter("mailUser"),
          request.getParameter("phoneNumberUser"), request.getParameter("addressUser"),
          request.getParameter("zipCodeUser"),request.getParameter("cityUser"),
          request.getParameter("passwordUser"), 0, false);

      errors = user.checkInformations();

      if (errors.size() == 0) {
        try {
          errors = new ArrayList<String>();
          errors = usersManager.signUpUser(user);
          if (errors.size() == 0) {
            session.setAttribute("idUserConnected", user.getNo_user());
            session.setAttribute("nameUserConnected", user.getName());
            session.setAttribute("firstNameUserConnected", user.getFirst_name());
            redirectServlet = "./Home";
            request.setAttribute("success", "Votre compte a été crée avec succès !");
          } else {
            request.setAttribute("errors", errors);
          }
        } catch (BLLException e) {
          errors.add("[erreur] L'inscripton de l'utilisateur à échoué");
        }
      } else {
        request.setAttribute("errors", errors);
      }
    }

    RequestDispatcher rd = request.getRequestDispatcher(redirectServlet);

    if (rd != null) {
      rd.forward(request, response);
    }
  }
}
