package json;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class DataLookupImpl {
    private static final Logger logger = LoggerFactory.getLogger(DataLookupImpl.class);
    private final String dbDriverClassName;
    private final String url;
    private final String username;
    private final String password;

    public DataLookupImpl(String dbDriverClassName, String url, String username, String password) {
        this.dbDriverClassName = dbDriverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
    }


    public Iterator<List<Map<String, Object>>> lookupInBatches(String sqlQuery, Map<String, Object> namedParameters, Integer batchSize) {
        logger.info("Executing :: DataLookupImpl.lookupInBatches");
        Iterator<List<Map<String, Object>>> hello = fetchDataIteratorBatch(sqlQuery,batchSize,createConnection());
        logger.info("Exiting :: DataLookupImpl.lookupInBatches");
        return hello;
    }
    private Iterator<List<Map<String, Object>>> fetchDataIteratorBatch(String sqlQuery, Integer batchSize, Connection connection){
        Iterator<List<Map<String, Object>>> listIterator = null;
        List<List<Map<String, Object>>> dataList = new ArrayList<>();
        List<Map<String ,Object>> data = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int offset = 0;
        int total = 0;
        try {
            String query = sqlQuery + " ORDER BY email OFFSET ? FETCH FIRST ? ROWS ONLY";
            statement = connection.prepareStatement(query);
            while (offset <= total) {
                statement.setInt(1, offset);
                statement.setInt(2, batchSize);
                resultSet = statement.executeQuery();
                int columnCount = resultSet.getMetaData().getColumnCount();
                while (resultSet.next()) {
                    Map<String, Object> dataMap = new HashMap<>(columnCount);
                    for (int i = 1; i <= columnCount; i++) {
                        String columnKey = resultSet.getMetaData().getColumnName(i);
                        Object columnValue = resultSet.getObject(i);
                        dataMap.put(columnKey, columnValue);
                    }
                    total++;
                    data.add(dataMap);
                }
                if (offset < total) {
                    offset += batchSize;
                } else {
                    break;
                }
            }
            dataList.add(data);
            logger.info("data{}",data.size());
            logger.info("dataList{}",dataList.size());
            listIterator = dataList.iterator();
            while (listIterator.hasNext()){
                logger.info("listIterator=>{}",listIterator.next());
            }
            data.clear();
            dataList.clear();
            logger.info("data==>>{}",data);
            logger.info("dataList===>>>>{}",dataList);
            logger.info("Data Stored in list!!");
        } catch (SQLException e) {
            logger.error("Error while executing query!!", e);
        } finally {
            close(connection, statement, resultSet);
            logger.info("Connection Closed!!");
        }
        return listIterator;
    }

    private List<Map<String, Object>> fetchDataWithBatch(String sqlQuery, Integer batchSize, Connection connection) {
        logger.info("Executing :: DataLookupImpl.fetchDataWithBatch, query: {}, batchSize:{}", sqlQuery, batchSize);
        int total = 0;
        int offset = 0;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        List<Map<String, Object>> data = new ArrayList<>();
        try {
            String query = sqlQuery + " ORDER BY email OFFSET ? FETCH FIRST ? ROWS ONLY";
            statement = connection.prepareStatement(query);
            while (offset <= total) {
                statement.setInt(1, offset);
                statement.setInt(2, batchSize);
                resultSet = statement.executeQuery();
                int columnCount = resultSet.getMetaData().getColumnCount();
                while (resultSet.next()) {
                    Map<String, Object> dataMap = new HashMap<>(columnCount);
                    for (int i = 1; i <= columnCount; i++) {
                        String columnKey = resultSet.getMetaData().getColumnName(i);
                        Object columnValue = resultSet.getObject(i);
                        dataMap.put(columnKey, columnValue);
                    }
                    total++;
                    data.add(dataMap);
                }
                if (offset < total) {
                    offset += batchSize;
                } else {
                    break;
                }
            }
            logger.info("Data Stored in list!!");
        } catch (SQLException e) {
            logger.error("Error while executing query!!", e);
        } finally {
            close(connection, statement, resultSet);
            logger.info("Connection Closed!!");
        }
        logger.info("Exiting :: DataLookupImpl.fetchDataWithBatch");
        return data;
    }

    private List<Map<String, Object>> fetchAllData(String sqlQuery, Connection connection) {
        logger.info("Executing :: DataLookupImpl.fetchAllData, query: {}", sqlQuery);
        List<Map<String, Object>> dataList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> dataMap = new HashMap<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    String columnKey = resultSet.getMetaData().getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    dataMap.put(columnKey, columnValue);
                }
                dataList.add(dataMap);
            }
            logger.info("Data Stored in list!!");
        } catch (SQLException e) {
            logger.error("Error while executing query.", e);
        } finally {
            close(connection, statement, resultSet);
            logger.info("Connection Closed!!");
        }
        logger.info("Exiting :: DataLookupImpl.fetchAllData");
        return dataList;
    }
    private Iterator<List<Map<String, Object>>> fetchAll(String sqlQuery, Connection connection){
        Iterator<List<Map<String, Object>>> listIterator = null;
        List<List<Map<String, Object>>> dataList = new ArrayList<>();
        List<Map<String ,Object>> data = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()){
                Map<String, Object> dataMap = new HashMap<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    String columnKey = resultSet.getMetaData().getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    dataMap.put(columnKey, columnValue);
                }
                data.add(dataMap);
            }

        }catch (SQLException e){
            logger.error("Error while executing query.", e);
        }
        return null;
    }

    private Connection createConnection() {
        Connection connection;
        try {
            Class.forName(dbDriverClassName);
            connection = DriverManager.getConnection(url, username, password);
            logger.info("Connection created!!");
        } catch (ClassNotFoundException e) {
            logger.error("Class not found with classname : {} ", dbDriverClassName);
            throw new IllegalArgumentException("Class not found", e);
        } catch (SQLException e) {
            logger.error("Error while creating connection with url : {} ", url);
            throw new IllegalArgumentException("Error while creating connection", e);
        }
        return connection;
    }

    public static void close(Connection conn, Statement stmt, ResultSet result) {
        closeR(result);
        closeS(stmt);
        closeC(conn);
    }

    private static void closeC(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                LoggerFactory.getLogger(DataLookupImpl.class).error("Error closing ResultSet", e);
            }

        }
    }

    private static void closeS(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                LoggerFactory.getLogger(DataLookupImpl.class).error("Error closing DB Statement", e);
            }

        }
    }

    private static void closeR(ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (Exception e) {
                LoggerFactory.getLogger(DataLookupImpl.class).error("Error closing DB ResultSet", e);
            }

        }
    }
}
