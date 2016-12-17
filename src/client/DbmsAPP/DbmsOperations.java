package DbmsAPP;


/**
* DbmsAPP/DbmsOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from dbms.idl
* Thursday, December 15, 2016 11:40:21 PM EET
*/

public interface DbmsOperations 
{
  String hello ();
  String show_dbs ();
  String show_tables (String DBname);
  void drop_database (String DBname);
  void drop_table (String DBname, String Tname);
  void create_database (String DBname);
  void create_table (String DBname, String Tname, String keyType);
  boolean register (String user, String password);
  boolean login (String user, String password);
  String find (String DBname, String Tname);
  boolean is_unique_name (String DBname);
  String get_metadata (String DBname, String Tname);
  String limit (String DBname, String Tname, int num);
  String skip (String DBname, String Tname, int num);
  String sort (String DBname, String Tname, int num);
  int count (String DBname, String Tname);
  void insert (String DBname, String Tname, String keyValue);
  void update (String id, String keyVal, String DBname, String Tname);
  void remove (String DBname, String Tname, String id);
} // interface DbmsOperations