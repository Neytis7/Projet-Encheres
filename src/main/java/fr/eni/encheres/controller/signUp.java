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
@WebServlet(description = "Permet à l'utilisateur de s'inscrire", urlPatterns = { "/sign-up" })
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/sign-up.jsp");
		
		if(rd != null) {
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String btnSignUp = (String) request.getParameter("btnSignUp");
		List<String> errors = new ArrayList<>();
		UsersManager usersManager = new UsersManager();
		String redirectServlet = "WEB-INF/sign-up.jsp";
		HttpSession session = request.getSession();	
		
		if(btnSignUp != null) {
	
			String pseudoUser = (String) request.getParameter("pseudoUser");
			String nameUser = (String) request.getParameter("nameUser");
			String firstNameUser = (String) request.getParameter("firstNameUser");
			String mailUser = (String) request.getParameter("mailUser");
			String phoneNumberUser = (String) request.getParameter("phoneNumberUser");
			String addressUser = (String) request.getParameter("addressUser");
			String cityUser = (String) request.getParameter("cityUser");
			String zipCodeUser = (String) request.getParameter("zipCodeUser");
			String passwordUser = (String) request.getParameter("passwordUser");
			int creditUser = 0;
			boolean isAdministrator = false;	
			
			if(pseudoUser.isEmpty()) {
				errors.add("Le pseudo est manquant");		
			}else if(!pseudoUser.matches("^[a-zA-Z0-9]*$")) {
				errors.add("Le pseudo doit être composé uniquement de caractère alphanumérique !");	
			}
			
			if(nameUser.isEmpty()) {
				errors.add("Le nom est manquant");		
			}
			if(firstNameUser.isEmpty()) {
				errors.add("Le prénom est manquant");		
			}
			if(mailUser.isEmpty()) {
				errors.add("L'email est manquant");		
			}
			if(addressUser.isEmpty()) {
				errors.add("L'adresse est manquante");		
			}
			if(cityUser.isEmpty()) {
				errors.add("La ville est manquante");		
			}
			if(zipCodeUser.isEmpty()) {
				errors.add("Le code postal est manquant");		
			}
			if(passwordUser.isEmpty()) {
				errors.add("Le mot de passe est manquant");		
			}			
			
			if(errors.size() == 0) {
			
				try {
					User user = new User(pseudoUser, nameUser, firstNameUser, mailUser, phoneNumberUser, 
							addressUser, zipCodeUser, cityUser, passwordUser, creditUser, isAdministrator);
					
					errors = new ArrayList<>();
					errors = usersManager.signUpUser(user);
					
					if(errors.size() != 0) {
						request.setAttribute("errors", errors);
					}else {
						session.setAttribute("idUserConnected", user.getNo_user());
						session.setAttribute("nameUserConnected", user.getName());
						session.setAttribute("firstNameUserConnected", user.getFirst_name());
						redirectServlet = "WEB-INF/Home.jsp";
					}					
					
				} catch (BLLException e) {
					errors.add("[erreur] L'inscripton de l'utilisateur à échoué");
				}
			}else {
				request.setAttribute("errors", errors);
			}	
		}
			
		RequestDispatcher rd = request.getRequestDispatcher(redirectServlet);
		
		if(rd != null) {
			rd.forward(request, response);
		}
	}
}
