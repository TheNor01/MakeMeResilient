package com.DesignPatternDoc.client;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

import com.DesignPatternDoc.DataContracts.DPDocumentationInterface;
import com.DesignPatternDoc.DataContracts.DPDocumentationService;
import com.DesignPatternDoc.DataContracts.models.DesignPatternDoc;

public class ServiceProxy implements DPDocumentationInterface {

    private final String serviceNameRemote = "rmi://127.0.0.1:9100//Server";
    private DPDocumentationService service;

    public ServiceProxy(){
        System.out.println("PROXY CONSTUCTOR");
        try{
            TimeUnit.SECONDS.sleep(5);
            //the nly point in wich the lookup will 
            service = (DPDocumentationService) Naming.lookup(serviceNameRemote);
        }
        catch (RemoteException | MalformedURLException | NotBoundException | InterruptedException e){
            System.err.println(e);
        }
    }

    @Override
    public String[] getDPNames() {
        try{
            return service.getDPNames();
        } catch(Exception e) {
            return new  String[] {""};
        }
    }


    @Override
    public String getUMLDiagram(String name) {
        try{
            return service.getUMLDiagram(name);
        }catch(Exception e) {
            return "";
        }
    }


    @Override
    public DesignPatternDoc getDPDocumentation(String name) {
        try{
            return service.getDPDocumentation(name);
        }catch(Exception e) {
            return new DesignPatternDoc("","","");
        }
    }
}
