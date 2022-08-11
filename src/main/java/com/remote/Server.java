package com.remote;

import com.service.api.simplyInterfaceService;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;

public class Server  {

    public static void main(String [] args) throws RemoteException {

        System.out.println("Starting server");
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        String URL = "rmi://127.0.0.1:9100//Server";

        simplyInterfaceService Service = new Service();

        try{
            LocateRegistry.createRegistry(9100);
            Naming.rebind(URL,Service);
            System.out.println("SERVER IS UP");
        }catch (Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
