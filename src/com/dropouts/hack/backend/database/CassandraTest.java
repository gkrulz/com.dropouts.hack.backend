package com.dropouts.hack.backend.database;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.google.gson.Gson;

/**
 * Created by ReaperXoX on 4/7/2015.
 */
public class CassandraTest {
    /***
     *
     * @param args
     */
    public static void main(String[] args) {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Session session = databaseManager.getSession();

        session.execute("DROP KEYSPACE IF EXISTS volunteer;");

        session.execute("CREATE KEYSPACE IF NOT EXISTS volunteer WITH replication " +
                "= {'class':'SimpleStrategy', 'replication_factor':1};");

        session.execute(
                "CREATE TABLE IF NOT EXISTS volunteer.user (" +
                        "user_id bigint PRIMARY KEY," +
                        "user_name text," +
                        "user_password text," +
                        "user_dob timestamp," +
                        "user_gender text," +
                        "user_location text," +
                        "user_occupation text" +
                        ");");

        
      
//        session.execute(
//                "INSERT INTO blood_world.character " +
//                        "VALUES (" +
//                        "756716f7-2e54-4715-9f00-91dcbea6cf50," +
//                        "'"+pos+"'," +
//                        "80," +
//                        "'Xtabay'," +
//                        "10000," +
//                        "true," +
//                        "{'name':'Holy Light'}" +
//                        ");");
//
        session.execute(
                "INSERT INTO volunteer.user (user_id, user_name, user_password, user_dob, user_gender, user_location, user_occupation) " +
                        "VALUES (" +
                        "001," +
                        "'Sam'," +
                        "'sam123'," +
                        "'1981-02-12 00:00:00'," +
                        "'Male'," +
                        "'Colombo'," +
                        "'engineer'" +
                        ");");

//        session.execute("UPDATE blood_world.character " +
//                "SET health = 24000 " +
//                "WHERE id = 756716f7-2e54-4715-9f00-91dcbea6cf60");

        ResultSet results = session.execute("SELECT * FROM volunteer.user");
        for (Row row : results) {
            System.out.println(row.getLong("user_id")+" "+row.getString("user_name")+" "+row.getString("user_password")+
            		" "+ row.getDate("user_dob")+" "+row.getString("user_gender")+" "+row.getString("user_location")+" "
            		+row.getString("user_occupation"));

        }
        System.out.println("Done");
        session.close();
        databaseManager.closeCluster();
    }
}
