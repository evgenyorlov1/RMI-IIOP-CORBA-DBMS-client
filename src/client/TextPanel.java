/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DbmsAPP.Dbms;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.lang.System.out;

/**
 *
 * @author pc
 */
public class TextPanel extends javax.swing.JPanel {

    
    private static String useState = "none";
    private static Gson gson = new Gson();
    private Dbms dbms;
    private static final String none = "none";
    
    /*
     * Creates new form TextEntryPanel
     */
    public TextPanel() {
        initComponents();
        dbms = SingletonDBMSadapter.getInstance();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        jTextPane1.setToolTipText("Enter commands");
        jTextPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EnterPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void EnterPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EnterPressed
              
        if((evt.getKeyCode() == KeyEvent.VK_ENTER) && (evt.getID() == KeyEvent.KEY_PRESSED)) {
            String currentText = jTextPane1.getText();
            String lastLine = currentText.split("\n")
                    [currentText.split("\n").length-1];           
                      
            useState = QuaeryProcessor.useState(lastLine, useState);            
            Object[] command = QuaeryProcessor.parse(lastLine);             
            switch((int)command[0]) {                
                case 0:   
                    clear();             
                    break;                                    
                case 1:
                    try {show_dbs();} catch(Exception e) {}
                    break;                                    
                case 2:
                    try {use();} catch(Exception e) {}                    
                    break; 
                case 3: 
                    try {load(command);} catch(Exception e) {}
                    break;
                case 10:
                    db();
                    break;                
                case 11:
                    try {show_tables(lastLine);} catch(Exception e) {}                    
                    break;                
                case 12:
                    try {dropDatabase(lastLine);} catch(Exception e) {}                    
                    break;                
                case 13:
                    try {createTable(command, lastLine);} catch(Exception e) {}                                            
                    break;    
                case 14:
                    try {save(command, lastLine);} catch(Exception e) {}
                    break;     
                case 15:
                    try {load_table(command);} catch(Exception e) {}
                    break; 
                case 16:
                    try {insert(command, lastLine);} catch(Exception e) {}
                    break;
                case 101:
                    try {drop(command, lastLine);} catch(Exception e) {}              
                    break;                
                case 102:                    
                    try {find(command, lastLine);} catch(Exception e) {}                                                      
                    break;    
                case 103:
                    try {save_table(command, lastLine);} catch(Exception e) {} 
                    break;
                case 104:
                    try {count(command, lastLine);} catch(Exception e) {} 
                    break;
                case 105: 
                    try {update(command, lastLine);} catch(Exception e) {} 
                    break;
                case 106:                    
                    try {remove_id(command, lastLine);} catch(Exception e) {} 
                    break;
                case 107:
                    try {remove_key(command, lastLine);} catch(Exception e) {} 
                    break;
                case 1001:
                    try {limit(command, lastLine);} catch(Exception e) {}              
                    break;                
                case 1002:
                    try {sort(command, lastLine);} catch(Exception e) {}              
                    break;                
                case 1003:       
                    try {skip(command, lastLine);} catch(Exception e) {}              
                    break;                    
                case 999:                
                    try {
                        jTextPane1.getDocument().insertString(
                                jTextPane1.getText().length(), 
                                "\n".concat(lastLine.concat(" is not defined")), 
                                null);
                    } catch(Exception e) {}
            }
        }            
    }//GEN-LAST:event_EnterPressed

    //TESTED
    private void clear() {
        System.out.println("TextPanel.clear");
        jTextPane1.setText(""); //FIX caret on second line    
    }
    
    //TESTED
    private void show_dbs() throws Exception{
        System.out.println("TextPanel.show_dbs");
        String json = dbms.show_dbs();  
        ArrayList<String> dbs = gson.fromJson(json,  new TypeToken<ArrayList<String>>(){}.getType());
        
        for(String db : dbs) {
            System.out.println(db);            
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(db), null);             
        }
    }
    
    //TESTED
    private void use() throws Exception {
        System.out.println("TextPanel.use");
        System.out.println(useState);
        if(dbms.is_unique_name(useState))          
            dbms.create_database(useState);
        else 
            System.out.println("TextPanel.select_database");            
    }
    
    //TESTED
    private void db() {
        System.out.println("TextPanel.db");    
        if(!useState.equals("none")) 
            jTextPane1.setText(useState + "\n");
        else 
            jTextPane1.setText("select database with use");
    }
    
    //TESTED
    private void show_tables(String lastLine) throws Exception{
        System.out.println("TextPanel.show_tables");
        if(!useState.equals(this.none)) {
            String json = dbms.show_tables(useState);
            ArrayList<String> tables = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());
            for(String tb : tables)             
                jTextPane1.getDocument().insertString(jTextPane1.getText().
                        length(), "\n".concat(tb), null);                                                      
        } else {           
            jTextPane1.getDocument().insertString( jTextPane1.getText().length(), 
                    "\n".concat(lastLine.concat(" - select database")), null);                                    
        }
    }
    
    //TESTED
    private void dropDatabase(String lastLine) throws Exception{
        System.out.println("TextPanel.dropDatabase");
        if(!useState.equals(this.none)) {
            dbms.drop_database(useState);
            useState = this.none;
        } else {            
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - select database")), null);
        }    
    }
    
    //TESTED
    private void createTable(Object[] command, String lastLine) throws Exception {
        System.out.println("TextPanel.createTable");        
        if(!useState.equals("none")) {
            String json = gson.toJson((ArrayList<String[]>)command[2]);
            dbms.create_table(useState, (String)command[1], json);
        } else {            
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - select database")), null);                                    
        }   
    }
        
    //TESTED
    private void save(Object[] command, String lastLine) throws Exception{
        System.out.println("TextPanel.save");        
        if(!useState.equals(this.none)) {
            //dbms.save_serialization(useState, (String)command[1]);
        } else {
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - select database")), null);                                    
        }    
    }
    
    //TESTED
    private void save_table(Object[] command, String lastLine) throws Exception{
        System.out.println("TextPanel.save_database");
        if(!useState.equals(this.none)) {
            //dbms.save_serialization(useState, (String)command[1], (String)command[2]);
        } else {
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - select database")), null);                                    
        }    
    }
    
    //TESTED
    private void load(Object[] command) throws Exception{
        System.out.println("TextPanel.load_database");        
        //dbms.load_serialization((String)command[1]);        
    }
    
    //TESTED
    private void load_table(Object[] command) throws Exception{
        System.out.println("TextPanel.load_table");        
        //dbms.load_serialization(useState, (String)command[1]);
    }
    
    //TESTED
    private void drop(Object[] command, String lastLine) throws Exception{
        System.out.println("TextPanel.drop");
        if(!useState.equals(this.none)) {
            dbms.drop_table(useState, (String)command[1]);
        } else {            
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - select database")), null);                                    
        }      
    }
    
    //TESTED
    private void insert(Object[] command, String lastLine) throws Exception {
        System.out.println("TextPanel.insert");  
        if(!useState.equals(this.none)) {         
            String json = gson.toJson((ArrayList<String[]>)command[2]);
            dbms.insert(useState, (String)command[1], json);
        } else {           
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - wrong command")), null);                                   
        }            
    }
    
    //TESTED
    private void remove_id(Object[] command, String lastLine) throws Exception {
        System.out.println("TextPanel.remove_id");                
        if(!useState.equals(this.none)) {                               
            //dbms.remove_id(useState, command[1].toString(), command[2].toString());            
        } else {           
            try {
                jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                    "\n".concat(lastLine.concat(" - select database")), null);                                   
            } catch(Exception e) {System.out.println("error");}
        }
    }
    
    //TESTED
    private void remove_key(Object[] command, String lastLine) throws Exception {
        System.out.println("TextPanel.remove_key");          
        if(!useState.equals(this.none)) {                
            String json = gson.toJson((ArrayList<String[]>)command[2]);                    
            //dbms.remove_key(useState, command[1].toString(), json);            
        } else {           
            try {
                jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                    "\n".concat(lastLine.concat(" - select database")), null);                                   
            } catch(Exception e) {System.out.println("error");}
        }
    }
    
    //TEST
    private void update(Object[] command, String lastLine) throws Exception {
        System.out.println("TextPanel.update");
        ArrayList<String[]> keyValue = (ArrayList<String[]>)command[3];
        String json = gson.toJson(keyValue);
        
        if(!useState.equals(this.none)) {            
            dbms.update(command[2].toString(), json, useState, command[1].toString());            
        } else {           
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - error")), null);                                   
        }
    }
    
    //TESTED
    private void find(Object[] command, String lastLine) throws Exception {
        System.out.println("TextPanel.find");
        ArrayList<ArrayList<String[]>> results = new ArrayList<>();        
        
        if(!useState.equals(this.none)) {            
            String json = dbms.find(useState, (String)command[1]);
            results = gson.fromJson(json, new TypeToken<ArrayList<ArrayList<String[]>>>(){}.getType());
            TableFrame tableFrame = new TableFrame(results, useState, (String)command[1]);
        } else {           
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - select database")), null);                                   
        }       
    }
    
    //TESTED
    private void count(Object[] command, String lastLine) throws Exception {
        System.out.println("TextPanel.count");
        int results = 0;
        if(!useState.equals(this.none)) {                  
            results = dbms.count(useState, (String)command[1]); 
            jTextPane1.getDocument().insertString(jTextPane1.getText().
                        length(), "\n".concat(String.valueOf(results)), null);    
        } else {           
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - wrong command")), null);                                   
        }    
    }
    
    //TESTED
    private void limit(Object[] command, String lastLine) throws Exception {
        System.out.println("TextPanel.limit");
        ArrayList<ArrayList<String[]>> results = new ArrayList<ArrayList<String[]>>();
        if(!useState.equals(this.none)) {             
            String json = dbms.limit(useState, (String)command[1], (int)command[2]);                        
            results = gson.fromJson(json, new TypeToken<ArrayList<ArrayList<String[]>>>(){}.getType());
            TableFrame tableFrame = new TableFrame(results, useState, (String)command[1]);
        } else {           
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - select database")), null);                                   
        }     
    }
    
    //TESTED
    private void sort(Object[] command, String lastLine) throws Exception{
        System.out.println("TextPanel.sort");        
        ArrayList<ArrayList<String[]>> results = new ArrayList<ArrayList<String[]>>();
        if(!useState.equals(this.none)) {       
            String token = (String)command[1].toString().concat(";").concat((String)command[2]);
            String json = dbms.sort(useState, token, (int)command[3]);
            results = gson.fromJson(json, new TypeToken<ArrayList<ArrayList<String[]>>>(){}.getType());
            TableFrame tableFrame = new TableFrame(results, useState, (String)command[1]);
        } else {            
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - select database")), null);
        }     
    }
    
    //TESTED
    private void skip(Object[] command, String lastLine) throws Exception{
        System.out.println("TextPanel.skip");
        ArrayList<ArrayList<String[]>> results = new ArrayList<ArrayList<String[]>>();
        if(!useState.equals(this.none)) {            
            String json = dbms.skip(useState, (String)command[1], (int)command[2]);
            results = gson.fromJson(json, new TypeToken<ArrayList<ArrayList<String[]>>>(){}.getType());
            TableFrame tableFrame = new TableFrame(results, useState, (String)command[1]);
        } else {            
            jTextPane1.getDocument().insertString(jTextPane1.getText().length(), 
                "\n".concat(lastLine.concat(" - select database")), null);                                    
        }  
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}