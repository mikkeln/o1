package syntaxtree;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

public class SymbolTable{


    /*Variables*/
    SymbolTable parent;
    HashMap entries;
    int level;
    String type;
    String name;
    Boolean byRef;
    List params;
    List <Boolean> paramRef;


    //Constructor
    SymbolTable(String type, String name){
	level = 0;
	this.type = type;
	this.name = name;
	entries = new HashMap();
	parent = null;
	byRef = false;
	params = new ArrayList();
	paramRef = new ArrayList<Boolean>();
    }

    public Boolean getRefParam(int i){
	return paramRef.get(i);
    }

    public void addParamRef(Boolean ref){
	paramRef.add(ref);
    }


    public int nrOfParams(){
	return params.size();
    }

    public String getParam(int i){
	return (String) params.get(i);
    }

    public void addParam(String param){
	params.add(param);
    }

    public int getSize(){
	return entries.size();
    }


    /*Create new entry in symboltable*/
    public SymbolTable newEntry(String entryType, String name){
	SymbolTable table;

	table = new SymbolTable(entryType, name);
	enter(table);

	table.parent = this;
	table.level = level + 1;
	table.name = name;

	return table;
    }


    /*Put entry in hashmap*/
    public boolean enter(SymbolTable st){
	if(locateWithinScope(st.name) != null)
	    return false;
	
	entries.put(st.name, st);	    
	return true;
    }



    public SymbolTable searchThru(String name){
	//SymbolTable table;

	SymbolTable structt = null;
	
	Collection c = this.entries.values();
	Iterator it = c.iterator();
	
	Collection b;
	Iterator itr;
	
	while(it.hasNext()){
	    SymbolTable tmp = (SymbolTable)it.next();
	    if(tmp.name.equals(name)){
		return tmp;
	    }else{
		b = tmp.entries.values();
		itr = b.iterator();
		while(itr.hasNext()){
		    SymbolTable crap = (SymbolTable)itr.next();
		    if(crap.name.equals(name)){
			return crap;
		    }
		}
	    }
	}
	
	return null;
    }

    



    /*Find entry in entire table*/
    public SymbolTable locate(String name){
	int i;

	SymbolTable st;
	SymbolTable tmp;

	for(st = this; st != null; st = st.parent){
	    tmp = (SymbolTable)entries.get(name);
	    if(tmp != null){
		return tmp;
	    }
	}

	return null;
    }


    /*Find entry on this level*/
    public SymbolTable locateWithinScope(String name){
	return (SymbolTable)entries.get(name);
    }


    /*Move up one level*/
    public SymbolTable ascend(){
	return parent;	
	//return parent != null ? parent : this;
    }


    /*Move down one level*/
    public SymbolTable descend(String name){
	SymbolTable st = locateWithinScope(name);

	try{
	    if(st == null)
		return null;
	    else
		return st;


	}catch(Exception e){
	    System.out.println(e);
	    e.printStackTrace();
	    return null;
	}
    }

}
