
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonExtraction {
    //	private static final String FILENAME = "C:\\Users\\eric\\Desktop\\inputData.txt";
    private static final String FILENAME ="inputData.txt";
    static String Output_column ="";
    static String Output_value ="";
    static int JsonCount=0;
    static HashMap<String, String[]> alldata = new HashMap<String, String[]>();
    public static void main(String[] args) {

        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            String JsonString="";

            br = new BufferedReader(new FileReader(FILENAME));
            while ((JsonString = br.readLine()) != null) {
                JSONObject firstobject = (JSONObject) new JSONTokener(JsonString).nextValue();
                Iterator<String> firstKeys = firstobject.keys();
                while (firstKeys.hasNext()) {
                    checkType(firstobject, firstKeys, "");
                }
                JsonCount++;
            }
            Set<String> allkeySet = alldata.keySet();
            Iterator<String> allkey = allkeySet.iterator();
            while(allkey.hasNext()){
                String currkey = allkey.next();
                Output_column += currkey;
            }
            allkey = allkeySet.iterator();
            for(int i=0;i<JsonCount;i++){
                allkey = allkeySet.iterator();
                while(allkey.hasNext()){
                    String currkey = allkey.next();
                    String[] value = new String[JsonCount];
                    String[] oldArray = alldata.get(currkey);
                    for(int j=0;j<oldArray.length;j++){
                        value[j] = oldArray[j];
                    }
                    if(oldArray.length<JsonCount){
                        value[i] = "null" + "\t";
                    }
                    Output_value += value[i];
                }
                Output_value += "\n";
                System.out.println(""+i);
            }
            outputFile();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static void checkType(JSONObject firstobject, Iterator<String> Keys, String column) throws JSONException {
        String CurrKey = Keys.next();
        JSONObject secondobject = firstobject.optJSONObject(CurrKey);
        if (secondobject != null && !secondobject.equals("")) {
            Keys = secondobject.keys();
            while (Keys.hasNext()) {
                checkType(secondobject, Keys, column + CurrKey + "_");
            }
        } else {
            JSONArray secondArray = firstobject.optJSONArray(CurrKey);
            if (secondArray != null && !secondArray.equals("")) {
                for (int i = 0; i < secondArray.length(); i++) {
                    JSONObject ArrayObject = secondArray.getJSONObject(i);
                    Keys = ArrayObject.keys();
                    while (Keys.hasNext()) {
                        checkType(ArrayObject, Keys, i + "_" + column + CurrKey + "_");
                    }
                }
            } else {
                String curr_column = column + CurrKey + "\t";
                String curr_value = firstobject.optString(CurrKey).toString()  + "\t";
                if(alldata.containsKey(curr_column)){
                    String[] newArray = new String[JsonCount+1];
                    String[] oldArray = alldata.get(curr_column);
                    for(int i=0;i<oldArray.length;i++){
                        newArray[i] = oldArray[i];
                    }
                    newArray[JsonCount] = curr_value;
                    alldata.put(curr_column, newArray);
                }else{
                    String[]  newArray = new String[JsonCount+1];
                    for(int i=0;i<=JsonCount;i++){
                        newArray[i] = "null" + "\t";
                        if(i==JsonCount){
                            newArray[i] = curr_value;
                        }
                    }
                    alldata.put(curr_column, newArray);
                }
            }
        }
    }

    public static void outputFile(){
        File output_file = new File("C:\\Users\\eric\\Desktop\\OutPutData.txt");
        FileOutputStream fout;
        try {
            fout = new FileOutputStream(output_file, true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout));
            bw.flush();
            bw.write(Output_column + "\n" + Output_value);
            bw.flush();
            bw.close();
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("OutPut Complete");
    }

}