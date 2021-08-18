package fr.eni.encheres.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.UsersManager;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.bo.Utils;

/**
 * Servlet implementation class NewPassword
 */
@WebServlet(description = "Creation d'un nouveau mot de passe", urlPatterns = { "/NewPassword" })
public class NewPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String token = (String) request.getParameter("token");
		String pseudo = (String) request.getParameter("pseudo");
		UsersManager userManager = new UsersManager();
		User user = null;
		ArrayList<String> errors = new ArrayList<>();
		String redirectPage = "./Home";


		if(!Utils.isBlankString(pseudo) && !Utils.isBlankString(token)) {

			try {
				user = userManager.getUserByLogin(pseudo);
			} catch (BLLException e) {
				e.printStackTrace();
			}


			if(user != null && user.getTokenPassword() != null && token != null && user.getTokenPasswordDate() != null
					&& token.trim().equals(user.getTokenPassword().trim())) {

				Period intervalPeriod = Period.between(user.getTokenPasswordDate(), LocalDate.now());

				if(intervalPeriod.getDays() > 1) {
					errors.add("Ce lien a expiré ou est eronné");
				}else {
					request.setAttribute("pseudo", user.getPseudo());
					request.setAttribute("token", user.getTokenPassword());
					redirectPage = "WEB-INF/newPassword.jsp";
				}
			}else {
				errors.add("Une erreur est survenue, veuillez rennouveler votre demande.");
			}
		}else {
			errors.add("Vous n'avez pas accès à cette page !");
		}

		request.setAttribute("errors", errors);
		RequestDispatcher rd = request.getRequestDispatcher(redirectPage);

		if(rd != null) {
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String password = (String) request.getParameter("passwordUser");
		String passwordConfirm = (String) request.getParameter("passwordUserConfirm");
		String btnNewPassword = (String) request.getParameter("btnNewPassword");
		ArrayList<String> errors = new ArrayList<>();
		String redirectPage = "WEB-INF/newPassword.jsp";
		String pseudo = (String) request.getParameter("pseudoUser");
		String token = (String) request.getParameter("tokenUser");
		User user = null;
		UsersManager userManager = new UsersManager();
		String success = "Vos informations ont étés mis à jour !";

		if(btnNewPassword != null && password != null && passwordConfirm != null 
				&& password.trim().equals(passwordConfirm.trim())) {

			try {

				user = userManager.getUserByLogin(pseudo);

				if(user != null && token != null && user.getTokenPassword() != null 
						&& user.getTokenPasswordDate() != null && token.trim().equals(user.getTokenPassword().trim())) {

					user.setPassword(password);
					Period intervalPeriod = Period.between(user.getTokenPasswordDate(), LocalDate.now());

					if(intervalPeriod.getDays() > 1) {
						errors.add("Ce lien a expiré ou est eronné");
					}else {
						user.setTokenPassword("");
						user.setTokenPasswordDate(null);
						userManager.updateUser(user);
						redirectPage = "./sign-in";
						request.setAttribute("success", success);
					}
				}else {
					errors.add("Des informations sont incorrectes ou le lien est expiré");
				}
			} catch (BLLException e) {
				errors.add("Une erreur est survenue, veuillez réessayer plus tard !");
			}
		}else {
			errors.add("Les mots de passe correspondent pas !");
		}

		request.setAttribute("errors", errors);
		RequestDispatcher rd = request.getRequestDispatcher(redirectPage);

		if(rd != null) {
			rd.forward(request, response);
		}
	}
}
