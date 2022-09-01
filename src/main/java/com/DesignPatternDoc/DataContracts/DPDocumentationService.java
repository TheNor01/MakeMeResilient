package com.DesignPatternDoc.DataContracts;
import java.rmi.*;
import com.DesignPatternDoc.DataContracts.models.DesignPatternDoc;

public interface DPDocumentationService extends Remote{
    String[] getDPNames() throws RemoteException;

    String getUMLDiagram(String name) throws RemoteException;

    DesignPatternDoc getDPDocumentation(String name) throws RemoteException;
}



