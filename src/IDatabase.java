import java.sql.*;

//Interface IDatabase
//Interface ini di bei nama IDatabase yang berarti awalan I sebagai Interface

public interface IDatabase{
    void view() throws SQLException ;
    void save() throws SQLException ;
    void update() throws SQLException ;
    void delete() throws SQLException ;
    void search() throws SQLException ;
}

