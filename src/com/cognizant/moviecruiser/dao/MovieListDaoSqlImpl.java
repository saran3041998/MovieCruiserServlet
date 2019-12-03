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

import com.cognizant.moviecruiser.model.MovieList;

/**
 * @Created By Saranya 760862
 *
 */
public class MovieListDaoSqlImpl implements MovieListDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cognizant.moviecruiser.dao.MovieListDao#getMovieListAdmin()
	 */
	@Override
	public List<MovieList> getMovieListAdmin() {
		ConnectionHandler ch = new ConnectionHandler();
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		List<MovieList> movielist = new ArrayList<MovieList>();
		ResultSet resultSet;
		boolean activeFlag, freeDeliveryFlag;
		try {
			conn = ch.getConnection();
			if (conn != null) {
				preparedStatement = conn
						.prepareStatement("select ml_id,ml_title,ml_active,ml_date_of_launch,ml_box_office,ml_genre,ml_has_teaser from movie_list");
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {

					int id = resultSet.getInt("ml_id");
					String title = resultSet.getString("ml_title");
					Date dateOfLaunch = resultSet.getDate("ml_date_of_launch");
					String active = resultSet.getString("ml_active");
					float box_office = resultSet.getLong("ml_box_office");
					String genre = resultSet.getString("ml_genre");
					String freeDelivery = resultSet.getString("ml_has_teaser");
					if (freeDelivery != null && freeDelivery.equals("Yes")) {
						freeDeliveryFlag = true;
					} else {
						freeDeliveryFlag = false;
					}
					if (active != null && active.equals("Yes")) {
						activeFlag = true;
					} else {
						activeFlag = false;
					}
					MovieList movieList = new MovieList(id, title,
							(long) box_office, activeFlag, dateOfLaunch, genre,
							freeDeliveryFlag);
					System.out.println(movieList);
					movielist.add(movieList);
				}
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

		return movielist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cognizant.moviecruiser.dao.MovieListDao#getMovieItemlistCustomer()
	 */
	@Override
	public List<MovieList> getMovieItemlistCustomer() {
		// TODO Auto-generated method stub
		Connection connection = ConnectionHandler.getConnection();
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		List<MovieList> movielist = new ArrayList<MovieList>();
		if (connection != null) {
			try {
				preparedStatement = connection
						.prepareStatement("select * from movie_list where ml_active='Yes' and ml_date_of_launch <= now()");
				resultSet = preparedStatement.executeQuery();
				boolean activeFlag, freeDeliveryFlag;
				Date date_of_launch;
				while (resultSet.next()) {
					int movieId = resultSet.getInt(1);
					String title = resultSet.getString(2);
					long box_office = resultSet.getLong(3);
					String active = resultSet.getString(4);
					date_of_launch = resultSet.getDate(5);
					String genre = resultSet.getString(6);
					String freeDelivery = resultSet.getString(7);
					if (active != null && active.equals("Yes"))
						activeFlag = true;
					else
						activeFlag = false;
					if (freeDelivery != null && freeDelivery.equals("Yes"))
						freeDeliveryFlag = true;
					else
						freeDeliveryFlag = false;
					MovieList movieList = new MovieList(movieId, title,
							(long) box_office, activeFlag, date_of_launch,
							genre, freeDeliveryFlag);
					movielist.add(movieList);
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
		return movielist;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cognizant.moviecruiser.dao.MovieListDao#modifyMovieList(com.cognizant
	 * .moviecruiser.model.MovieList)
	 */
	@Override
	public void modifyMovieList(MovieList movieList) {
		// TODO Auto-generated method stub
		Connection connection = ConnectionHandler.getConnection();
		String sql = "update movie_list set ml_title = ?,ml_box_office =?,ml_active= ?,ml_date_of_launch= ?,ml_genre =?,ml_has_teaser=? where ml_id =?";
		try {
			if (connection != null) {
				PreparedStatement preparestatement = connection
						.prepareStatement(sql);
				preparestatement.setString(1, movieList.getTitle());
				preparestatement.setLong(2, movieList.getBox_office());
				if (movieList.isActive()) {
					preparestatement.setString(3, "Yes");
				} else {
					preparestatement.setString(3, "No");
				}

				preparestatement.setDate(4, new java.sql.Date(movieList
						.getDateOfLaunch().getTime()));
				preparestatement.setString(5, movieList.getGenre());
				if (movieList.getHasTeaser()) {
					preparestatement.setString(6, "Yes");
				} else {
					preparestatement.setString(6, "No");

				}
				preparestatement.setLong(7, movieList.getId());
				preparestatement.executeUpdate();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}

		finally {
			try {
				connection.close();
			} catch (Exception e)

			{
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cognizant.moviecruiser.dao.MovieListDao#getMovie(long)
	 */
	@Override
	public MovieList getMovie(long id) {
		ConnectionHandler ch = new ConnectionHandler();
		Connection connection = ch.getConnection();
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		MovieList movieList = null;
		if (connection != null) {
			try {
				preparedStatement = connection
						.prepareStatement("select * from movie_List where ml_id=?");
				preparedStatement.setLong(1, id);

				resultSet = preparedStatement.executeQuery();
				boolean activeFlag, freeDeliveryFlag;
				Date date_of_launch;
				while (resultSet.next()) {
					String title = resultSet.getString(2);
					long box_office = resultSet.getLong(3);
					String active = resultSet.getString(4);
					date_of_launch = resultSet.getDate(5);
					String genre = resultSet.getString(6);
					String freeDelivery = resultSet.getString(7);
					if (active != null && active.equals("Yes"))
						activeFlag = true;
					else
						activeFlag = false;
					if (freeDelivery != null && freeDelivery.equals("Yes"))
						freeDeliveryFlag = true;
					else
						freeDeliveryFlag = false;
					movieList = new MovieList(id, title, (long) box_office,
							activeFlag, date_of_launch, genre, freeDeliveryFlag);
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
		return movieList;
	}

}
