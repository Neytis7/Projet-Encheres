package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.jdbc.ConnexionProvider;

public class ArticleDaoImpl implements ArticleDAO {

	private static final String INSERT_ARTICLE = "USE DB_ENCHERES INSERT INTO ARTICLES_VENDUS"
			+ "(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, "
			+ "no_utilisateur, no_categorie, est_supprime ) VALUES (?,?,?,?,?,?,?,?)";

	private static final String VIEW_ARTICLES_IN_PROGRESS =
			"USE DB_ENCHERES SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE est_supprime = 0 AND date_debut_encheres<=GETDATE() AND GETDATE()<date_fin_encheres ";

	private static final String VIEW_ARTICLE =
			"USE DB_ENCHERES SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie ";

	private static final String CHECK_MAX_MONTANT =
			"USE DB_ENCHERES SELECT Max(montant_enchere) AS me FROM ARTICLES_VENDUS INNER JOIN ENCHERES ON ARTICLES_VENDUS.no_article = ENCHERES.no_article where ENCHERES.no_article = ";

	private static final String CHECK_USER_MAX_AUCTION =
			"USE DB_ENCHERES SELECT Encheres.no_article,MAX(montant_enchere) AS maxEnchere FROM ARTICLES_VENDUS INNER JOIN ENCHERES ON ARTICLES_VENDUS.no_article = ENCHERES.no_article WHERE ";

	private static final String USER_AUCTION =
			"USE DB_ENCHERES SELECT ARTICLES_VENDUS.no_article FROM ARTICLES_VENDUS INNER JOIN ENCHERES ON ARTICLES_VENDUS.no_article = ENCHERES.no_article INNER JOIN CATEGORIES ON CATEGORIES.no_categorie = ARTICLES_VENDUS.no_categorie where est_supprime = 0 AND date_debut_encheres<=GETDATE() AND GETDATE()<date_fin_encheres ";

	private static final String USER_ARTICLE_IN_PROGRESS =
			"USE DB_ENCHERES SELECT ARTICLES_VENDUS.no_article FROM ARTICLES_VENDUS INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie where est_supprime = 0 AND date_debut_encheres<=GETDATE() AND GETDATE()<date_fin_encheres ";

	private static final String USER_ARTICLE_NOT_BEGIN =
			"USE DB_ENCHERES SELECT ARTICLES_VENDUS.no_article FROM ARTICLES_VENDUS INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie where est_supprime = 0 AND date_debut_encheres>GETDATE() ";

	private static final String USER_ARTICLE_TERMINATE =
			"USE DB_ENCHERES SELECT ARTICLES_VENDUS.no_article FROM ARTICLES_VENDUS INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie where est_supprime = 0 AND date_fin_encheres<GETDATE() ";

	private static final String USER_ARTICLE_MAX_AUCTION =
			"USE DB_ENCHERES SELECT Encheres.no_article,MAX(montant_enchere) AS miseMax FROM ARTICLES_VENDUS INNER JOIN ENCHERES ON ARTICLES_VENDUS.no_article = ENCHERES.no_article INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE ARTICLES_VENDUS.date_fin_encheres<GETDATE() AND est_supprime = 0 ";


	@Override
	public int insert(Article article) throws DALException {

		int result = 0;
		int idArticle = 0;
		try (Connection connection = ConnexionProvider.getConnection()){

			PreparedStatement ps =
					connection.prepareStatement(INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, article.getName_article());
			ps.setString(2, article.getDescription());
			ps.setDate(3, Date.valueOf(article.getStart_date()));
			ps.setDate(4, Date.valueOf(article.getEnd_date()));
			ps.setInt(5, article.getInitial_price());
			ps.setInt(6, article.getUserSeller().getNo_user());
			ps.setInt(7, article.getCategory().getNo_category());
			ps.setBoolean(8, false);

			result = ps.executeUpdate();

			if (result > 0) {
				try (ResultSet generatedKey = ps.getGeneratedKeys()) {
					if (generatedKey.next()) {
						article.setNo_article(generatedKey.getInt(1));
						idArticle = article.getNo_article();
					}
				} catch (SQLException e) {
					throw new DALException(new Exception("La génération de l'id de l'article a échoué"));
				}
			}
		} catch (SQLException exception) {
			throw new DALException(new Exception("[Erreur] impossible de créer l'article."));
		}
		return idArticle;
	}

	@Override
	public List<Article> getArticlesInProgress() throws Exception {
		return getArticle(VIEW_ARTICLES_IN_PROGRESS);
	}


	@Override
	public List<Article> getPrice(List<Article> listArticles) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		for (int i = 0; i <= listArticles.size() - 1; i++) {
			String finalSQL = "'" + listArticles.get(i).getNo_article() + "'";
			ResultSet res = sts.executeQuery(CHECK_MAX_MONTANT + finalSQL);
			while (res.next()) {
				if (res.getInt(1) != 0) {
					listArticles.get(i).setPrice_auction(res.getInt(1));
				}
			}
		}
		if(cnx != null) {
			cnx.close();
		}

		return listArticles;
	}

	@Override
	public List<Article> FilterNameAll(String search) throws Exception {
		String finalArticle = VIEW_ARTICLES_IN_PROGRESS + "AND nom_article LIKE '" + search + "%'";
		return getArticle(finalArticle);
	}


	@Override
	public List<Article> FilterNameCategory(String search, String category) throws Exception {
		String finalArticle = "AND nom_article LIKE'" + search + "%' ";
		String finalCategorie = "AND libelle = '" + category + "'";
		String sqlFinal = VIEW_ARTICLES_IN_PROGRESS + finalArticle + finalCategorie;
		return getArticle(sqlFinal);
	}


	@Override
	public List<Article> FilterCategory(String category) throws Exception {
		String finalCategorie = "AND libelle = '" + category + "'";
		String finalRequete = VIEW_ARTICLES_IN_PROGRESS + finalCategorie;
		return getArticle(finalRequete);
	}

	@Override
	public List<Article> FilterConnectedSearchAuctionInProgress(String search, int idUserConnected)
			throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalArticle = "AND nom_article LIKE'" + search + "%' ";
		String finalId =
				"AND ENCHERES.no_utilisateur = " + idUserConnected + " group by ARTICLES_VENDUS.no_article";
		String sqlFinal = finalArticle + finalId;
		ResultSet res = sts.executeQuery(USER_AUCTION + sqlFinal);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterConnectedSearchCategoryAuctionInProgress(String search,
			String category, int idUserConnected) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalArticle = "AND nom_article LIKE'" + search + "%' ";
		String finalId =
				"AND ENCHERES.no_utilisateur = " + idUserConnected + " group by ARTICLES_VENDUS.no_article";
		String finalCategory = "AND libelle = '" + category + "' ";
		String sqlFinal = finalArticle + finalCategory + finalId;
		ResultSet res = sts.executeQuery(USER_AUCTION + sqlFinal);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterCategoryAuctionInProgress(String category, int idUserConnected)
			throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId =
				"AND ENCHERES.no_utilisateur = " + idUserConnected + " group by ARTICLES_VENDUS.no_article";
		String finalCategory = "AND libelle = '" + category + "' ";
		String sqlFinal = finalCategory + finalId;
		ResultSet res = sts.executeQuery(USER_AUCTION + sqlFinal);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterMyAuctionInProgress(int idUserConnected) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId =
				"AND ENCHERES.no_utilisateur = " + idUserConnected + " group by ARTICLES_VENDUS.no_article";
		ResultSet res = sts.executeQuery(USER_AUCTION + finalId);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterConnectedSearchMySellInProgress(String search, int idUserConnected)
			throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		String finalArticle = " AND nom_article LIKE '" + search + "%' ";
		ResultSet res = sts.executeQuery(USER_ARTICLE_IN_PROGRESS + finalId + finalArticle);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterConnectedSearchCategoryMySellInProgress(String search, String category,
			int idUserConnected) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		String finalCategory = "AND libelle = '" + category + "' ";
		String finalArticle = " AND nom_article LIKE '" + search + "%' ";
		ResultSet res =
				sts.executeQuery(USER_ARTICLE_IN_PROGRESS + finalId + finalArticle + finalCategory);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterCategoryMySellInProgress(String category, int idUserConnected)
			throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		String finalCategory = "AND libelle = '" + category + "' ";
		ResultSet res = sts.executeQuery(USER_ARTICLE_IN_PROGRESS + finalId + finalCategory);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterMySellInProgress(int idUserConnected) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		ResultSet res = sts.executeQuery(USER_ARTICLE_IN_PROGRESS + finalId);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterConnectedSearchMySellNotBegin(String search, int idUserConnected)
			throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		String finalArticle = " AND nom_article LIKE '" + search + "%' ";
		ResultSet res = sts.executeQuery(USER_ARTICLE_NOT_BEGIN + finalId + finalArticle);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterConnectedSearchCategoryMySellNotBegin(String search, String category,
			int idUserConnected) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		String finalCategory = "AND libelle = '" + category + "' ";
		String finalArticle = " AND nom_article LIKE '" + search + "%' ";
		ResultSet res =
				sts.executeQuery(USER_ARTICLE_NOT_BEGIN + finalId + finalArticle + finalCategory);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterCategoryMySellNotBegin(String category, int idUserConnected)
			throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		String finalCategory = "AND libelle = '" + category + "' ";
		ResultSet res = sts.executeQuery(USER_ARTICLE_NOT_BEGIN + finalId + finalCategory);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterMySellNotBegin(int idUserConnected) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		ResultSet res = sts.executeQuery(USER_ARTICLE_NOT_BEGIN + finalId);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterConnectedSearchMySellTerminate(String search, int idUserConnected)
			throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		String finalArticle = " AND nom_article LIKE '" + search + "%' ";
		ResultSet res = sts.executeQuery(USER_ARTICLE_TERMINATE + finalId + finalArticle);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterConnectedSearchCategoryMySellTerminate(String search, String category,
			int idUserConnected) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		String finalCategory = "AND libelle = '" + category + "' ";
		String finalArticle = " AND nom_article LIKE '" + search + "%' ";
		ResultSet res =
				sts.executeQuery(USER_ARTICLE_TERMINATE + finalId + finalArticle + finalCategory);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterCategoryMySellTerminate(String category, int idUserConnected)
			throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		String finalCategory = "AND libelle = '" + category + "' ";
		ResultSet res = sts.executeQuery(USER_ARTICLE_TERMINATE + finalId + finalCategory);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterMySellTerminate(int idUserConnected) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND no_utilisateur = " + idUserConnected;
		ResultSet res = sts.executeQuery(USER_ARTICLE_TERMINATE + finalId);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt("no_article");
			listIdArticle.add(no_article);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(listIdArticle);
	}

	@Override
	public List<Article> FilterConnectedSearchAuctionWin(String search, int idUserConnected)
			throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND Encheres.no_utilisateur = " + idUserConnected;
		String finalArticle = " AND nom_article LIKE '" + search + "%' group by Encheres.no_article";
		ResultSet res = sts.executeQuery(USER_ARTICLE_MAX_AUCTION + finalId + finalArticle);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		List<Integer> listAuction = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt(1);
			listIdArticle.add(no_article);
			int miseMax = res.getInt(2);
			listAuction.add(miseMax);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(checkUserMaxAuction(listIdArticle, listAuction));
	}

	@Override
	public List<Article> FilterConnectedSearchCategoryAuctionWin(String search, String category,
			int idUserConnected) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND Encheres.no_utilisateur = " + idUserConnected;
		String finalCategory = "AND libelle= '" + category + "' ";
		String finalArticle = " AND nom_article LIKE '" + search + "%' group by Encheres.no_article";
		ResultSet res =
				sts.executeQuery(USER_ARTICLE_MAX_AUCTION + finalId + finalCategory + finalArticle);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		List<Integer> listAuction = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt(1);
			listIdArticle.add(no_article);
			int miseMax = res.getInt(2);
			listAuction.add(miseMax);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(checkUserMaxAuction(listIdArticle, listAuction));
	}

	@Override
	public List<Article> FilterCategoryAuctionWin(String category, int idUserConnected)
			throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId = "AND Encheres.no_utilisateur = " + idUserConnected;
		String finalCategory = "AND libelle= '" + category + "' group by Encheres.no_article";
		ResultSet res = sts.executeQuery(USER_ARTICLE_MAX_AUCTION + finalId + finalCategory);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		List<Integer> listAuction = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt(1);
			listIdArticle.add(no_article);
			int miseMax = res.getInt(2);
			listAuction.add(miseMax);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(checkUserMaxAuction(listIdArticle, listAuction));
	}

	@Override
	public List<Article> FilterMyAuctionWin(int idUserConnected) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		String finalId =
				"AND Encheres.no_utilisateur = " + idUserConnected + " group by Encheres.no_article";
		ResultSet res = sts.executeQuery(USER_ARTICLE_MAX_AUCTION + finalId);
		List<Integer> listIdArticle = new ArrayList<Integer>();
		List<Integer> listAuction = new ArrayList<Integer>();
		while (res.next()) {
			int no_article = res.getInt(1);
			listIdArticle.add(no_article);
			int miseMax = res.getInt(2);
			listAuction.add(miseMax);
		}
		if(cnx != null) {
			cnx.close();
		}
		return getArticle(checkUserMaxAuction(listIdArticle, listAuction));
	}

	public List<Integer> checkUserMaxAuction(List<Integer> listIdArticle, List<Integer> listAuction)
			throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		List<Integer> listFinalIdArticle = new ArrayList<Integer>();
		for (int i = 0; i < listIdArticle.size(); i++) {
			String no_article = "Encheres.no_article = " + listIdArticle.get(i);
			String auction =
					" group by Encheres.no_article HAVING MAX(montant_enchere)= " + listAuction.get(i);
			String finalSql = no_article + auction;
			ResultSet res1 = sts.executeQuery(CHECK_USER_MAX_AUCTION + finalSql);
			while (res1.next()) {
				int finalNoArticle = res1.getInt("no_article");
				listFinalIdArticle.add(finalNoArticle);
			}
		}
		if(cnx != null) {
			cnx.close();
		}
		return listFinalIdArticle;
	}

	public List<Article> getArticle(List<Integer> listIdArticle) throws Exception {
		List<Article> listArticles = new ArrayList<Article>();
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		for (int i = 0; i < listIdArticle.size(); i++) {
			String newSqlFinal = "WHERE no_article =  " + listIdArticle.get(i);
			ResultSet newRes = sts.executeQuery(VIEW_ARTICLE + newSqlFinal);
			while (newRes.next()) {
				// Article
				int no_article = newRes.getInt("no_article");
				String name_article = newRes.getString("nom_article");
				String description = newRes.getString("description");
				LocalDate start_date = newRes.getDate("date_debut_encheres").toLocalDate();
				LocalDate end_date = newRes.getDate("date_fin_encheres").toLocalDate();
				int initial_price = newRes.getInt("prix_initial");
				int sell_price = newRes.getInt("prix_vente");
				boolean isDelete = newRes.getBoolean("est_supprime");
				// Category
				int no_category = newRes.getInt("no_categorie");
				String libelle = newRes.getString("libelle");
				Category categoryBis = new Category(no_category, libelle);
				// User
				int no_user = newRes.getInt("no_utilisateur");
				String pseudo = newRes.getString("pseudo");
				String name = newRes.getString("nom");
				String first_name = newRes.getString("prenom");
				String mail = newRes.getString("email");
				String phone_number = newRes.getString("telephone");
				String address = newRes.getString("rue");
				String zip_code = newRes.getString("code_postal");
				String city = newRes.getString("ville");
				String password = newRes.getString("mot_de_passe");
				int credit = newRes.getInt("credit");
				boolean isAdministrator = newRes.getBoolean("administrateur");
				User user = new User(no_user, pseudo, name, first_name, mail, phone_number, address,
						zip_code, city, password, credit, isAdministrator);

				Article article = new Article(no_article, name_article, description, start_date, end_date,
						initial_price, sell_price, user, categoryBis, isDelete);

				listArticles.add(article);
			}
		}
		if(cnx != null) {
			cnx.close();
		}
		return listArticles;
	}

	public List<Article> getArticle(String requete) throws Exception {
		Connection cnx = ConnexionProvider.getConnection();
		Statement sts = cnx.createStatement();
		ResultSet res = sts.executeQuery(requete);
		List<Article> listArticles = new ArrayList<Article>();
		while (res.next()) {// Parcour ligne par ligne
			// Article
			int no_article = res.getInt("no_article");
			String name_article = res.getString("nom_article");
			String description = res.getString("description");
			LocalDate start_date = res.getDate("date_debut_encheres").toLocalDate();
			LocalDate end_date = res.getDate("date_fin_encheres").toLocalDate();
			int initial_price = res.getInt("prix_initial");
			int sell_price = res.getInt("prix_vente");
			boolean isDelete = res.getBoolean("est_supprime");
			// Category
			int no_category = res.getInt("no_categorie");
			String libelle = res.getString("libelle");
			Category categoryBis = new Category(no_category, libelle);
			// User
			int no_user = res.getInt("no_utilisateur");
			String pseudo = res.getString("pseudo");
			String name = res.getString("nom");
			String first_name = res.getString("prenom");
			String mail = res.getString("email");
			String phone_number = res.getString("telephone");
			String address = res.getString("rue");
			String zip_code = res.getString("code_postal");
			String city = res.getString("ville");
			String password = res.getString("mot_de_passe");
			int credit = res.getInt("credit");
			boolean isAdministrator = res.getBoolean("administrateur");
			User user = new User(no_user, pseudo, name, first_name, mail, phone_number, address, zip_code,
					city, password, credit, isAdministrator);

			Article article = new Article(no_article, name_article, description, start_date, end_date,
					initial_price, sell_price, user, categoryBis, isDelete);

			listArticles.add(article);

		}
		if(cnx != null) {
			cnx.close();
		}
		return listArticles;
	}
}
