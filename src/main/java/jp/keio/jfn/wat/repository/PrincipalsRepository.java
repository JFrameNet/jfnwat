package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Principals;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jfn on 2/24/16.
 */
public interface PrincipalsRepository extends CrudRepository<Principals, Long> {
    Principals findByUser(String user);
}
