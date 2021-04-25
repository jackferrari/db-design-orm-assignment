package com.example.springtemplate.daos;

import com.example.springtemplate.models.User;

import java.sql.*;
import java.util.*;

public class EditorJdbcDao {
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String HOST = "localhost:3306";
    static final String SCHEMA = "db_journals";
    static final String CONFIG = "serverTimezone=UTC";
    static final String URL =
            "jdbc:mysql://"+HOST+"/"+SCHEMA+"?"+CONFIG;
    static final String USERNAME = "YOUR_USERNAME";
    static final String PASSWORD = "P@ssw0rd";

    static Connection connection = null;
    static PreparedStatement statement = null;
    String CREATE_EDITOR = "INSERT INTO editors VALUES (null, ?, null)";
    String FIND_ALL_EDITORS = "SELECT * FROM editors";
    String FIND_EDITOR_BY_ID = "SELECT * FROM editors WHERE id=?";
    String DELETE_EDITOR = "DELETE FROM editors WHERE id=?";
    String UPDATE_EDITOR = "UPDATE editors SET role=? WHERE id=?";

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public Editor findEditorById(Integer id) throws SQLException, ClassNotFoundException {
        Editor editor = null;
        connection = getConnection();
        statement = connection.prepareStatement(FIND_EDITOR_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            editor = new Editor(
                    resultSet.getString("role")
//                    resultSet.getString("password"),
//                    resultSet.getString("first_name"),
//                    resultSet.getString("last_name"),
//                    resultSet.getString("email"),
//                    resultSet.getString("date_of_birth")
            );
        }
        closeConnection(connection);
        return editor;
    }

    public Integer deleteEditor(Integer editorId) throws SQLException, ClassNotFoundException {
        Integer rowsDeleted = 0;
        connection = getConnection();
        statement = connection.prepareStatement(DELETE_EDITOR);
        statement.setInt(1, editorId);
        rowsDeleted = statement.executeUpdate();
        closeConnection(connection);
        return rowsDeleted;
    }

    public Integer updateEditor(Integer editorId, Editor newEditor) throws SQLException, ClassNotFoundException {
        Integer rowsUpdated = 0;
        connection = getConnection();
        statement = connection.prepareStatement(UPDATE_EDITOR);
        statement.setString(1, newEditor.getRole());
        statement.setInt(2, editorId);
        rowsUpdated = statement.executeUpdate();
        closeConnection(connection);
        return rowsUpdated;
    }

    public List<Editor> findAllEditors() throws ClassNotFoundException, SQLException {
        List<Editor> users = new ArrayList<User>();
        connection = getConnection();
        statement = connection.prepareStatement(FIND_ALL_EDITORS);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Edtior editor = new Editor(
                    resultSet.getString("role")
            );
            editors.add(editor);
        }
        closeConnection(connection);
        return editors;
    }
    public Integer createEditor(Editor editor)
            throws ClassNotFoundException, SQLException {
        Integer rowsUpdated = 0;
        connection = getConnection();
        statement = connection.prepareStatement(CREATE_EDITOR);
        statement.setString(1, editor.getRole());
        rowsUpdated = statement.executeUpdate();
        closeConnection(connection);
        return rowsUpdated;
    }
}

