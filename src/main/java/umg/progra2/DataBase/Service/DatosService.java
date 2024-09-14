package umg.progra2.DataBase.Service;
import umg.progra2.DataBase.DAO.DatosDao;
import umg.progra2.DataBase.Model.tb_datos;
import java.sql.SQLException;
import java.util.List;

public class DatosService {
private DatosDao datosDao = new DatosDao();


    //insertar un nuevo dato
    public boolean insertarDato(tb_datos dato) {
        return datosDao.insertar(dato);
    }


     //obtener todos los registros
    public List<tb_datos> obtenerTodosLosDatos() {
        return datosDao.obtenerTodos();
    }


    //obtener un dato por su ID
    public tb_datos obtenerPorId(int id) throws SQLException {
        return datosDao.obtenerPorId(id);
    }


     //actualizar dato existente
    public boolean actualizarDato(tb_datos dato) {
        return datosDao.actualizar(dato);
    }


    //eliminar un dato por su código
    public boolean eliminarDato(int codigo) {
        return datosDao.eliminar(codigo);
    }

}
