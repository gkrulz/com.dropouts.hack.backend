package com.dropouts.hack.backend.database;

import com.datastax.driver.core.Session;

public class DatabaseCreator {
	
	private DatabaseManager databaseManager;
	private Session session;
	
	public DatabaseCreator(){
		databaseManager = DatabaseManager.getInstance();
		session = databaseManager.getSession();
	}
	
	public void createVolunteerDB(){
		session.execute(
                "CREATE TABLE IF NOT EXISTS volunteer.user (" +
                        "user_id text," +
                		"user_type text," +
                        "user_name text," +
                        "user_password text," +
                        "user_dob timestamp," +
                        "user_gender text," +
                        "user_location text," +
                        "user_occupation text," +
                        "user_pic blob," +
                        "PRIMARY KEY(user_id)" +
                        ");");
		
		
		session.execute(
				"CREATE TABLE IF NOT EXISTS volunteer.post_from_location (" +
						"post_id text," +
						"post_title text," +
						"post_pic blob," +
						"post_str text," +
						"post_org_id text," +
						"post_org_name text," +
						"post_location text," +
						"post_timestamp timestamp," +
						"PRIMARY KEY(post_location, post_timestamp))"+
				"WITH CLUSTERING ORDER BY (post_timestamp DESC);");
		
		session.execute(
				"CREATE TABLE IF NOT EXISTS volunteer.post_from_org (" +
						"post_id text," +
						"post_title text," +
						"post_pic blob," +
						"post_str text," +
						"post_org_id text," +
						"post_org_name text," +
						"post_location text," +
						"post_timestamp timestamp," +
						"PRIMARY KEY(post_org_id, post_timestamp))"+
				"WITH CLUSTERING ORDER BY (post_timestamp DESC);");
		
		session.execute(
				"CREATE TABLE IF NOT EXISTS volunteer.volunteer_work_from_user_id (" +
						"user_id text," +
						"post_id text," +
						"post_title text," +
						"post_pic blob," +
						"post_str text," +
						"post_org_id text," +
						"post_org_name text," +
						"post_location text," +
						"attended_timestamp timestamp," +
						"PRIMARY KEY(user_id, attended_timestamp))"+
				"WITH CLUSTERING ORDER BY (attended_timestamp DESC);");
		
		session.execute(
				"CREATE TABLE IF NOT EXISTS volunteer.cities_from_district (" +
						"district text," +
						"city text," +
						"PRIMARY KEY(district, city));");
		
		session.execute(
				"CREATE TABLE IF NOT EXISTS volunteer.district (" +
						"partition_id text," +
						"district text," +
						"PRIMARY KEY(partition_id, district));");
	}
	
	public void create() {
		
		session.execute(
                "CREATE TABLE IF NOT EXISTS share_good.user"
                + " (" +
                        "user_id text," +
                        "user_name text," +
                        "user_password text," +
                        "user_dob timestamp," +
                        "user_gender text," +
                        "user_location text," +
                        "user_occupation text," +
                        "user_pic blob," +
                        "PRIMARY KEY(user_id)" +
                        ");");
		
		
		session.execute(
				"CREATE TABLE IF NOT EXISTS share_good.post_from_user_id (" +
						"post_id text," +
						"post_title text," +
						"post_pic blob," +
						"post_str text," +
						"post_user_id text," +
						"post_user_name text," +
						"post_user_pic blob," +
						"post_location text," +
						"post_timestamp timestamp," +
						"PRIMARY KEY(post_org_id, post_timestamp))"+
				"WITH CLUSTERING ORDER BY (post_timestamp DESC);");
		
		
		session.execute(
				"CREATE TABLE IF NOT EXISTS share_good.user_following_users (" +
						"user_id text," +
						"folliwing_user_id text," +
						"PRIMARY KEY(user_id, folliwing_user_id));");
		
	}
}
