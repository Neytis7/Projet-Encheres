package fr.eni.encheres.controller;

import java.io.IOException;
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
import fr.eni.encheres.bo.Utils;

/**
 * Servlet implementation class UserProfile
 */
@WebServlet("/user-profil")
public class UserProfil extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public UserProfil() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UsersManager usersManager = UsersManager.getInstance();
    User userToDisplay = null;
    String errorMessage = "";
    String redirectServlet = "./Home";

    HttpSession session = request.getSession();

    if (session != null && session.getAttribute("idUserConnected") != null) {

      int idUserConnected = (int) session.getAttribute("idUserConnected");

      try {
        userToDisplay = usersManager.getUserById(idUserConnected);
      } catch (BLLException bllException) {
        errorMessage = "An error has occured while displaying your profile";
      }

      if (userToDisplay != null) {
        request.setAttribute("user", userToDisplay);
        redirectServlet = "WEB-INF/userProfil.jsp";
      }
    } else {
      errorMessage = "You must be connected to see this page";
    }


    if (!Utils.isBlankString(errorMessage)) {
      request.setAttribute("errors", errorMessage);
    }

    RequestDispatcher rd = request.getRequestDispatcher(redirectServlet);

    if (rd != null) {
      rd.forward(request, response);
    }
  }

}
