package json;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DatabaseIteratorExample {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/test";
        String username = "postgres";
        String password = "isha@123";
        ResultSet resultSet = null;
        ResultSet currentRecord = null;
        int offset = 0;
        int batchSize = 4;
        int j = 0;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            int i = 0;
            boolean fetchRecords = true;
            String query = "SELECT * FROM users ";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            Iterator<ResultSet> resultSetIterator = new ResultSetIterator(resultSet);

            while (resultSetIterator.hasNext()) {

                currentRecord = resultSetIterator.next();

                int columnCount = currentRecord.getMetaData().getColumnCount();
                while (currentRecord.next()) {
                    Map<String, Object> dataMap = new HashMap<>(columnCount);
                    for (int h = 1; h <= columnCount; h++) {
                        String columnKey = resultSet.getMetaData().getColumnName(h);
                        Object columnValue = resultSet.getObject(h);
                        dataMap.put(columnKey, columnValue);
                    }
                    System.out.println(dataMap);
                    // ... do something with the data
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null && currentRecord != null) {
                    currentRecord.close();
                    resultSet.close();
                    System.out.println(resultSet.isClosed());
                    System.out.println(currentRecord.isClosed());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

class ResultSetIterator implements Iterator<ResultSet> {
    private final ResultSet resultSet;

    public ResultSetIterator(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public boolean hasNext() {
        try {
            return !resultSet.isClosed() && resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ResultSet next() {
        return resultSet;
    }
}
