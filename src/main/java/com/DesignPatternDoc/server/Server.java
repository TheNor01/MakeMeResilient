package com.DesignPatternDoc.server;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;

import com.DesignPatternDoc.DataContracts.DPDocumentationService;

public class Server  {

    //changed
    private static DPDocumentationService Service;


    public static void main(String [] args) throws RemoteException {

        System.out.println("Starting server");
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        String URL = "rmi://127.0.0.1:9100//Server";

        Service = new Service();

        try{
            LocateRegistry.createRegistry(9100);
            Naming.rebind(URL,Service);
            System.out.println("SERVER IS UP");
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
        }catch (Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
