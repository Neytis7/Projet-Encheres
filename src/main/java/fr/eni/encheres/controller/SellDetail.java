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
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.AuctionManager;
import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.CategoryManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bll.UsersManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Auction;
import fr.eni.encheres.bo.User;

/**
 * Servlet implementation class SellDetail
 */
@WebServlet(description = "sell detail servlet", urlPatterns = { "/sell-detail" })
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int idArticle = getIDArticleParameter(request, response);
		ArticleManager articleManager = ArticleManager.getInstance();
		CategoryManager categoryManager = CategoryManager.getInstance();
		AuctionManager auctionManager = AuctionManager.getInstance();
		RetraitManager retraitManager = new RetraitManager();
		UsersManager usersManager = new UsersManager();
		HttpSession session = request.getSession();
		ArrayList<String> errors = new ArrayList<>();
		int connectedUser = 0;
		if (session != null && session.getAttribute("idUserConnected") != null) {
			connectedUser = (int) session.getAttribute("idUserConnected");
		} else {
			errors.add("You must be connected to see sell details");
			request.setAttribute("errors", errors);
			redirect(request, response, "./sign-in");
		}

		try {
			Article articleToDisplay = articleManager.getArticleById(idArticle);
			if (articleToDisplay == null) {
				throw new BLLException(new Exception("Error in http request"));
			}
			articleToDisplay.setCategory(categoryManager.getCategoryByIdArticle(idArticle));
			articleToDisplay.setListAuction(auctionManager.getListAuctionByIdArticle(idArticle));
			articleToDisplay.setWithdrawalPoint(retraitManager.getRetraitByIdArticle(idArticle));
			articleToDisplay.setUserSeller(usersManager.getUserByIdArticle(idArticle));

			request.setAttribute("article", articleToDisplay);
			request.setAttribute("bid",
					(session != null && connectedUser != articleToDisplay.getUserSeller().getNo_user()
							&& LocalDate.now().isBefore(articleToDisplay.getEnd_date())));
			// FINISH SELL

			if (LocalDate.now().isAfter(articleToDisplay.getEnd_date())
					|| LocalDate.now().isEqual(articleToDisplay.getEnd_date())) {
				request.setAttribute("finished", true);
				if (!articleToDisplay.isFinished()) {
					articleManager.finishSellArticle(idArticle, articleToDisplay.getBestBid());
					User userToDebit = articleToDisplay.getUserWithBestBid();
					userToDebit.setCredit(userToDebit.getCredit() - articleToDisplay.getBestBid());
					usersManager.updateUser(userToDebit);
				}
			} else {
				request.setAttribute("finished", false);
			}

		} catch (BLLException e) {
			// TODO Auto-generated catch block
			errors.add("Error in http request");
			request.setAttribute("errors", errors);
			redirect(request, response, "./Home");
		}

		redirect(request, response, "WEB-INF/sellDetail.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int idArticle = getIDArticleParameter(request, response);
		int connectedUser = (int) request.getSession().getAttribute("idUserConnected");
		AuctionManager auctionManager = AuctionManager.getInstance();
		ArticleManager articleManager = ArticleManager.getInstance();
		UsersManager usersManager = new UsersManager();

		try {
			Article articleToTest = articleManager.getArticleById(idArticle);
			if (articleToTest == null) {
				throw new BLLException(new Exception("Error in http request"));
			}

			User user = usersManager.getUserById(connectedUser);
			articleToTest.setListAuction(auctionManager.getListAuctionByIdArticle(idArticle));
			int bestBid = articleToTest.getBestBid();
			int proposedBid = Integer.parseInt(request.getParameter("priceItem"));

			if (LocalDate.now().isAfter(articleToTest.getEnd_date())) {
				redirectToGet(request, response, "You can't bid anymore on this item");
			}

			if (user.getCredit() < proposedBid) {
				redirectToGet(request, response, "You don't have enough credits");

			}

			if (proposedBid <= bestBid || proposedBid <= articleToTest.getInitial_price()) {
				redirectToGet(request, response, "Your bid is too low");
			}

			Auction auction = new Auction(LocalDate.now(), proposedBid, user);

			auctionManager.insertNewAuction(auction, idArticle);

		} catch (BLLException e) {
			doGet(request, response);
			e.printStackTrace();
		}

		doGet(request, response);
	}

	private void redirectToGet(HttpServletRequest request, HttpServletResponse response, String error)
			throws ServletException, IOException, BLLException {
		ArrayList<String> errors = new ArrayList<>();
		errors.add(error);
		request.setAttribute("errors", errors);
		doGet(request, response);
		throw new BLLException(new Exception(error));
	}

	private int getIDArticleParameter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> errors = new ArrayList<>();
		int idArticle = 0;
		try {
			idArticle = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException exception) {
			errors.add("Error in http request");
			request.setAttribute("errors", errors);
			redirect(request, response, "./Home");
		}
		return idArticle;
	}

	private void redirect(HttpServletRequest request, HttpServletResponse response, String URL)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(URL);

		if (rd != null) {
			rd.forward(request, response);
		}
	}

}
