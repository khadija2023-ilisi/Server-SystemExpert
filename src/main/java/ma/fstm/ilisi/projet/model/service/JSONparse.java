package ma.fstm.ilisi.projet.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONparse implements Runnable {
	
	private Thread ref;
	public Thread getRef() {
		return ref;
	}

	public void setRef(Thread ref) {
		this.ref = ref;
	}

	public JSONparse() {
		ref = new Thread(this);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run()
	{
		while(true)
		{
		String url = "https://services3.arcgis.com/hjUMsSJ87zgoicvl/arcgis/rest/services/Covid_19/FeatureServer/0/query?where=1%3D1&outFields=RegionFr,Cases,Deaths,Recoveries&returnGeometry=false&outSR=4326&f=json";  // example url which return json data
	   JSONObject jr=JSONparse.parser(url);
	    try {
			JSONArray firstT =jr.getJSONArray("features");
			for(int i =0 ;i !=firstT.length();i++)
			{
				JSONObject atb = firstT.getJSONObject(i);
				JSONObject info = atb.getJSONObject("attributes");
				String nomReg = info.getString("RegionFr");
				int nbmort= info.getInt("Deaths");
				int nbRecov = info.getInt("Recoveries");
				int nbCase = info.getInt("Cases");
				  
				System.out.println(nomReg);
				System.out.println(nbmort);
				System.out.println(nbRecov);
				System.out.println(nbCase);
				// il faut qu' un contructeur le remplir 
				// et appeler la fonction de dao pour l'update ou bien(effacer tout l'ancien contenu et commer
				// une boucle des insertion
			}
			
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  
	   //System.out.println(jr);
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
	public JSONObject readJsonFromUrl(String link) throws IOException, JSONException {
	    InputStream input = new URL(link).openStream();
	    // Input Stream Object To Start Streaming.
	    try {                                 // try catch for checked exception
	      BufferedReader re = new BufferedReader(new InputStreamReader(input,"UTF-8"));
	    // Buffer Reading In UTF-8  
	      String Text = Read(re);         // Handy Method To Read Data From BufferReader
	      //System.out.println("hhhhhh"+Text);
	      //JSONArray jr=new JSONArray(Text);
	      JSONObject json = new JSONObject(Text);    //Creating A JSON 
	      //System.out.println("kkkkk"+json);
	      return json;    // Returning JSON
	    } catch (Exception e) {
	      return null;
	    } finally {
	      input.close();
	    }
	}
	
	/**
	 * 
	 */
	
	public String Read(Reader re) throws IOException {     // class Declaration
	    StringBuilder str = new StringBuilder();     // To Store Url Data In String.
	    int temp;
	    do {

	      temp = re.read();       //reading Charcter By Chracter.
	      str.append((char) temp);

	    } while (temp != -1);        
	    //  re.read() return -1 when there is end of buffer , data or end of file. 

	    return str.toString();

	}
	
	public static JSONObject parser(String url)
	{
		JSONparse reader = new JSONparse(); 
		JSONObject jr=null;
		try {
			jr= reader.readJsonFromUrl(url);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return jr;
	}
	/**
	 * 
	 */
	public static void main(String[] args) throws IOException, JSONException {
	   // String url = "https://services3.arcgis.com/hjUMsSJ87zgoicvl/arcgis/rest/services/Covid_19/FeatureServer/0/query?where=1%3D1&outFields=RegionFr,Cases,Deaths,Recoveries&returnGeometry=false&outSR=4326&f=json";  // example url which return json data
	    //JSONObject jr=JSONparse.parser(url);
	    //System.out.println(jr);
	 
	   
		  JSONparse info = new JSONparse();
		  info.getRef().start();
		  
	   
	}
}
