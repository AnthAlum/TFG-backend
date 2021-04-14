package backend.clients;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    @Query("select c from Client c where c.email = ?1")
    List<Client> findClientByEmail(String email);

    @Query("select c from Client c")
    public Page<Client> searchClients(Pageable pageable);

    Page<Client> findByNameContains(String name, Pageable pageable);
    long countByNameContains(String name);
    Page<Client> findByEmailContains(String email, Pageable pageable);
    long countByEmailContains(String email);
    Page<Client> findByPhoneContains(String phone, Pageable pageable);
    long countByPhoneContains(String phone);
    Page<Client> findByCompanyContains(String company, Pageable pageable);
    long countByCompanyContains(String company);
}
