package com.h5n1.eventsys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.h5n1.eventsys.events.Event;

public class JsonRequester {
	private static String json;
	private static JSONObject jsonObj;
	private static InputStream is;
	private static ArrayList<HashMap<String, String>> eventsList = new ArrayList<HashMap<String, String>>();
	private static HashMap<Long, JSONObject> generatedEvents = new HashMap<Long, JSONObject>();
	private static JSONArray events;
	private static String token;
	
	private static Integer deviceID = (new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE));
	public static Integer getDeviceID() {
		return deviceID;
	}
	public static final String TAG_EVENTS = "events";
	public static final String TAG_CONTENT = "content";
	public static final String TAG_TYPE = "type";
	public static final String TAG_TIME = "time";
	public static final String TAG_ID = "id";

	private static final String CREATE_URL = "http://192.168.1.92/ubicomp/create_event.php";
	private static final String GET_All_URL = "http://192.168.1.92/ubicomp/get_all_events.php";
	private static final String UPDATE_URL = "http://192.168.1.92/ubicomp/update_event.php";
	private static final String DELETE_URL = "http://192.168.1.92/ubicomp/delete_event.php";
	private static final String GET_URL = "http://192.168.1.92/ubicomp/get_event.php";
	// private static final String CREATE_URL = "http://54.235.186.77/ubicomp/api/post/event";
	// private static final String DELETE_URL = "http://54.235.186.77/ubicomp/api/delete/event";
	// private static final String GET_URL = "http://54.235.186.77/ubicomp/api/get/event/";
	// private static final String TOKEN_URL = "http://54.235.186.77/ubicomp/api/get/token";

//	public static void getToken() {
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		JSONObject getJsonObj = makeHttpRequest(TOKEN_URL, "POST", params);	
//		token = (String) getJsonObj.get("token");
//		deviceID = (Integer) getJsonObj.get("device_id");
//	}
	
	public static JSONObject newEvent(Event event) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("type", event.getClass().getSimpleName() + "_" + event.getType().toString()));
		params.add(new BasicNameValuePair("content", event.toJsonString()));
		return makeHttpRequest(CREATE_URL, "POST", params);
	}

	public static JSONObject updateEvent(String id, String type, String content) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG_ID, id));
		params.add(new BasicNameValuePair(TAG_TYPE, type));
		params.add(new BasicNameValuePair(TAG_CONTENT, content));
		return makeHttpRequest(UPDATE_URL, "POST", params);
	}

	public static JSONObject getEvent(String id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id));
		return makeHttpRequest(GET_URL, "GET", params);
	}

	public static JSONObject deleteEvent(String id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id));
		return makeHttpRequest(DELETE_URL, "POST", params);
	}

	public static ArrayList<HashMap<String, String>> getAllEvents() {
		eventsList.clear();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject getJsonObj = makeHttpRequest(GET_All_URL, "GET", params);
		try {
			events = getJsonObj.getJSONArray(TAG_EVENTS);
			for (int i = 0; i < events.length(); i++) {
				JSONObject c = events.getJSONObject(i);
				String id = c.getString(TAG_ID);
				String type = c.getString(TAG_TYPE);
				String time = c.getString(TAG_TIME);
				String content = c.getString(TAG_CONTENT);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(TAG_ID, id);
				map.put(TAG_TYPE, type);
				map.put(TAG_TIME, time);
				map.put(TAG_CONTENT, content);
				eventsList.add(map);
			}
			return eventsList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject makeHttpRequest(String url, String method,
			List<NameValuePair> params) {
		try {
			if (method == "POST") {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			} else if (method == "GET") {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			System.out.println("Error converting result " + e.toString());
		}
		try {
			jsonObj = new JSONObject(json.trim());
		} catch (JSONException e) {
			System.out.println("Error parsing data " + e.toString());
		}
		return jsonObj;
	}
}