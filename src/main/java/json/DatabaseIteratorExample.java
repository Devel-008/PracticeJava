package json;

import java.sql.*;
import java.util.Iterator;

public class DatabaseIteratorExample {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/test";
        String username = "postgres";
        String password = "isha@123";
        ResultSet resultSet = null;
        ResultSet currentRecord = null;
        int offset = 0;
        int batchSize = 4;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {


int i= 0;
while (offset <= i) {
    String query = "SELECT * FROM users ORDER BY email OFFSET "+offset + "  FETCH FIRST "+batchSize+"  ROWS ONLY";
    Statement statement = connection.createStatement();
    resultSet = statement.executeQuery(query);
    Iterator<ResultSet> resultSetIterator = new ResultSetIterator(resultSet);
    while (resultSetIterator.hasNext()) {
        currentRecord = resultSetIterator.next();
        // Process the current record
        String id = currentRecord.getString("email");
        String name = currentRecord.getString("username");
        // ... do something with the data

        System.out.println("ID: " + id + ", Name: " + name);
        i++;
    }
offset += batchSize;
} System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(resultSet != null && currentRecord != null)
                currentRecord.close();
                resultSet.close();
                System.out.println(resultSet.isClosed());
                System.out.println(currentRecord.isClosed());
            }catch (SQLException e){
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
