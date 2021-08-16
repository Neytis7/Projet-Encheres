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
		UsersManager usersManager = new UsersManager();
		String redirectServlet = "WEB-INF/sign-in.jsp";
		ArrayList<Object> array = new ArrayList<>();
		User userConnected = null;
		HttpSession session = request.getSession();

		if (btnSignIn != null) {

			String loginUser = request.getParameter("loginUser");
			String passwordUser = request.getParameter("passwordUser");

			if (!loginUser.isEmpty() || !passwordUser.isEmpty()) {

				try {
					array = usersManager.signInUser(loginUser, passwordUser);

					if (!array.isEmpty()) {

						userConnected = (User) array.get(0);
						boolean access = (boolean) array.get(1);

						if (access) {

							session.setAttribute("idUserConnected", userConnected.getNo_user());
							session.setAttribute("nameUserConnected", userConnected.getName());
							session.setAttribute("firstNameUserConnected", userConnected.getFirst_name());
							redirectServlet = "./Home";
						}
					} else {
						errors.add("Le login ou le mot de passe est incorrecte !");
					}

				} catch (BLLException exception) {
					errors.add("Une erreur s'est produite, veuillez réessayer !");
				}
			} else {
				errors.add("Les champs pseudo/email et mot de passe doivent être renseignés.");
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
