package fr.eni.encheres.controller;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class DeleteAccount
 */
@WebServlet(description = "Permet à l'utilisateur de supprimer son compte", urlPatterns = { "/DeleteAccount" })
public class DeleteAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UsersManager usersManager = new UsersManager();
	    User userConnected = null;
	    ArrayList<String> errorMessage = new ArrayList<>();
	    String redirectServlet = "./user-profil";
	    String successMessage = "Votre compte à bien été supprimé !";

	    HttpSession session = request.getSession();

	    if (session != null && session.getAttribute("idUserConnected") != null) {

	      int idUserConnected = (int) session.getAttribute("idUserConnected");

	      try {
	    	  userConnected = usersManager.getUserById(idUserConnected);
	    	  
	    	  if(userConnected != null) {
	    		  boolean success = usersManager.deleteAccount(userConnected);
		    	  
		    	  if(success) {
		    		  request.setAttribute("successMessage", successMessage);
		    		  redirectServlet = "./sign-out";
		    	  }
	    	  }else {
	    		  throw new BLLException(new Exception("L'utilisateur n'a pas été trouvé"));
	    	  }
	      } catch (BLLException bllException) {
	        errorMessage.add("Une erreur est survenue pour la suppression de votre compte");
	      }
	    } else {
	    	 errorMessage.add("Vous devez vous connecter pour réaliser cette action");
	    }
	    
	    request.setAttribute("errors", errorMessage);

	    RequestDispatcher rd = request.getRequestDispatcher(redirectServlet);

	    if (rd != null) {
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
