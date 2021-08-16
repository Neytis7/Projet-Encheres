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
 * Servlet implementation class ModifyUser
 */
@WebServlet(description = "modify user servlet", urlPatterns = {"/modify-user"})
public class ModifyUser extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ModifyUser() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UsersManager usersManager = new UsersManager();
    User userToDisplay = null;
    String errorMessage = "";
    String redirectServlet = "/WEB-INF/modifyUser.jsp";
    HttpSession session = request.getSession();
    if (session != null && session.getAttribute("idUserConnected") != null) {
      int idUserConnected = (int) session.getAttribute("idUserConnected");
      try {
        userToDisplay = usersManager.getUserById(idUserConnected);
      } catch (BLLException bllException) {
        errorMessage = "Failed to get user " + idUserConnected;
      }

      if (userToDisplay != null) {
        request.setAttribute("user", userToDisplay);
      } else {
        request.setAttribute("errorMessage", errorMessage);
      }

    } else {
      errorMessage = "Vous devez vous connecter pour accéder à votre profil";
      redirectServlet = "/WEB-INF/Home.jsp";
    }

    RequestDispatcher rd = request.getRequestDispatcher(redirectServlet);

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
    String btnValidateModification = request.getParameter("btnValidateModification");
    List<String> errors = new ArrayList<>();
    UsersManager usersManager = new UsersManager();
    String redirectServlet = "/WEB-INF/modifyUser.jsp";
    boolean success = false;
    HttpSession session = request.getSession();
    if (session != null && session.getAttribute("idUserConnected") != null) {
      int idUserConnected = (int) session.getAttribute("idUserConnected");

      if (btnValidateModification != null) {
        User userToModify = new User(idUserConnected, request.getParameter("pseudoUser"),
            request.getParameter("nameUser"), request.getParameter("firstNameUser"),
            request.getParameter("mailUser"), request.getParameter("phoneNumberUser"),
            request.getParameter("addressUser"), request.getParameter("zipCodeUser"),
            request.getParameter("cityUser"), request.getParameter("passwordUser"),
            Integer.parseInt(request.getParameter("credit")), false);
        errors = userToModify.checkInformations();
        if (!request.getParameter("passwordUser").trim()
            .equals(request.getParameter("confirmPasswordUser").trim())) {
          errors.add("Les mots de passe rentr�s ne correspondent pas.");

        }

        if (errors.size() == 0) {
          try {
            usersManager.updateUser(userToModify);
            request.setAttribute("success", "Vos informations ont bien �t� mises � jour");
            success = true;
            redirectServlet = "./user-profil";
          } catch (BLLException e) {
            errors.add("[erreur] Echec lors de l'enregistrement de vos informations.");
          }
        }

        if (!success) {
          request.setAttribute("errors", errors);
          request.setAttribute("user", userToModify);
        }
      } else {
        redirectServlet = "./Home";
      }


    } else {
      errors.add("Vous devez vous connecter pour acc�der � votre profil");
      redirectServlet = "/WEB-INF/Home.jsp";
    }

    RequestDispatcher rd = request.getRequestDispatcher(redirectServlet);

    if (rd != null) {
      rd.forward(request, response);
    }
  }

}
