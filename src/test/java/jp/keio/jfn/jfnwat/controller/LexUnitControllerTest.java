package jp.keio.jfn.jfnwat.controller;

import jp.keio.jfn.wat.JFNWAT;
import jp.keio.jfn.wat.controller.LexUnitController;
import jp.keio.jfn.wat.webreport.domain.LexUnit;
import jp.keio.jfn.wat.webreport.repository.FrameRepository;
import jp.keio.jfn.wat.webreport.repository.LexUnitRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by jfn on 2/12/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JFNWAT.class)
@ActiveProfiles("test")
public class LexUnitControllerTest {
    
    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    FrameRepository frameRepository;
    
    private LexUnitController controller;
    
    private LexUnit mainLexUnit;
    
    @Before
    public void setup() {
        controller = new LexUnitController();
        controller.setLexUnitRepository(lexUnitRepository);
    }
    
    @After
    public void after() {
        lexUnitRepository.deleteAll();
        frameRepository.deleteAll();
    }

    @Test
    public void test(){
        assertEquals(0,0);
    }
}
