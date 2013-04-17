import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

public class JsonPrototype {

	static int TIMEOUT_MILLISEC = 10000; // = 10 second
	static String SERVER_URL = "";
	
	enum EventType {
		GPS, RFID, MOTION
	}

	JFrame frame;
	JLabel response;

	public static void main(String[] args) {

		new JsonPrototype();
	}

	public JsonPrototype() {

		AbstractEvent event = new AbstractEvent(EventType.GPS, new Date(
				System.currentTimeMillis()),
				"Hier koennte ihre Werbung stehen!");
		
		frame = new JFrame("JsonPrototype");
		response = new JLabel("");
		String s = "Sending " + event.type.name() + " Event @"
				+ event.time.toString() + " with data " + event.eventData;
		frame.add(new JLabel(s));
		frame.add(response);
		frame.setVisible(true);
		
		jsonHttpRequest(SERVER_URL, event.getJsonObject());
	}

	private void jsonHttpRequest(String serverUrl, JSONObject jsonObject) {

		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
		HttpClient client = new DefaultHttpClient(httpParams);

		HttpPost request = new HttpPost(serverUrl);
		try {
			request.setEntity(new ByteArrayEntity(jsonObject.toString()
					.getBytes("UTF8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			HttpResponse response = client.execute(request);
			
			this.response.setText(response.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class AbstractEvent {

		EventType type;
		Date time;
		String eventData;

		public AbstractEvent(EventType type, Date time, String data) {
			this.eventData = data;
			this.time = time;
			this.type = type;
		}

		public JSONObject getJsonObject() {
			JSONObject object = new JSONObject();
			object.append("type", type);
			object.append("time", time);
			object.append("data", eventData);
			return object;
		}
	}
}
