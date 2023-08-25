package org.example;

import java.sql.*;

public class Countryrepositories {
    private final String CREATE_TABLE = "create table if not exists country(id serial primary key,name varchar(250),area bigint)";
    private final String FIND_BY_ID = "select * from country where id = ?";
    private final String SAVE = "insert into country(name,area)values (?,?)";
    private final String DELETE_BY_ID = "delete from country where id = ?";
    private final String DELETE_ALL = "delete from country";


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

    public Country findById(Long id) {
        Country country = null;
        try (Connection connection = DdConection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                country = new Country();
                country.setId(resultSet.getLong("id"));
                country.setName(resultSet.getString("name"));
                country.setArea(resultSet.getLong("area"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return country;
    }

    public void saveCountry(Country country) {
        try (Connection connection = DdConection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, country.getName());
            preparedStatement.setLong(2, country.getArea());
            preparedStatement.executeUpdate();
            System.out.println("save");
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}