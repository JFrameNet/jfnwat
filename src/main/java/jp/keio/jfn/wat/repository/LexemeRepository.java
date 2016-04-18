package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Lexeme;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jfn on 3/22/16.
 */
public interface LexemeRepository extends CrudRepository<Lexeme, Long> {
    Lexeme findById(int id);
    Lexeme findByName(String name);
}