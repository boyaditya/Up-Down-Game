package viewmodel;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.Tscore;
import model.TscoreModel;

public class TscoreProcess {
    private String error; // for storing error message
    private TscoreModel scoreModel; // a class for accessing query
    private ArrayList<Tscore> data; // for storing result from query

    // Constructor
    public TscoreProcess() {
        try {
            // Initializing Table Object and List
            scoreModel = new TscoreModel();
            data = new ArrayList<Tscore>();
        } catch (Exception e) {
            error = e.toString();
        }
    }

    // Read Data From Database And Return It As DefaultTableModel
    public DefaultTableModel readData() {
        DefaultTableModel tableData = null;
        try {
            // Getting All Data From screrience Table
            Object[] column = { "Username", "Score", "Up", "Down" };
            tableData = new DefaultTableModel(null, column);
            scoreModel.getData();
            while (scoreModel.getResult().next()) {
                // Taking All Query Result
                Tscore profile = new Tscore();
                profile.setUsername(scoreModel.getResult().getString(1));
                profile.setScore(scoreModel.getResult().getInt(2));
                profile.setUp(scoreModel.getResult().getInt(3));
                profile.setDown(scoreModel.getResult().getInt(4));

                Object[] row = new Object[4];
                row[0] = profile.getUsername();
                row[1] = profile.getScore();
                row[2] = profile.getUp();
                row[3] = profile.getDown();

                // Add Data to List
                tableData.addRow(row);
                data.add(profile);
            }
            // Close Result
            scoreModel.closeResult();

            // Close Database Connection
            scoreModel.closeConnection();
        } catch (Exception e) {
            error = e.toString();
        }

        return tableData;
    }

    // Check Username Existence
    public boolean checkUsername(String username) {
        boolean result = false;
        try {
            // Getting Specific Data From Database
            scoreModel.getDataByUsername(username);

            while (scoreModel.getResult().next()) {
                result = true;
            }

        } catch (Exception e) {
            error = e.toString();
        }

        return result;
    }

    // Get Data From List
    public void getDataByUsername(String username) {
        try {
            // Getting Specific Data From Database
            scoreModel.getDataByUsername(username);

            while (scoreModel.getResult().next()) {
                // Taking All Query Result
                Tscore profile = new Tscore();
                profile.setUsername(scoreModel.getResult().getString(1));
                profile.setScore(scoreModel.getResult().getInt(2));
                profile.setUp(scoreModel.getResult().getInt(3));
                profile.setDown(scoreModel.getResult().getInt(4));

                // Add Data to List
                data.add(profile);
            }

            // Close Result
            scoreModel.closeResult();

            // Close Database Connection
            scoreModel.closeConnection();
        } catch (Exception e) {
            error = e.toString();
        }
    }

    // Insert Data To Database
    public void insertData(String username, int score, int up, int down) {
        try {
            // Insert Data To Database
            Tscore profile = new Tscore();
            profile.setUsername(username);
            profile.setScore(score);
            profile.setUp(up);
            profile.setDown(down);

            if (checkUsername(username)) {
                scoreModel.updateData(profile);
            } else {
                scoreModel.insertData(profile);
            }

            // Close Database Connection
            scoreModel.closeConnection();
        } catch (Exception e) {
            error = e.toString();
        }
    }

    // Update Data To Database
    public void updateData(String username) {
        try {
            // Update Data To Database
            Tscore profile = new Tscore();
            profile.setUsername(username);
            profile.setScore(getScore(0));
            profile.setUp(getUp(0));
            profile.setDown(getDown(0));

            scoreModel.updateData(profile);

            // Close Database Connection
            scoreModel.closeConnection();
        } catch (Exception e) {
            error = e.toString();
        }
    }

    // Save Data To Database
    public void saveData(String username, int score, int up, int down) {
        try {
            Tscore profile = new Tscore();
            profile.setUsername(username);
            profile.setScore(score);
            profile.setUp(up);
            profile.setDown(down);

            if (checkUsername(username)) {
                scoreModel.updateData(profile);
            } else {
                scoreModel.insertData(profile);
            }

            // Close Database Connection
            scoreModel.closeConnection();

        } catch (Exception e) {
            error = e.toString();
        }
    }

    public String getUsername(int i) {
        return data.get(i).getUsername();
    }

    public int getScore(int i) {
        return data.get(i).getScore();
    }

    public int getUp(int i) {
        return data.get(i).getUp();
    }

    public int getDown(int i) {
        return data.get(i).getDown();
    }

    public int getSize() {
        return data.size();
    }

    public String getError() {
        return error;
    }

}
