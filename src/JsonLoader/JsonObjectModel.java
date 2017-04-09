package JsonLoader;

import java.util.Hashtable;

/**
 * Created by roye on 2017/4/9.
 */
public class JsonObjectModel {

    JsonObjectModel()
    {
        data=new Hashtable<String,String>();
    }
    public Hashtable<String, String> getData() {
        return data;
    }

    public void setData(Hashtable<String, String> data) {
        this.data = data;
    }

    private Hashtable<String,String> data;


}

