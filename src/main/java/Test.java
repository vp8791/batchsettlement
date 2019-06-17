import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		List<String> names = new ArrayList<String>();
	       
		names.add("1233"); 
		names.add("3432397920662921");
		names.add("34323979206629213333");
		 
		String regex = "^[0-9]{10,16}$";
		 
		Pattern pattern = Pattern.compile(regex);
		 
		for (String name : names)
		{
		    Matcher matcher = pattern.matcher(name);
		    System.out.println(matcher.matches());
		}
		 

	}

}
