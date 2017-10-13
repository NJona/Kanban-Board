import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ColumnService {
	
	private Column[] columnArray;
	private Object dataObject;
	private JSONObject jsonObject;
	private JSONArray jsonColumnArray;
	
	public ColumnService(){
		
	}

	public void loadData(){
		if(this.readJSONFile()){
			this.transferDataToJSON();
			this.getColumnFromJSON();
			this.createColumnObjects();
		}else{
			System.err.println("Problem with File!");
		}
	}
	
	private boolean readJSONFile(){
		JSONParser parser = new JSONParser();
		try {
            this.dataObject = parser.parse(new FileReader("../projectData.json"));	
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
			return false;
        }
	}
	
	private void transferDataToJSON(){
		this.jsonObject = (JSONObject) this.dataObject;
	}
	
	private void getColumnFromJSON(){
        jsonColumnArray = (JSONArray) jsonObject.get("Column");		
	}
	
	private void createColumnObjects(){
        createColumnArray(jsonColumnArray.size());
        Iterator<?> iterator = jsonColumnArray.iterator();
        int arrayIndex = 0;
        while (iterator.hasNext()) {
        	JSONObject column = (JSONObject) iterator.next();
            String name = (String) column.get("name");
            this.addColumnToArray(arrayIndex, name);
            arrayIndex++;
        }
	}
	public void addColumnToArray(int index, String name){
		if(this.columnArray[index] == null){
			System.out.println("Created Column " + name);
			this.columnArray[index] = new Column(name);
		}else{
			System.err.println("Column " + name + " already exists!");
		}
	}
	
	public void createColumnArray(int size) {
		this.columnArray = new Column[size];
	}
	
	public Column[] getColumns(){
		return this.columnArray;
	}
}
