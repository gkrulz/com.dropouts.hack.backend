package com.dropouts.hack.backend.database;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.google.gson.Gson;

/**
 * Created by ReaperXoX on 4/7/2015.
 */
public class CassandraTest {
	
	private static DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static Session session = databaseManager.getSession();
	
	/***
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		CassandraTest test = new CassandraTest();

		session.execute("DROP KEYSPACE IF EXISTS volunteer;");

		session.execute("CREATE KEYSPACE IF NOT EXISTS volunteer WITH replication " +
		 "= {'class':'SimpleStrategy', 'replication_factor':1};");
		
		DatabaseCreator databaseCreator = new DatabaseCreator();
		databaseCreator.createVolunteerDB();
		
		test.insertuserData();

		ResultSet results = session.execute("SELECT * FROM volunteer.user");
		for (Row row : results) {
			System.out.println(
					row.getString("user_id") + " " + row.getString("user_name") + " " + row.getString("user_password")
							+ " " + row.getDate("user_dob") + " " + row.getString("user_gender") + " "
							+ row.getString("user_location") + " " + row.getString("user_occupation"));

		}
		System.out.println("Done");
		session.close();
		databaseManager.closeCluster();
	}
	
	public void insertuserData(){

		String[] names = { "Sam Perera", "Mufthaz Muhammad", "Vithushan Manoraj", "Leon Wirasinge", "Jane Lee",
				"Ann Taylor", "Lois Lane", "Lana Lang", "Kate Austen", "Thilini Nimesha" };
		String[] genders = { "Male", "Male", "Male", "Male", "Female", "Female", "Female", "Female", "Female",
				"Female" };
		String[] locations = {"colombo", "Galle", "Galle", "Galle", "Galle", "colombo", "colombo", "colombo", "colombo", "Gampaha"};

		for (int i = 0; i < names.length; i++) {
			session.execute(
					"INSERT INTO volunteer.user (user_id, user_name, user_password, user_dob, user_gender, user_location, user_occupation) "
							+ "VALUES (" + 
							"'"+(i+1)+"'," + 
							"'"+names[i]+"'," + 
							"'pass"+i+"'," + 
							"'1981-0"+(i+1)+"-12 00:00:00'," + 
							"'"+genders[i]+"',"+ 
							"'"+locations[i]+"'," + 
							"'volunteer'" + ");");

		}
	}
}
