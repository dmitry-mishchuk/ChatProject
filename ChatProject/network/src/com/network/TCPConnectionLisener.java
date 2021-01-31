package com.network;

public interface TCPConnectionLisener {

    void  onConnectionReady(TCPConnection tcpConnection);
    void  onReceiveString(TCPConnection tcpConnection, String value);
    void  onDisconnect(TCPConnection tcpConnection);
    void  onExeption(TCPConnection tcpConnection, Exception e);


}
