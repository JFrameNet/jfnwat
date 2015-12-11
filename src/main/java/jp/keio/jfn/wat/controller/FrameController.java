package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jp.keio.jfn.wat.JFNWAT;
import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.repository.FrameRepository;
import org.springframework.beans.factory.annotation.Autowired;

@ManagedBean
@Controller
public class FrameController implements Serializable {
    @Autowired
    FrameRepository frameRepository;

    public Iterable<Frame> getAll () {
        return frameRepository.findAll();
    }
}