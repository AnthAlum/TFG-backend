package backend.merchants;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantRepository extends CrudRepository<Merchant, Long> {
    @Query("select m from Merchant m where m.email = ?1")
    Merchant findMerchantByEmail(String email);

    @Query("select m from Merchant m")
    Page<Merchant> searchMerchants(Pageable pageable);

    @Query("select m.id, m.name, m.email from Merchant m")
    List<String[]> getMerchantsSimplified();

    Page<Merchant> findByIdRole(Integer idRole, Pageable pageable);
    long countByIdRole(Integer idRole);
    Page<Merchant> findByEmailContains(String email, Pageable pageable);
    long countByEmailContains(String email);
    Page<Merchant> findByNameContains(String name, Pageable pageable);
    long countByNameContains(String name);
    Page<Merchant> findByPhoneContains(String phone, Pageable pageable);
    long countByPhoneContains(String phone);
}
