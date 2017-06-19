package util.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import util.exception.ArrayLengthException;
import util.exception.ArrayOverflowException;
import util.exception.NoDataException;

public class SignalData {

    //Properties
//    private String signalType;
//    private String signalName;
//    private String signalGroup;
//    private double max, min;
//    private String units;
    private HashMap<String, String> properties;
    
    //Critical field
    private ArrayList<Double> doubleBuff;
    private double[] data;
    
    public SignalData(String[] field, String[] value) {
//        if(s==null){
//            String[] t = {"","","","0","0",""};
//            s = t;
//        }
//        this.signalType = s[0];
//        this.signalName = s[1];
//        this.signalGroup = s[2];
//        this.max = Double.parseDouble(s[3]);
//        this.min = Double.parseDouble(s[4]);
//        this.units = s[5];
        if(field.length != value.length){
            try {
                throw new ArrayLengthException();
            } catch (ArrayLengthException e) {
                e.printStackTrace();
            }
        }
        properties = new HashMap<>();
        for(int index = 0; index<field.length; index++){
            properties.put(field[index], value[index]);
        }
        doubleBuff = new ArrayList<Double>();
    }

    public double[] queryArray(int index, int num) throws NoDataException,
            ArrayOverflowException {
        if (this.size() == 0) {
            throw new NoDataException();
        } else if (index < 0 || index + num > this.size()) {
            throw new ArrayOverflowException();
        }
        double[] queryAns = new double[num];
        System.arraycopy(this.data, index, queryAns, 0, num);
        return queryAns;
    }
    
    public double queryRecord(int index){
        return index>this.data.length?this.data[data.length-1]:this.data[index];
    }

    public void postProgress() {
        int len = this.doubleBuff.size();
        this.data = new double[len];
        for (int k = 0; k < len; k++) {
            this.data[k] = this.doubleBuff.get(k);
        }
        this.doubleBuff.clear();
        this.doubleBuff = null;
    }

    public void add(double record) {
        this.doubleBuff.add(record);
    }

    public int size() {
        return this.data.length;
    }

    @Override
    public String toString() {
//        return "Signal Tpye: " + this.signalType + ";\n" + "Signal Name: "
//                + this.signalName + ";\n" + "Signal Group: " + this.signalGroup
//                + ";\n" + "Number of data: " + this.size() + "\n"
//                + "Maximum value: " + this.max + "\n" + "Minimum value "
//                + this.min + "\n" + "Units: " + this.units;
        String ans = "<html>";
        Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
            ans += "<B>" + entry.getKey() + ":</B>  " + entry.getValue() + "<br>";
        }
        ans += "</html>";
        return ans;
    }
}
