package com.remote;

import com.service.api.simplyInterfaceService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Service extends UnicastRemoteObject implements simplyInterfaceService {

    public Service() throws RemoteException{
        super();
        System.out.println("Service Constructor");
    }

    @Override
    public int calculatePower(int x) throws RemoteException {
        return x*x;
    }
}
