package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Frame;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Alex Kabbach
 */
public interface FrameRepository extends CrudRepository<Frame, Long> {
	List<Frame> findByName(String name);
}