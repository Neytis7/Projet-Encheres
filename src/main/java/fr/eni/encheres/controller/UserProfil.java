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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsersManager usersManager = new UsersManager();
		User userToDisplay = null;
		String errorMessage = "";
		String redirectServlet = "WEB-INF/Home.jsp";
		
		HttpSession session = request.getSession();
		
		if(session != null && session.getAttribute("idUserConnected") != null) {
			
			int idUserConnected = (int) session.getAttribute("idUserConnected");
			
			try {
				userToDisplay = usersManager.getUserById(idUserConnected);
			} catch (BLLException bllException) {
				errorMessage = "Une erreur est survenue pour l'affichage de votre profil"; 
			}
			
			if (userToDisplay != null) {
				request.setAttribute("user", userToDisplay);
				redirectServlet = "WEB-INF/userProfil.jsp";
			}
		}else {
			errorMessage = "Vous devez vous connecter pour accèder à votre profil"; 
		}

		
		if(!errorMessage.isBlank()) {
			request.setAttribute("errors", errorMessage);
		}
				
		RequestDispatcher rd = request.getRequestDispatcher(redirectServlet);
		
		if(rd != null) {
			rd.forward(request, response);
		}
	}

}