package co.com.telefonica.ws.persistence;

import co.com.telefonica.ws.persistence.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface Users repository.
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    /**
     * Count users by date integer.
     *
     * @param loadDate the load date
     * @return the integer
     */
    @Query(value = "SELECT COUNT(*) " +
            "FROM DWHODS.USERS " +
            "WHERE TRUNC(LOAD_DATE) = TO_DATE(:loadDate, 'YYYY-MM-DD')",
            nativeQuery = true)
    Integer countUsersByDate(String loadDate);

}
