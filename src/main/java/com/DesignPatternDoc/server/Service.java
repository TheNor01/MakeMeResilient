package com.DesignPatternDoc.server;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.DesignPatternDoc.DataContracts.DPDocumentationService;
import com.DesignPatternDoc.DataContracts.models.DesignPatternDoc;
import java.awt.image.BufferedImage;

public class Service extends UnicastRemoteObject implements DPDocumentationService {

    Map<String,DesignPatternDoc> dps = new HashMap<String,DesignPatternDoc>();
    private final String basePath = "./src/main/java/com/DesignPatternDoc/server/";
    public Service() throws RemoteException{
        super();
        getJsonData();
    }

    private void getJsonData(){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader(basePath + "Resources/dp.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray objList = (JSONArray) obj;
            //System.out.println(objList);
            //Iterate over  array
            objList.forEach(el -> saveObject( (JSONObject) el ) );
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveObject(JSONObject obj){
        dps.put((String) obj.get("title"), new DesignPatternDoc((String) obj.get("title"), (String) obj.get("description"), (String) obj.get("imagePath")));
    }

    @Override
    public String[] getDPNames() throws RemoteException {
        String[] names = dps.keySet().toArray(new String[dps.size()]);
        System.out.println(names);
        return  names;
    }

    @Override
    public String getUMLDiagram(String name) throws RemoteException {
        DesignPatternDoc dp = dps.get(name);
        try{
            BufferedImage bImage = ImageIO.read(new File(basePath + dp.imagePath));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos );
            byte [] data = bos.toByteArray();
            return Base64.getEncoder().encodeToString(data);
        }
        catch(Exception e){
            e.printStackTrace();
            return "";
        }
        
    }

    @Override
    public DesignPatternDoc getDPDocumentation(String name) throws RemoteException {
        return dps.get(name);
    }
}
