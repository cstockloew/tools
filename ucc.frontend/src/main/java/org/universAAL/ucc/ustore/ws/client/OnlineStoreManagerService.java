package org.universAAL.ucc.ustore.ws.client;


public interface OnlineStoreManagerService extends javax.xml.rpc.Service {
    public java.lang.String getOnlineStoreManagerPortAddress();

    public OnlineStoreManager getOnlineStoreManagerPort() throws javax.xml.rpc.ServiceException;

    public OnlineStoreManager getOnlineStoreManagerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}

