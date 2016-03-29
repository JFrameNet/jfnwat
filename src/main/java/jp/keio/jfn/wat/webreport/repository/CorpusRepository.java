package jp.keio.jfn.wat.webreport.repository;

import jp.keio.jfn.wat.webreport.domain.Corpus;
import org.springframework.data.repository.CrudRepository;


public interface CorpusRepository extends CrudRepository<Corpus, Long> {
    Corpus findById(Integer id);
}
