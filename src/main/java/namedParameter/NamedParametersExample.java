package namedParameter;

import java.sql.*;
import java.util.*;

public class NamedParametersExample {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/test";
        String username = "postgres";
        String password = "isha@123";

        String query = "SELECT * FROM practice WHERE age <= ? AND city = ?";
        Map<String, Object> data = new HashMap<>();
        data.put("age", 23);
        data.put("city", "New York");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Set named parameters
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.prepareStatement(query);
            Object value = getValueByIndex(data, 0);
            Object ages = getValueByIndex(data, 1);
            statement.setInt(1, (Integer) ages);
            statement.setString(2, (String) value);
            // Execute the query
            resultSet = statement.executeQuery();
            // Process the results
            while (resultSet.next()) {
                // Handle each row of the result set
                int firstName = resultSet.getInt("id");
                String lastName = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String city = resultSet.getString("city");

                System.out.println(firstName + " " + lastName + " - Age: " + age + " - City: " + city);
            }
          /*  for (int index = 0; index<data.size();index++) {
                Object values = getValueByIndex(data, index);
                System.out.println("Value at index " + index + ": " + values);
            }*/

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    statement.close();
                    connection.close();
                    if (resultSet.isClosed() && statement.isClosed() && connection.isClosed()){
                        System.out.println("Connection closed!!");}
                }
            }                catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static <K, V> V getValueByIndex(Map<K, V> map, int index) {
        if (index < 0 || index >= map.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Collection<V> values = map.values();
        List<V> valueList = new ArrayList<>(values);
        return valueList.get(index);
    }

}
