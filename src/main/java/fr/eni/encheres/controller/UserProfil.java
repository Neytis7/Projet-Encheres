package fr.eni.encheres.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.UsersManager;
import fr.eni.encheres.bo.User;

/**
 * Servlet implementation class UserProfile
 */
@WebServlet(description = "user profile servlet", urlPatterns = { "/user-profile" })
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsersManager usersManager = new UsersManager();
		User userToDisplay = null;
		String errorMessage = "";
		
		try {
			userToDisplay = usersManager.getUserById(1); // TODO : Mettre l'id de la variable de session
		} catch (BLLException bllException) {
			errorMessage = "Failed to get user " + 1; 
		}
		
		if (userToDisplay != null) {
			request.setAttribute("user", userToDisplay);
		}else {
			request.setAttribute("errorMessage", errorMessage);
		}
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/userProfil.jsp");
		
		if(rd != null) {
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}

}
