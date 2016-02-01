package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Corpus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CorpusRepository extends CrudRepository<Corpus, Long> {

}
