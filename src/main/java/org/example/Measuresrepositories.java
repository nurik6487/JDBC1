package org.example;

import java.sql.*;

public class Measuresrepositories {
    private final String CREATE_TABLE = "create table if not exists measures(id serial primary key,name varchar(250),age bigint)";
    private final String FIND_BY_ID = "select * from measures  where id = ?";
    private final String SAVE = "insert into measures(name,age)values (?,?)";
    private final String DELETE_BY_ID = "delete from measures where id = ?";
    private final String DELETE_ALL = "delete from measures";

    public void CREATE_TABLE() {

        try {
            Connection connection = DdConection.getConnection();
            Statement statement = connection.createStatement();
            {
                statement.executeUpdate(CREATE_TABLE);
                System.out.println("Creat!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Measures findById(Long id) {
        Measures measures = null;
        try (Connection connection = DdConection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                measures =new Measures();
                measures.setId(resultSet.getLong("id"));
                measures.setName(resultSet.getString("name"));
                measures.setAge(resultSet.getInt("age"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return measures;
    }

    public void saveMeasures (Measures measures ) {
        try (Connection connection = DdConection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, measures.getName());
            preparedStatement.setInt(2,measures.getAge());
            preparedStatement.executeUpdate();
            System.out.println("Successfully Saved!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        try (Connection connection = DdConection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        try (Connection connection = DdConection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_ALL);
            System.out.println(" All Deleted successfully in spreadsheet!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}