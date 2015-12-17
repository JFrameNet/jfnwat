package jp.keio.jfn.jfnwat.repository;

import jp.keio.jfn.wat.JFNWAT;
import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.repository.FrameRepository;
import jp.keio.jfn.wat.controller.FrameController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Alex Kabbach
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JFNWAT.class)
@ActiveProfiles("test")
public class FrameControllerTest {
    @Autowired
    FrameRepository frameRepository;

    @Test
    public void testFrames() {
        FrameController cont = new FrameController();
        assertEquals(1, cont.getAll());
    }
}
