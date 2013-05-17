package org.universAAL.ucc.database.parser;

import org.universAAL.middleware.deploymanager.uapp.model.AalUapp;
import org.universAAL.ucc.model.usrv.AalUsrv;

public interface ParserService {
	public AalUapp getUapp(String path);
	public AalUsrv getUsrv(String path);
}
