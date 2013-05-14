package com.h5n1.eventsys;

public static class JsonRequester {
	private static String json;
	private static JSONObject jsonObj;
	private static InputStream is;

	private static final String TAG_EVENTS = "events";
	private static final String TAG_CONTENT = "content";
	private static final String TAG_TYPE = "type";
	private static final String TAG_TIME = "time";
	private static final String TAG_ID = "id";
	private static final String CREATE_URL = "http://localhost/ubicomp/create_event.php";
	private static final String GET_All_URL = "http://localhost/ubicomp/get_all_events.php";
	private static final String UPDATE_URL = "http://localhost/ubicomp/update_event.php";
	private static final String DELETE_URL = "http://localhost/ubicomp/delete_event.php";
	private static final String GET_URL = "http://localhost/ubicomp/get_event.php";

	public static void newEvent(String type, String content) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG_TYPE, type));
		params.add(new BasicNameValuePair(TAG_CONTENT, content));
		makeHttpRequest(CREATE_URL, "POST", params);
	}

	public static void updateEvent(String id, String type, String content) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG_ID, id));
		params.add(new BasicNameValuePair(TAG_TYPE, type));
		params.add(new BasicNameValuePair(TAG_CONTENT, content));
		makeHttpRequest(UPDATE_URL, "POST", params);
	}

	public static void getEvent(String id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG_ID, id));
		makeHttpRequest(GET_URL, "GET", params);
	}

	public static void deleteEvent(String id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id));
		makeHttpRequest(DELETE_URL, "POST", params);
	}

	public static void getAllEvents() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject getJsonObj = makeHttpRequest(GET_All_URL, "GET", params);
		try {
			events = getJsonObj.getJSONArray(TAG_EVENTS);
			for (int i = 0; i < events.length(); i++) {
				JSONObject c = events.getJSONObject(i);
				String id = c.getString(TAG_ID);
				String type = c.getString(TAG_ID);
				String time = c.getString(TAG_CONTENT);
				String content = c.getString(TAG_CONTENT);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(TAG_ID, id);
				map.put(TAG_TYPE, type);
				map.put(TAG_TIME, time);
				map.put(TAG_CONTENT, content);
				eventsList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
					is, "iso-8859-1"), 8);
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