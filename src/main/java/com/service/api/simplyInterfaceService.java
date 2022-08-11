package com.service.api;

import java.rmi.*;

public interface simplyInterfaceService extends Remote{

    int calculatePower(int x) throws RemoteException;
}
