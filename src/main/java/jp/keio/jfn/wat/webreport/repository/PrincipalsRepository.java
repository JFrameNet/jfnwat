package jp.keio.jfn.wat.webreport.repository;

import jp.keio.jfn.wat.webreport.domain.Principals;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jfn on 2/24/16.
 */
public interface PrincipalsRepository extends CrudRepository<Principals, Long> {
    Principals findByUser(String user);
}
