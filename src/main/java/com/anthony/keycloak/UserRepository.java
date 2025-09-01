package com.anthony.keycloak;

import org.keycloak.models.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final DatabaseConfigurationModel configurationModel;
    private final String tableFullName;
    private final Connection connection;
    public UserRepository(DatabaseConfigurationModel configurationModel, Connection connection){
        this.configurationModel = configurationModel;
        this.connection = connection;
        this.tableFullName = configurationModel.getTableFullName();
    }

    public UserEntity getUserByLogin(String login){
        try (PreparedStatement stmt = connection.prepareStatement(
                String.format("SELECT id, login FROM %s WHERE login = ?", tableFullName))) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserEntity user = new UserEntity(
                        rs.getInt("id"),
                        rs.getString("login")
                );
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserEntity getUserById(int id){
        try (PreparedStatement stmt = connection.prepareStatement(
                String.format("SELECT id, login FROM %s WHERE id = ?", tableFullName))) {
            stmt.setString(1, Integer.toString(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserEntity user = new UserEntity(
                        rs.getInt("id"),
                        rs.getString("login")
                );
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserPasswordByLogin(String login){
        try (PreparedStatement stmt = connection.prepareStatement(
                String.format("SELECT id, login FROM %s WHERE login = ?", tableFullName))) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserEntity> searchUsersByLogin(String login, Integer firstResult, Integer maxResults){
        List<UserEntity> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                String.format("SELECT id, login FROM %s WHERE login ILIKE ? order by id offset ? limit ?", tableFullName))) {
            stmt.setString(1, "%"+login+"%");
            stmt.setInt(2, firstResult);
            stmt.setInt(3, maxResults);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserEntity user = new UserEntity(
                        rs.getInt("id"),
                        rs.getString("login")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<UserEntity> searchUsersByName(String login, Integer firstResult, Integer maxResults){
        List<UserEntity> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                String.format("SELECT id, login FROM %s WHERE name ILIKE ? order by id offset ? limit ?", tableFullName))) {
            stmt.setString(1, "%"+login+"%");
            stmt.setInt(2, firstResult);
            stmt.setInt(3, maxResults);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserEntity user = new UserEntity(
                        rs.getInt("id"),
                        rs.getString("login")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
