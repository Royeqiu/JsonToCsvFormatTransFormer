import JsonLoader.JsonObjectExpander;
import JsonLoader.JsonObjectModel;
import org.json.JSONException;
import org.json.JSONObject;
import util.IOManager;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 * Created by roye on 2017/4/9.
 */
public class main {

    public static void main(String[] args) throws JSONException {

        IOManager io=new IOManager();
        io.setreadpath("inputData.txt");
        io.setwritepath("expandedData.txt",true);
        JsonObjectExpander jsonObjectExpander=new JsonObjectExpander();
        int dataCount=1;
        while(io.ready())
        {
            String singleJsonData=io.readLine();
            jsonObjectExpander.setJsonObject(new JSONObject(singleJsonData));
            jsonObjectExpander.expandObject(jsonObjectExpander.getJsonObject());
        }
        System.out.println(jsonObjectExpander.getColumnCount());

        String column="";
        for(int i=0;i<jsonObjectExpander.getColumnCount();i++)
        {
            column+=i+jsonObjectExpander.getColumnIndex().get(i)+"\t";

        }
        io.writeLine(column);

        for(JsonObjectModel singleJsonData:jsonObjectExpander.getExpandedJsonObjectArray())
        {
            String output="";
            Hashtable<String,String>dataTable=singleJsonData.getData();
            for(int i=0;i<jsonObjectExpander.getColumnCount();i++)
            {
                String columnName=jsonObjectExpander.getColumnIndex().get(i);
                String columnvalue="Null";
                if(dataTable.containsKey(columnName))
                {
                    columnvalue=dataTable.get(columnName);
                }
                output+=columnvalue+"\t";
            }
            io.writeLine(output);
        }

    }
}
