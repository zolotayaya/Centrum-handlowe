package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa {@code ManagerDB} odpowiada za operacje bazodanowe dotyczące obiektów klasy {@link Manager}.
 */
public class ManagerDB {

    /** Połączenie z bazą danych. */
    private static Connection connection;

    /** Lista wszystkich managerów wczytanych z bazy danych. */
    public static List<Manager> managers;

    /**
     * Konstruktor inicjalizujący połączenie z bazą danych i listę managerów.
     */
    public ManagerDB() {
        managers = new ArrayList<>();
        this.connection = Database.getConnection();
    }

    /**
     * Wczytuje wszystkich managerów z tabeli {@code Managers} w bazie danych
     * i dodaje ich do lokalnej listy {@code managers}.
     *
     * @throws SQLException jeśli wystąpi błąd podczas komunikacji z bazą danych
     */
    public void setManagersFromDB() throws SQLException {
        String sql = "SELECT * FROM Managers";
        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String depname = rs.getString("department");
            float income = rs.getFloat("income");
            float commission = rs.getFloat("commission");
            int experience = rs.getInt("experience");

            managers.add(new Manager(id, name, getDep(depname), income, commission, experience));
        }
    }

    /**
     * Wyszukuje obiekt {@link Department} na podstawie jego nazwy.
     *
     * @param depName nazwa działu
     * @return obiekt {@code Department}, jeśli znaleziono; w przeciwnym razie {@code null}
     */
    public Department getDep(String depName) {
        List<Department> departments = DepartmentDB.getDepartments();
        if (depName == null) return null;
        for (Department dep : departments) {
            if (depName.trim().equalsIgnoreCase(dep.getName().trim())) {
                return dep;
            }
        }
        return null;
    }

    /**
     * Aktualizuje dochód danego managera w bazie danych.
     *
     * @param manager obiekt {@code Manager}, którego dochód ma zostać zaktualizowany
     * @throws SQLException jeśli wystąpi błąd podczas zapisu do bazy danych
     */
    public static void updateManagerIncome(Manager manager) throws SQLException {
        String sql = "UPDATE Managers SET income = ? WHERE id = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setFloat(1, manager.getIncome());
        st.setInt(2, manager.getId());
        st.executeUpdate();
    }

    /**
     * Zwraca listę wszystkich managerów.
     *
     * @return lista obiektów {@code Manager}
     */
    public List<Manager> getManagers() {
        return managers;
    }

    /**
     * Wstawia nowego managera do bazy danych oraz dodaje go do lokalnej listy {@code managers}.
     *
     * @param manager obiekt {@code Manager}, który ma zostać dodany
     * @throws SQLException jeśli wystąpi błąd podczas dodawania do bazy danych
     */
    public static void insertManager(Manager manager) throws SQLException {
        String sql = "INSERT INTO Managers (name, department, income, commission, experience) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, manager.getName());
        st.setString(2, manager.getDepartment().getName());
        st.setFloat(3, manager.getIncome());
        st.setFloat(4, manager.getCommision());
        st.setInt(5, manager.getExperience());
        st.executeUpdate();

        managers.add(manager);
    }

    /**
     * Usuwa managera z bazy danych oraz z lokalnej listy {@code managers} na podstawie jego ID.
     *
     * @param id identyfikator managera do usunięcia
     * @throws SQLException jeśli wystąpi błąd podczas usuwania z bazy danych
     */
    public static void deleteManagerById(int id) throws SQLException {
        String sql = "DELETE FROM Managers WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();

        managers.removeIf(manager -> manager.getId() == id);
    }
}
