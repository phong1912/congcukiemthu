
package webLib;


public class MyLib {
    public static String getParameter(String name,javax.servlet.http.HttpServletRequest request){
        String value = request.getParameter(name);
        if (value == null) {
                value = "";
        }
        return value;
    }    
}
