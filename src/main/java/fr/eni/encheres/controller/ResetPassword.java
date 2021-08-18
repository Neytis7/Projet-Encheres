package fr.eni.encheres.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.UsersManager;
import fr.eni.encheres.bo.SecureTokenGenerator;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.bo.Utils;

/**
 * Servlet implementation class ResetPassword
 */
@WebServlet(description = "Permet à l'utilisateur de réinitialiser son mot de passe", urlPatterns = { "/ResetPassword" })
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResetPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/resetPassword.jsp");

		if(rd != null) {
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UsersManager userManager = new UsersManager();
		String btnResetPassword = (String) request.getParameter("btnResetPassword");
		String email = (String) request.getParameter("emailUser");
		ArrayList<String> errors = new ArrayList<>();
		String redirectPage = "WEB-INF/resetPassword.jsp";
		String success = "Si l'adresse email renseigné existe, un lien de réinitialisation "
				+ "vous sera envoyé pour modifier votre mot de passe.";
		User user = null;
		String lien = null;

		if(btnResetPassword != null && !Utils.isBlankString(email) && Utils.isEmailAdress(email)) {

			String token = SecureTokenGenerator.nextToken();
			
			try {
				user = userManager.getUserByLogin(email);					
			} catch (BLLException e) {
				e.printStackTrace();
			}

			if(user != null) {
				user.setTokenPassword(token);
				user.setTokenPasswordDate(LocalDate.now());
			
				try {
					userManager.updateUser(user);
					String baseUrl = "NewPassword?pseudo="+user.getPseudo()+"&token=" + user.getTokenPassword();
					lien = request.getContextPath() + "/" + baseUrl;
					
				}catch(BLLException exception){
					exception.printStackTrace();
					errors.add("L'envoie de l'email à échoué !");
				}
			}

			if(lien == null) {
				lien = "";
			}
			request.setAttribute("success", success + "<br> " + lien);
			redirectPage = "./Home";
		}else {
			errors.add("L'email doit être renseigné et au bon format pour la réinitialisation !");
		}

		request.setAttribute("errors", errors);

		RequestDispatcher rd = request.getRequestDispatcher(redirectPage);

		if(rd != null) {
			rd.forward(request, response);
		}
	}
}
