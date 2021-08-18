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

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.AuctionManager;
import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.CategoryManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bll.UsersManager;
import fr.eni.encheres.bo.Article;

/**
 * Servlet implementation class SellDetail
 */
@WebServlet(description = "sell detail servlet", urlPatterns = {"/sell-detail"})
public class SellDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SellDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idArticle = Integer.parseInt(request.getParameter("id"));
		ArticleManager articleManager = ArticleManager.getInstance();
		CategoryManager categoryManager = CategoryManager.getInstance();
		AuctionManager auctionManager = AuctionManager.getInstance();
		RetraitManager retraitManager = new RetraitManager();
		UsersManager usersManager = new UsersManager();
		HttpSession session = request.getSession();
		ArrayList<String> errors = new ArrayList<>();

	    if (session != null) {
	    	try {
				Article articleToDisplay = articleManager.getArticleById(idArticle);
				articleToDisplay.setCategory(categoryManager.getCategoryByIdArticle(idArticle));
				articleToDisplay.setListAuction(auctionManager.getListAuctionByIdArticle(idArticle));
				articleToDisplay.setWithdrawalPoint(retraitManager.getRetraitByIdArticle(idArticle));
				articleToDisplay.setUserSeller(usersManager.getUserByIdArticle(idArticle));
				System.out.println(articleToDisplay);
				request.setAttribute("article", articleToDisplay);
			} catch (BLLException e) {
				// TODO Auto-generated catch block
				errors.add("Erreur lors de la récupération de l'article");
				request.setAttribute("errors", errors);
			}
	    	
	    }
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sellDetail.jsp");

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
