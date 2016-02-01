package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Document;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jfn on 12/18/15.
 */
public interface DocumentRepository extends CrudRepository<Document, Long> {
    Document findById(Integer id);
}
