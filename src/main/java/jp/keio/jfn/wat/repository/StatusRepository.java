package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface StatusRepository extends CrudRepository<Status, Long> {

}
