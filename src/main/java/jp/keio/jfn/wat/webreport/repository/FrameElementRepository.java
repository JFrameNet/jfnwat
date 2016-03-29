package jp.keio.jfn.wat.webreport.repository;

import jp.keio.jfn.wat.webreport.domain.FrameElement;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jfn on 12/21/15.
 */
public interface FrameElementRepository extends CrudRepository<FrameElement, Long> {
    FrameElement findById(int id);
}
