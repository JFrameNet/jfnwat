package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
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

    public String testDef () {
        return "<def-root>Some entity (<fen>Theme</fen>) starts out in one place (<fen>Source</fen>) and ends up in some other place (<fen>Goal</fen>), having covered some space between the two (<fen>Path</fen>). The frames that inherit the general Motion frame add some elaboration to this simple idea.  Inheriting frames can add Goal-profiling (<ment>arrive, reach</ment>), Source-profiling (<ment>leave, depart</ment>), or Path-profiling (<ment>traverse, cross</ment>), or aspects of the manner of motion (<ment>run, jog</ment>) or assumptions about the shape-properties, etc., of any of the places involved (<ment>insert, extract</ment>).  A particularly complex area in the vocabulary of Motion is the depiction of the relation of Vehicles to the Theme.  In some cases, no separate Theme is expressed:  <ex>The plane <t>flew</t> over the city.</ex>  In this case, the sentence is annotated in Self-motion.  When the Vehicle is profiled as being operated by a Driver, the sentence is annotated in the Operate_vehicles frame:  <ex>Don't try to <t>fly</t> an F-16 without training!</ex>  This is very similar to the Carrying frame which covers cases where the Vehicle is necessarily involved, but the movement of the <fen>Theme</fen> (something carried by the Vehicle) is profiled:  <ex>It's scary <t>flying</t> hundreds of people over thousands of miles of ocean every day.</ex> ";
    }

}