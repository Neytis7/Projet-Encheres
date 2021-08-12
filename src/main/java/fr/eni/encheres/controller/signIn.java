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

import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.UsersManager;

/**
 * Servlet implementation class signIn
 */
@WebServlet(description = "Permet à l'utilisateur de se connecter", urlPatterns = { "/sign-in" })
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/sign-in.jsp");
		
		if(rd != null) {
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String btnSignIn = (String) request.getParameter("btnSignIn");
		List<String> errors = new ArrayList<>();
		UsersManager usersManager = new UsersManager();
		String redirectServlet = "WEB-INF/sign-in.jsp";
		
		if(btnSignIn != null) {
			
			String loginUser = (String) request.getParameter("loginUser");
			String passwordUser = (String) request.getParameter("passwordUser");
			
			if(!loginUser.isEmpty() || !passwordUser.isEmpty()) {
				
				try {
					usersManager.signInUser(loginUser, passwordUser);
					redirectServlet = "WEB-INF/Home.jsp";
				}catch(BLLException exception) {
					errors.add("Le login ou le mot de passe est incorrecte, veuillez réessayer !");
				}
			}else {
				errors.add("Les champs pseudo/email et mot de passe doivent être renseignés.");
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(redirectServlet);
		
		if(rd != null) {
			rd.forward(request, response);
		}
	}

}
