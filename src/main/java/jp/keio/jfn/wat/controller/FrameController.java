package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.LexUnit;
import jp.keio.jfn.wat.repository.LexUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.repository.FrameRepository;

@ManagedBean
@Controller
public class FrameController implements Serializable {
    @Autowired
    FrameRepository frameRepository;

    public Iterable<Frame> getAll () {
        return frameRepository.findAll();
    }
}