/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DbmsAPP.Dbms;
import DbmsAPP.DbmsHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

/**
 *
 * @author pc
 */
public class SingletonDBMSadapter {
    
    
    private static Dbms helloRef = null;
    
    private SingletonDBMSadapter() {}
    
    
    public static Dbms getInstance() {
        if(helloRef == null) {
            try{ 
                String[] properities = {"-ORBInitialPort", "1050"};       
                ORB orb = ORB.init(properities, null); 
                org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService"); 
                NamingContext ncRef = NamingContextHelper.narrow(objRef); 
                NameComponent nc = new NameComponent("Hello", ""); 
                NameComponent path[] = {nc}; 
                helloRef = DbmsHelper.narrow(ncRef.resolve(path)); 
            } catch(Exception e) {
                System.out.println("ERROR : " + e); 
                e.printStackTrace(System.out); 
            }
        }
        return helloRef;
    }  
}
