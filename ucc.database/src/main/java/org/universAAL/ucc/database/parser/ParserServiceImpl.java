package org.universAAL.ucc.database.parser;

import java.io.File;

import javax.xml.bind.JAXB;

import org.universAAL.ucc.model.uapp.AalUapp;
import org.universAAL.ucc.model.usrv.AalUsrv;

public class ParserServiceImpl implements ParserService {

	public AalUapp getUapp(String path) {
		AalUapp uapp = JAXB.unmarshal(new File(path), AalUapp.class);
		return uapp;
	}

	public AalUsrv getUsrv(String path) {
		AalUsrv usrv = JAXB.unmarshal(new File(path), AalUsrv.class);
		return usrv;
	}

}
