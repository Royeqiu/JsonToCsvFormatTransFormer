package JsonLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;


/**
 * Created by roye on 2017/4/9.
 */
public class JsonObjectExpander {


    private ArrayList<JsonObjectModel> expandedJsonObjectArray;
    private JsonObjectModel expandedJsonObject;
    private JSONObject jsonObject;
    private int columnCount;

    public ArrayList<JsonObjectModel> getExpandedJsonObjectArray() {
        return expandedJsonObjectArray;
    }
    public int getColumnCount() {
        return columnCount;
    }

    public JsonObjectExpander()
    {
        columnCount=0;
        columnIndex=new Hashtable<Integer,String>();
        expandedJsonObjectArray=new ArrayList<JsonObjectModel>();
    }
    public Hashtable<Integer, String> getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Hashtable<Integer, String> columnIndex) {
        this.columnIndex = columnIndex;
    }

    private Hashtable<Integer,String> columnIndex;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JsonObjectModel getExpandedJsonObject() {
        return expandedJsonObject;
    }


    public void expandObject(JSONObject jsonObject)
    {
        expandedJsonObject=new JsonObjectModel();
        try {
            expandJsonObject(jsonObject,"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        expandedJsonObjectArray.add(expandedJsonObject);
    }
    private void expandJsonObject(JSONObject jsonObject,String parentColumnName) throws JSONException {

        Iterator<String> jsonObjecKeySet=jsonObject.keys();

        while(jsonObjecKeySet.hasNext())
        {
            String keyName=jsonObjecKeySet.next();
            Object jsonObjectValue=jsonObject.get(keyName);
            String columnName=parentColumnName+"_"+keyName;
            //if(jsonObjectValue.getClass()!=JSONObject.class&&jsonObjectValue.getClass()!= JSONArray.class&&jsonObjectValue!=JSONObject.NULL)
            if(jsonObjectValue.getClass()!=JSONObject.class&&jsonObjectValue.getClass()!= JSONArray.class)
            {
                if(!columnIndex.contains(columnName))
                {
                    columnIndex.put(columnCount++,columnName);
                }
                expandedJsonObject.getData().put(columnName,jsonObjectValue.toString());
            }
            else if(jsonObjectValue.getClass()==JSONObject.class)
            {
                expandJsonObject((JSONObject) jsonObjectValue,columnName);
            }
            else if(jsonObjectValue.getClass()==JSONArray.class)
            {
                JSONArray jsonArrayValue=(JSONArray)jsonObjectValue;
                expandJsonArray(jsonArrayValue,columnName);
            }
        }
    }
    private void expandJsonArray(JSONArray jsonArrayValue,String columnName) throws JSONException {
        for(int i=0;i<jsonArrayValue.length();i++)
        {
            Object singleValue=jsonArrayValue.get(i);
            if(singleValue.getClass()==JSONObject.class)
            {
                expandJsonObject((JSONObject)singleValue,columnName+"_"+i);
            }
            else if(singleValue.getClass()==JSONArray.class)
            {
                expandJsonArray((JSONArray)singleValue,columnName+"_"+i);
            }
            //else if(singleValue!=JSONObject.NULL)
            else
            {
                if(!columnIndex.contains(columnName+"_"+i))
                {
                    columnIndex.put(columnCount++,columnName+"_"+i);
                }
                expandedJsonObject.getData().put(columnName+"_"+i,singleValue.toString());
            }

        }
    }

}
