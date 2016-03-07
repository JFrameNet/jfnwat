package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.repository.FrameRepository;
import jp.keio.jfn.wat.repository.LexUnitRepository;
import org.hibernate.Hibernate;
import org.primefaces.model.menu.DynamicMenuModel;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Quiri on 7-3-2016.
 */
public class KwicMenu {
    private MenuModel model;
    private Frame mainFrame;

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    LexUnitRepository lexUnitRepository;

    public KwicMenu(String name) { // frame name

        System.out.println("new menu model for frame:"+name);
        model = new DynamicMenuModel();
/*
        mainFrame = frameRepository.findByName(name).get(0);
        Hibernate.initialize(mainFrame.getLexUnits());
        Hibernate.initialize(mainFrame.getFrameElements());
*/
        //Submenu
        DefaultSubMenu submenu = new DefaultSubMenu(name);

        DefaultMenuItem item = new DefaultMenuItem("Frame Element");

        submenu.addElement(item);

        model.addElement(submenu);
    }

    public MenuModel getModel() {
        return model;
    }
}
