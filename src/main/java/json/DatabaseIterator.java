package json;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseIterator {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/test";
        String username = "postgres";
        String password = "isha@123";
        ResultSet resultSet = null;
        int offset = 0;
        int batchSize = 4;
        int total = 0;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            boolean fetchRecords = true;
            String query = "SELECT * FROM users ORDER BY email OFFSET " + offset + " FETCH FIRST " + batchSize + " ROWS ONLY";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            int column = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()){
                Map<String,Object> dataMap = new HashMap<>(column);
                for (int i = 1; i<=column;i++){
                    String columnKey = resultSet.getMetaData().getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    dataMap.put(columnKey, columnValue);
                }
                total++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    System.out.println(resultSet.isClosed());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

