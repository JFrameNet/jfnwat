package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jfn on 3/23/16.
 */
public interface WordRepositoryCustom {
    List<KwicWord> findKwicwordLike(String wordElement);
}
