/**
 * 
 */
package com.cognizant.moviecruiser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cognizant.moviecruiser.model.Favoriutes;
import com.cognizant.moviecruiser.model.MovieList;

/**
 * @Created By Saranya 760862
 *
 */
public class FavoriteDaoSqlImpl implements FavoritesDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cognizant.moviecruiser.dao.FavoritesDao#addMovieList(long, long)
	 */
	@Override
	public void addMovieList(long userId, long movieId) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionHandler.getConnection();
		java.sql.PreparedStatement preparedStatement = null;

		try {
			if (conn != null) {

				preparedStatement = conn
						.prepareStatement("insert into favorite values(default,?,?)");
				preparedStatement.setLong(1, userId);
				preparedStatement.setLong(2, movieId);
				preparedStatement.executeUpdate();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cognizant.moviecruiser.dao.FavoritesDao#getAllFavoritesItem(long)
	 */
	@Override
	public List<MovieList> getAllFavoritesItem(long userId)
			throws FavoritesEmptyException {
		java.sql.PreparedStatement preparedStatement;
		Connection connection = null;
		ResultSet resultSet;
		boolean activeFlag, freeDeliveryFlag;
		List<MovieList> movieList = new ArrayList<MovieList>();

		try {
			connection = ConnectionHandler.getConnection();
			if (connection != null) {
				Favoriutes favorite = new Favoriutes(movieList, 0);
				StringBuffer sqlBuffer = new StringBuffer();
				sqlBuffer
						.append("select movie_list.ml_id, movie_list.ml_title, movie_list.ml_has_teaser, movie_list.ml_box_office, movie_list.ml_active, ")
						.append("movie_list.ml_genre, movie_list.ml_date_of_launch from movie_list inner ")
						.append("join favorite on ")
						.append("movie_list.ml_id = favorite.ft_pr_id ")
						.append("where favorite.ft_us_id = ?;");
				preparedStatement = connection.prepareStatement(sqlBuffer
						.toString());
				preparedStatement.setLong(1, userId);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Long movieId = resultSet.getLong(1);
					String title = resultSet.getString(2);
					float box_office = resultSet.getFloat(4);
					String active = resultSet.getString(5);
					String freeDelivery = resultSet.getString(3);
					String genre = resultSet.getString(6);
					Date dateOfLaunch = resultSet.getDate(7);
					if (freeDelivery != null && freeDelivery.equals("Yes"))
						freeDeliveryFlag = true;
					else
						freeDeliveryFlag = false;
					if (active != null && freeDelivery.equals("Yes"))
						activeFlag = true;
					else
						activeFlag = false;

					MovieList menuItem = new MovieList(movieId, title,
							(long) box_office, activeFlag, dateOfLaunch, genre,
							freeDeliveryFlag);
					movieList.add(menuItem);
				}
				favorite.setMovieList(movieList);
				// favorite.setTotal(getTotalPrice(userId, connection));
			}

			if (movieList.size() == 0) {
				throw new FavoritesEmptyException("Favorite is empty");
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return movieList;
	}

	private double getTotalPrice(long userId, Connection conn) {
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		double total = 0;
		List<MovieList> movieList = new ArrayList<MovieList>();
		try {
			if (conn != null) {
				Favoriutes favorite = new Favoriutes(movieList, 0);
				StringBuffer sqlBuffer = new StringBuffer();
				sqlBuffer
						.append("select count(ft_id) as tot_no_fav from movie_list m inner join favorite f on m.ml_id=f.ft_id;");

				System.out.println("SqlString:" + sqlBuffer.toString());

				preparedStatement = (PreparedStatement) conn
						.prepareStatement(sqlBuffer.toString());
				preparedStatement.setLong(1, userId);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					total = resultSet.getDouble(1);
				}
				System.out.println("Records fetched successfully");
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return total;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cognizant.moviecruiser.dao.FavoritesDao#removeFavoritesItem(long,
	 * long)
	 */
	@Override
	public void removeFavoritesItem(long userId, long movieId) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement;
		try {
			connection = ConnectionHandler.getConnection();
			if (connection != null) {
				preparedStatement = (PreparedStatement) connection
						.prepareStatement("delete from favorite where ft_us_id=? and  ft_pr_id=?");
				preparedStatement.setLong(1, userId);
				preparedStatement.setLong(2, movieId);
				preparedStatement.executeUpdate();
				System.out.println("Record deleted successfully");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
