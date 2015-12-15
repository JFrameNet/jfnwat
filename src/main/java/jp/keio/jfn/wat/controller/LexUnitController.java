package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.LexUnit;
import jp.keio.jfn.wat.domain.Status;
import jp.keio.jfn.wat.repository.LexUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@ManagedBean
@Controller
public class LexUnitController implements Serializable {
    @Autowired
    LexUnitRepository lexUnitRepository;

    public Iterable<LexUnit> getAll () {
        return lexUnitRepository.findAll();
    }
}