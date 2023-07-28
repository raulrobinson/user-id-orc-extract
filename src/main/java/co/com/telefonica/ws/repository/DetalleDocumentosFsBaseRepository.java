package co.com.telefonica.ws.repository;

import co.com.telefonica.ws.dto.DetalleDocumentosFsBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface DetalleDocumentosFsBaseRepository extends JpaRepository<DetalleDocumentosFsBase, String> {

    /* >>>>>>>>>>>>>>>>>>>>>>>>>> WORKING 15 */

    @Query("SELECT e FROM DetalleDocumentosFsBase e WHERE e.loadDate = :loadDate ORDER BY e.idType, e.idNumber")
    Page<DetalleDocumentosFsBase> findByLoadDate(Date loadDate, Pageable pageable);

    /* *************************************** */
}

