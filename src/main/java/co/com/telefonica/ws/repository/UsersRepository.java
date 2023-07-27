package co.com.telefonica.ws.repository;

import co.com.telefonica.ws.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    @Query(value = "SELECT " +
            "ROW_NUMBER() OVER (ORDER BY load_date) AS id, " +
            "id_type, " +
            "id_number, " +
            "load_date " +
            "FROM DWHODS.DETALLE_DOCUMENTOS_FS_BASE " +
            "WHERE load_date = TO_DATE(:loadDate, 'YYYY-MM-DD')", nativeQuery = true)
    List<Users> findUsersByLoadDateStr(String loadDate);

}
