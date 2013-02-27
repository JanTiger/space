package org.jan.webapp.hms.cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jan.webapp.hms.model.page.Online;


/**
 * @author Jan.Wang
 *
 */
public class OnlineCache {

    private Map<String, Online> onlines = new HashMap<String, Online>();

    private static OnlineCache cache = new OnlineCache();

    private OnlineCache(){}

    public static OnlineCache getInstance(){
        return cache;
    }

    public void addOnline(Online online){
        if(null != online)
            onlines.put(online.getLoginName(), online);
    }

    public void removeOnline(String loginName){
        if(null != loginName)
            onlines.remove(loginName);
    }

    public List<Online> getOnlines(Online online){
        List<Online> result = new ArrayList<Online>();
        for(Online o : onlines.values()){
            result.add(o);
        }
        Collections.sort(result, new OnlineComparator(online.getSort()));
        return result;
    }

    public int size(){
        return onlines.size();
    }

}
class OnlineComparator implements Comparator<Online> {

    private Method method;

    OnlineComparator(String compareName){
        try {
            method = Online.class.getMethod(String.format("get%s%s", compareName.substring(0, 1).toUpperCase(), compareName.substring(1)));
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Online o1, Online o2) {
        String s1 = viewToCompareValue(o1);
        String s2 = viewToCompareValue(o2);
        return s1.compareTo(s2);
    }

    private String viewToCompareValue(Online online){
        try {
            return (String) method.invoke(online);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }

}