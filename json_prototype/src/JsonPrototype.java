import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;


public class JsonPrototype {
	public static void main(String[] args) {
		
	}
	
	public JsonPrototype() {
		String serverUrl = "";

		JSONObject jsonObject = new JSONObject();
		
		int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
		HttpClient client = new DefaultHttpClient(httpParams);

		HttpPost request = new HttpPost(serverUrl);
		try {
			request.setEntity(new ByteArrayEntity(
			    jsonObject.toString().getBytes("UTF8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			HttpResponse response = client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
