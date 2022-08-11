package com.client;

import com.service.api.simplyInterfaceService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

public class Proxy implements simplyService {

    private final String serviceNameRemote = "rmi://127.0.0.1:9100//Server";

    private simplyInterfaceService service;

    public Proxy(){
        System.out.println("PROXY CONSTUCTOR");
        try{
            TimeUnit.SECONDS.sleep(5);
            service = (simplyInterfaceService) Naming.lookup(serviceNameRemote);
        }
        catch (RemoteException | MalformedURLException | NotBoundException | InterruptedException e){
            System.err.println(e);
        }

    }


    @Override
    public int getInt(int x){
        System.out.println("PROXY IN GET INT");
        try{
            return service.calculatePower(x);
        }catch(Exception e) {
            System.err.println(e);
            return -999;
        }
    }
}
