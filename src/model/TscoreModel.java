package model;

import java.sql.SQLException;

public class TscoreModel extends DB {
    // constructor
    public TscoreModel() throws Exception, SQLException {
        super();
    }

    // get all data from table
    public void getData() {
        try {
            String query = "SELECT * FROM tscore ORDER by score DESC";
            createQuery(query);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // get data by username
    public void getDataByUsername(String username) {
        try {
            String query = "SELECT * FROM tscore WHERE username='" + username + "'";
            createQuery(query);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // insert data to table
    public void insertData(Tscore profile) {
        try {
            String query = "INSERT INTO tscore VALUES ('" + profile.getUsername() + "', '" + profile.getScore() + "', '"
                    + profile.getUp() + "', '" + profile.getDown() + "')";
            createUpdate(query);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // update data
    public void updateData(Tscore profile) {
        try {
            String query = "UPDATE tscore SET score=" + profile.getScore() + ", up=" + profile.getUp() + ", down=" + profile.getDown()
                    + " WHERE username='" + profile.getUsername() + "'";
            createUpdate(query);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}