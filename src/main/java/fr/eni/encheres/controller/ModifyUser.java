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
    UsersManager usersManager = UsersManager.getInstance();
    User userToDisplay = null;
    ArrayList<String> errorMessage = new ArrayList<>();
    String redirectServlet = "/WEB-INF/modifyUser.jsp";
    HttpSession session = request.getSession();
    if (session != null && session.getAttribute("idUserConnected") != null) {
      int idUserConnected = (int) session.getAttribute("idUserConnected");
      try {
        userToDisplay = usersManager.getUserById(idUserConnected);
      } catch (BLLException bllException) {
        errorMessage.add("Failed to get user " + idUserConnected);
      }

      if (userToDisplay != null) {
        request.setAttribute("user", userToDisplay);
      } else {
        request.setAttribute("errorMessage", errorMessage);
      }

    } else {
      errorMessage.add("You must be connected to see your profile");
      redirectServlet = "./Home";
    }

    RequestDispatcher rd = request.getRequestDispatcher(redirectServlet);
    request.setAttribute("errors", errorMessage);

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
    User userToModify = null;
    HttpSession session = request.getSession();
    if (session != null && session.getAttribute("idUserConnected") != null) {
      int idUserConnected = (int) session.getAttribute("idUserConnected");

      if (btnValidateModification != null) {
        userToModify = new User(idUserConnected, request.getParameter("pseudoUser"),
            request.getParameter("nameUser"), request.getParameter("firstNameUser"),
            request.getParameter("mailUser"), request.getParameter("phoneNumberUser"),
            request.getParameter("addressUser"), request.getParameter("zipCodeUser"),
            request.getParameter("cityUser"), request.getParameter("passwordUser"),
            Integer.parseInt(request.getParameter("credit")), false, false);
        errors = userToModify.checkInformations();
        if (!request.getParameter("passwordUser").trim()
            .equals(request.getParameter("confirmPasswordUser").trim())) {
          errors.add("Passwords are different");

        }

        if (errors.size() == 0) {
          try {

            errors = new ArrayList<>();
            errors = usersManager.updateUser(userToModify);

            if (errors.size() == 0) {

              User userModified = usersManager.getUserById(userToModify.getNo_user());

              String sessionUserName = (String) session.getAttribute("nameUserConnected");
              String sessionUserFirstName = (String) session.getAttribute("firstNameUserConnected");

              if (!userModified.getName().equals(sessionUserName)
                  || !userModified.getFirst_name().equals(sessionUserFirstName)) {

                session.setAttribute("nameUserConnected", userModified.getName());
                session.setAttribute("firstNameUserConnected", userModified.getFirst_name());
              }
              request.setAttribute("success", "Your informations has been successfully updated");
              redirectServlet = "./user-profil";
            } else {
              request.setAttribute("errors", errors);
              request.setAttribute("user", userToModify);
            }
          } catch (BLLException e) {
            errors.add("An eeror has occurred while saving your informations");
          }
        }
      } else {
        redirectServlet = "./Home";
      }

    } else {
      errors.add("You must be connected to see you profile");
      redirectServlet = "./Home";
    }
    request.setAttribute("errors", errors);
    request.setAttribute("user", userToModify);
    RequestDispatcher rd = request.getRequestDispatcher(redirectServlet);

    if (rd != null) {
      rd.forward(request, response);
    }
  }
}
