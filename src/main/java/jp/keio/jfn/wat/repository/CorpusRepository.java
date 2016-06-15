package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Corpus;
import org.springframework.data.repository.CrudRepository;


public interface CorpusRepository extends CrudRepository<Corpus, Long> {
    Corpus findById(Integer id);
}
