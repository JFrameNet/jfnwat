package jp.keio.jfn.jfnwat.controller;

import jp.keio.jfn.wat.JFNWAT;

import jp.keio.jfn.wat.controller.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class UtilsTest {

    @Test
    public void testMatchSearch1() {
        assertEquals(true, Utils.matchSearch("", "test"));
    }

    @Test
    public void testMatchSearch2() {
        assertEquals(true, Utils.matchSearch("test", "test"));
    }

    @Test
    public void testMatchSearch3() {
        assertEquals(true, Utils.matchSearch("e", "test"));
    }

    @Test
    public void testMatchSearch4() {
        assertEquals(true, Utils.matchSearch("manytestmany", "test"));
    }

    @Test
    public void testMatchSearch5() {
        assertEquals(true, Utils.matchSearch("TeSt", "test"));
    }

    @Test
    public void testMatchSearch6() {
        assertEquals(false, Utils.matchSearch("other", "test"));
    }
}
