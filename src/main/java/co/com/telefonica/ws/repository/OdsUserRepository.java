package co.com.telefonica.ws.repository;

import co.com.telefonica.ws.domain.OdsUser;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface OdsUserRepository extends JpaRepository<OdsUser, String> {

    //@Query(value = "SELECT " +
    //        "ROW_NUMBER() OVER (ORDER BY load_date) AS id, " +
    //        "id_type, " +
    //        "id_number, " +
    //        "load_date " +
    //        "FROM DWHODS.DETALLE_DOCUMENTOS_FS_BASE " +
    //        "WHERE load_date = :loadDate",
    //        countQuery = "SELECT COUNT(*) FROM DWHODS.DETALLE_DOCUMENTOS_FS_BASE WHERE load_date = :loadDate",
    //        nativeQuery = true)
    //Page<OdsUser> findUsersByLoadDate(Date loadDate, Pageable pageable);

    @Query(value = "SELECT * " +
            "FROM (SELECT id_type, id_number, load_date FROM (SELECT id_type, id_number, load_date, ROWNUM AS rn, COUNT(*) OVER () AS total_registros " +
            "FROM DWHODS.DETALLE_DOCUMENTOS_FS_BASE " +
            "WHERE LOAD_DATE = TO_TIMESTAMP(:loadDate, 'YYYY-MM-DD') ORDER BY ID_NUMBER) WHERE rn >= :start AND rn <= :end)", nativeQuery = true)
    Page<OdsUser> findUsersByLoadDate(Date loadDate, int start, int end);

}
