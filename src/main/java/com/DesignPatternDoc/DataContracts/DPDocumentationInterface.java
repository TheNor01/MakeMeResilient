package com.DesignPatternDoc.DataContracts;
import com.DesignPatternDoc.DataContracts.models.DesignPatternDoc;

public interface DPDocumentationInterface{
    String[] getDPNames();

    String getUMLDiagram(String name);
    
    DesignPatternDoc getDPDocumentation(String name);
}



