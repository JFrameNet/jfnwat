package jp.keio.jfn.jfnwat.controller;

import jp.keio.jfn.wat.JFNWAT;

import jp.keio.jfn.wat.controller.FrameController;
import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.repository.FrameRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

import static org.junit.Assert.*;
/**
 * Created by jfn on 2/12/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JFNWAT.class)
@ActiveProfiles("test")
public class FrameControllerTest {
    @Autowired
    FrameRepository frameRepository;

    private FrameController controller;

    Frame mainFrame;

    @Before
    public void setup () {
        controller = new FrameController();
        controller.setFrameRepository(frameRepository);

        mainFrame = new Frame();
        mainFrame.setName("frame");
        mainFrame.setCreatedBy("test");
        mainFrame.setModifiedDate(new Timestamp(new java.util.Date().getTime()));

        frameRepository.save(mainFrame);
    }

    @After
    public void after() {
        frameRepository.deleteAll();
        controller = null;
    }

    @Test
    public void testEmptyOrderFrames() {
        controller.setFilter("");
        controller.orderFrames();
        assertEquals(1, controller.getOrderedFrames().size());
    }

    @Test
    public void testOrderFrames() {
        Frame frame = new Frame();
        frame.setName("winning");
        frame.setCreatedBy("test");
        frame.setModifiedDate(new Timestamp(new java.util.Date().getTime()));
        frameRepository.save(frame);

        controller.setFilter("w");
        controller.orderFrames();
        assertEquals(1, controller.getOrderedFrames().size());

        controller.setFilter("WiNNing");
        controller.orderFrames();
        assertEquals(1, controller.getOrderedFrames().size());

        controller.setFilter("losingwinning");
        controller.orderFrames();
        assertEquals(1, controller.getOrderedFrames().size());

        // checks that the frames are ordered by name
        controller.setFilter("");
        controller.orderFrames();
        assertEquals("frame", controller.getOrderedFrames().get(0));
        assertEquals("winning", controller.getOrderedFrames().get(1));

        frameRepository.delete(frame);
    }
}
