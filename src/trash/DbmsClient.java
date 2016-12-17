/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trash;

import DbmsAPP.Dbms;
import DbmsAPP.DbmsHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

/**
 *
 * @author pc
 */
public class DbmsClient {

    /**
     * @param args the command line arguments
     */
    
    public static Gson gson = new Gson();
    
    public static void main(String[] args) {
        try{ 
            String[] properities = {"-ORBInitialPort", "1050"};       
            ORB orb = ORB.init(properities, null); //args
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService"); 
            NamingContext ncRef = NamingContextHelper.narrow(objRef); 
            NameComponent nc = new NameComponent("Hello", ""); 
            NameComponent path[] = {nc}; 
            Dbms helloRef = DbmsHelper.narrow(ncRef.resolve(path)); 
            String Hello = helloRef.hello(); 
            helloRef.create_database("db1");
            helloRef.create_database("db2");
            helloRef.create_database("db3");
            helloRef.create_database("db4");
            String dbs = helloRef.show_dbs();
            ArrayList<String> db = gson.fromJson(dbs, new TypeToken<ArrayList<String>>(){}.getType());
            System.out.println("Size: " + db.size()); 
            System.out.println(Hello);       
        } catch(Exception e) { 
            System.out.println("ERROR : " + e); 
            e.printStackTrace(System.out); 
        }   
    }
    
}
