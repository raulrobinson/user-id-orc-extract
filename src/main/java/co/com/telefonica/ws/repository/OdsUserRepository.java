package co.com.telefonica.ws.repository;

import co.com.telefonica.ws.domain.OdsUser;
import co.com.telefonica.ws.dto.DetalleDocumentosDto;
import co.com.telefonica.ws.dto.DetalleDocumentosFsBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface OdsUserRepository extends JpaRepository<OdsUser, String> {

    //@Query("SELECT e FROM DetalleDocumentosFsBase e WHERE e.loadDate = :loadDate ORDER BY e.idType, e.idNumber")
    //Page<OdsUser> findByLoadDate(Date loadDate, Pageable pageable);

    @Query(value = "SELECT * FROM DWHODS.DETALLE_DOCUMENTOS_FS_BASE WHERE LOAD_DATE = :loadDate ORDER BY ID_TYPE, ID_NUMBER", nativeQuery = true)
    Page<OdsUser> findByLoadDate(Date loadDate, Pageable pageable);

    // @Query(value = "SELECT " +
    //         "ROW_NUMBER() OVER (ORDER BY load_date) AS id, " +
    //         "id_type, " +
    //         "id_number, " +
    //         "load_date " +
    //         "FROM DWHODS.DETALLE_DOCUMENTOS_FS_BASE " +
    //         "WHERE load_date = :loadDate",
    //         countQuery = "SELECT COUNT(*) FROM DWHODS.DETALLE_DOCUMENTOS_FS_BASE WHERE load_date = :loadDate",
    //         nativeQuery = true)
    // Page<OdsUser> findUsersByLoadDate(Date loadDate, Pageable pageable);

    // @Query(value = "SELECT * FROM DWHODS.DETALLE_DOCUMENTOS_FS_BASE " +
    //         "WHERE LOAD_DATE = :loadDate " +
    //         "ORDER BY ID_TYPE, ID_NUMBER " +
    //         "OFFSET :offset ROWS FETCH NEXT 100 ROWS ONLY", nativeQuery = true)
    // List<OdsUser> findInGroupsOf100ByLoadDate(Date loadDate, int offset);
    //
    // @Query(value = "SELECT " +
    //         "ROW_NUMBER() OVER (ORDER BY load_date) AS id, " +
    //         "id_type, " +
    //         "id_number, " +
    //         "load_date " +
    //         "FROM DWHODS.DETALLE_DOCUMENTOS_FS_BASE " +
    //         "WHERE LOAD_DATE = :loadDate " +
    //         //"ORDER BY ID_TYPE, ID_NUMBER " //+
    //         "OFFSET :offset ROWS FETCH NEXT 3 ROWS ONLY"
    //         //countQuery = "SELECT COUNT(*) FROM DWHODS.DETALLE_DOCUMENTOS_FS_BASE WHERE load_date = :loadDate",
    //         , nativeQuery = true)
    // List<OdsUser> findInGroupsOf100ByLoadDateId(Date loadDate, int offset);

}
