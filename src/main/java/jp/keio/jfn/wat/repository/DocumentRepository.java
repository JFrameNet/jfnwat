package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DocumentRepository extends CrudRepository<Document, Long> {

}
