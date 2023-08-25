package org.example;



import java.sql.*;

public class Cityrepositories {
    private final String CREATE_TABLE = "create table if not exists city(id serial primary key, name varchar, area bigint)";
    private final String FIND_BY_ID = "select * from city  where id = ?";
    private final String SAVE = "insert into city(name,area)values (?,?)";
    private final String DELETE_BY_ID = "delete from city where id = ?";
    private final String DELETE_ALL = "delete from city";


    public void CREATE_TABLE(){
        try {
            Connection connection= DdConection.getConnection();
            Statement statement= connection.createStatement();
            statement.executeUpdate(CREATE_TABLE);
            System.out.println("Creat!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public City findById(Long id) {
        City city = null;
        try (Connection connection = DdConection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                city = new City();
                city.setId(resultSet.getLong("id"));
                city.setName(resultSet.getString("name"));
                city.setArea(resultSet.getInt("area"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return city;
    }

    public void saveCity(City city) {
        try (Connection connection = DdConection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setLong(2, city.getArea());
            preparedStatement.executeUpdate();
            System.out.println("Saved successfully! ");
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
            System.out.println("Successfully Deleted!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}