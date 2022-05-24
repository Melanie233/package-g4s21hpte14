
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g4s21hpte14;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author Hernan
 */
public class ConexionDAO {
    
PreparedStatement ps;
Connection conexion = null;  
List<DatosDTO> listaDatosDTO = new ArrayList<>();
private void conectar(){

 String username = "user";
 String password ="rogeliogonzalez9";
 String url = "jdbc:mysql://localhost:3306/Isc4S21?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTim";
   

try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        
  conexion = (Connection) DriverManager.getConnection(url, username, password);
    

} catch (ClassNotFoundException ex) {
        Logger.getLogger(ConexionDAO.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(ConexionDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
}  


public boolean insertar(DatosDTO datos){
boolean estado = true;
try{
conectar();
PreparedStatement ps = conexion.prepareStatement("insert into datos(nombre, edad, correo)values(?,?,?)");
ps.setString(1, datos.getNombre());  
ps.setString(2, datos.getEdad());
ps.setString(3, datos.getCorreo());
ps.execute();

}catch(Exception ex){
estado = false;
}finally{
 cerrar();
}
  return estado; 
}


public boolean cargar (){
boolean estado = true;
DatosDTO datos;
try{
PreparedStatement ps = conexion.prepareStatement("select * from datos");
ResultSet resultado = ps.executeQuery();
while(resultado.next()){
datos = new DatosDTO();
datos.setId(resultado.getInt("id"));
datos.setNombre(resultado.getString("nombre"));
datos.setEdad(resultado.getString("edad"));
datos.setCorreo(resultado.getString("correo"));
listaDatosDTO.add(datos);
}
}catch(SQLException ex){
estado = false;

}finally{
 cerrar();
}
return estado;
}


public boolean actualizar(DatosDTO datos){
boolean estado = true;
try{
PreparedStatement ps = conexion.prepareStatement("update datos set nombre = ?, edad = ?, correo = ? where id ?  = ");
ps.setString(1, datos.getNombre());
ps.setString(2, datos.getEdad());
ps.setString(3, datos.getCorreo());
ps.setInt(4, datos.getId());
ps.execute();
}catch(SQLException ex){
estado = false;
}finally{
cerrar();
}
return estado;
}


public boolean eliminar (DatosDTO datos){
boolean estado = true;
try{
PreparedStatement ps = conexion.prepareStatement("delete from datos where id = ?");
ps.setInt(1, datos.getId());
ps.execute();
}catch(SQLException ex){
estado = false;
}finally{
cerrar();
}
return estado;
}


public List<DatosDTO>getDatos(){
return listaDatosDTO;
}


private void cerrar(){
    try {
        conexion.close();
    } catch (SQLException ex) {
        Logger.getLogger(ConexionDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}