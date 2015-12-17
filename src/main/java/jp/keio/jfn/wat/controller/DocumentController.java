package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.Status;
import jp.keio.jfn.wat.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@ManagedBean
@Controller
public class DocumentController implements Serializable {

    @Autowired
    StatusRepository statusRepository;


    public Iterable<Status> getAllStatus () {
        return statusRepository.findAll();
    }
}
