package jp.keio.jfn.jfnwat.repository;

import jp.keio.jfn.wat.JFNWAT;
import jp.keio.jfn.wat.webreport.domain.Frame;
import jp.keio.jfn.wat.webreport.repository.FrameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.sql.Timestamp;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Alex Kabbach
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JFNWAT.class)
@ActiveProfiles("test")
public class FrameRepositoryTest {
	@Autowired FrameRepository frameRepository;

	@Test
	public void testFindByName(){
		Frame frame = new Frame();
		frame.setName("Test");
		frame.setCreatedBy("test");
		frame.setModifiedDate(new Timestamp(new java.util.Date().getTime()));

		assertNull(frame.getId());
		frameRepository.save(frame);
		assertNotNull(frame.getId());
		frameRepository.delete(frameRepository.findByName("Test").get(0));
	}
}
