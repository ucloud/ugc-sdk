package javasdk;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.net.*;
import java.io.*;


public class HttpClient {

	public static  String httpGet(String url) throws IOException {
		URL getUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		connection.setConnectTimeout(5*1000);
		
		connection.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}

		reader.close();
		connection.disconnect();

		return sb.toString();
	}

	public static String httpPost(String url, HashMap<String, String> header, String data) throws IOException{
		URL postUrl = new URL(url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) postUrl.openConnection();

		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);        // 设置该连接是可以输出的
		httpURLConnection.setRequestMethod("POST"); // 设置请求方式
		//httpURLConnection.setRequestProperty("charset", "utf-8");
			
		Iterator titer=header.entrySet().iterator();
		while(titer.hasNext()){
			Map.Entry ent=(Map.Entry)titer.next();  
			String keyt = ent.getKey().toString();  
			String valuet = ent.getValue().toString();
			httpURLConnection.setRequestProperty(keyt, valuet);
		}
	
		PrintWriter pw = new PrintWriter(new BufferedOutputStream(httpURLConnection.getOutputStream()));
		pw.write(data);                   // 向连接中输出数据（相当于发送数据给服务器）
		pw.flush();
		pw.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {    // 读取数据
			sb.append(line + "\n");
		}

		br.close();	
		return  sb.toString();
	}

}
