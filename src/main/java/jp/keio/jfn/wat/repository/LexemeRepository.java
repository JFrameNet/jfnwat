package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Lexeme;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jfn on 3/22/16.
 */
public interface LexemeRepository extends CrudRepository<Lexeme, Long> {
    Lexeme findById(int id);
    List<Lexeme> findByName(String name);
}