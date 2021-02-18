package backend.merchants;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends CrudRepository<Merchant, Long> {
    @Query("select m from Merchant m where m.email = :email")
    public Merchant findMerchantByEmail(String email);

    @Query("select m from Merchant m")
    public Page<Merchant> searchMerchants(Pageable pageable);
}
