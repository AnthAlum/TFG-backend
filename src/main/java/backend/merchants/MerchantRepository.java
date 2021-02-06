package backend.merchants;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends CrudRepository<Merchant, Long> {
    @Query("select m from Merchant m where u.email = :email")
    public Merchant findMerchantByEmail(String email);
}
