package fr.eni.encheres.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.UsersManager;
import fr.eni.encheres.bo.User;

/**
 * Servlet implementation class signIn
 */
@WebServlet(description = "Permet à l'utilisateur de se connecter", urlPatterns = {"/sign-in"})
public class signIn extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public signIn() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sign-in.jsp");

    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("login")) {
          request.setAttribute("rememberMe", cookie.getValue());
          break;
        }
      }
    }

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

    String btnSignIn = request.getParameter("btnSignIn");
    List<String> errors = new ArrayList<>();
    UsersManager usersManager = UsersManager.getInstance();
    String redirectServlet = "WEB-INF/sign-in.jsp";
    ArrayList<Object> array = new ArrayList<>();
    User userConnected = null;
    HttpSession session = request.getSession();

    if (btnSignIn != null) {

      String loginUser = request.getParameter("loginUser");
      String passwordUser = request.getParameter("passwordUser");
      String[] rememberMe = request.getParameterValues("rememberMe");

      if (!loginUser.isEmpty() || !passwordUser.isEmpty()) {

        try {
          array = usersManager.signInUser(loginUser, passwordUser);

          if (!array.isEmpty()) {

            userConnected = (User) array.get(0);
            boolean access = (boolean) array.get(1);

            if (access) {

              if (rememberMe != null && rememberMe.length > 0) {
                for (String e : rememberMe) {
                  if ("on".equals(e.trim())) {
                    Cookie cookie = new Cookie("login", loginUser);
                    cookie.setMaxAge(60 * 60 * 24 * 30);
                    response.addCookie(cookie);
                    break;
                  }
                }
              } else {
                Cookie[] cookies = request.getCookies();

                if (cookies != null) {
                  for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("login")) {
                      cookie.setMaxAge(0);
                      response.addCookie(cookie);
                      break;
                    }
                  }
                }
              }

              session.setAttribute("idUserConnected", userConnected.getNo_user());
              session.setAttribute("nameUserConnected", userConnected.getName());
              session.setAttribute("firstNameUserConnected", userConnected.getFirst_name());
              redirectServlet = "./Home";
            } else {
              errors.add("This account is inactive");
              redirectServlet = "WEB-INF/sign-in.jsp";
            }
          } else {
            errors.add("Login/Password incorrect !");
          }

        } catch (BLLException exception) {
          errors.add("An error has occured, try again !");
        }
      } else {
        errors.add("You have to fill login/password fields.");
      }
    }

    if (errors.size() > 0) {
      request.setAttribute("errors", errors);
    }

    RequestDispatcher rd = request.getRequestDispatcher(redirectServlet);

    if (rd != null) {
      rd.forward(request, response);
    }
  }
}
