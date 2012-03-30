package com.atl.extensions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.webobjects.foundation.NSDictionary;

import er.rest.ERXRestRequestNode;
import er.rest.format.ERXJSONRestParser;
import er.rest.format.ERXStringRestRequest;

public class ATLHttpClientUtilities {
  
  public static String get(String server, String path, boolean isSecure, int port){
    String resultString = null;
    DefaultHttpClient httpclient = new DefaultHttpClient();
    try {
        HttpEntity entity = httpGetResponse(server, path, isSecure, port, httpclient).getEntity();
        resultString = EntityUtils.toString(entity);
    } 
    catch (IOException e1) {
      resultString = "IOException";
      e1.printStackTrace();
    }
    finally {httpclient.getConnectionManager().shutdown();}
    return resultString ;
    
  }
  
  public static String get(String server, String path, boolean isSecure){
    return get(server, path, isSecure, defaultPort(isSecure));
  }
  
  @SuppressWarnings("unchecked")
  public static NSDictionary getRest(String server, String path, boolean isSecure, int port){
    String resultString = ATLHttpClientUtilities.get(server, path, isSecure);
    return parseRest(resultString); 
  }
  
  @SuppressWarnings("unchecked")
  public static NSDictionary getRest(String server, String path, boolean isSecure){
    return getRest(server, path, isSecure, defaultPort(isSecure));
  }
  
  public static String delete(String server, String path, boolean isSecure, int port){
    String resultString = null;
    DefaultHttpClient httpclient = new DefaultHttpClient();
    try {
      HttpEntity entity = httpDeleteResponse(server, path, isSecure, port, httpclient).getEntity();
      resultString = EntityUtils.toString(entity);
    } 
    catch (IOException e1) {
      resultString = "IOException";
      e1.printStackTrace();
    }
    finally {httpclient.getConnectionManager().shutdown();}
    return resultString;
  }
  
  public static String delete(String server, String path, boolean isSecure){
    return delete(server, path, isSecure, defaultPort(isSecure));
  }
  
  @SuppressWarnings("unchecked")
  public static NSDictionary deleteRest(String server, String path, boolean isSecure, int port){
    String resultString = ATLHttpClientUtilities.delete(server, path, isSecure);
    return parseRest(resultString);
  }
  
  @SuppressWarnings("unchecked")
  public static NSDictionary deleteRest(String server, String path, boolean isSecure){
    return deleteRest(server, path, isSecure, defaultPort(isSecure));
  }
  
  private static List<? extends org.apache.http.NameValuePair> toList(NSDictionary<String, ?> dict){
    List<NameValuePair> list = new ArrayList<NameValuePair>(dict.allKeys().count());
    for (int i = 0; i < dict.allKeys().count(); i++) {
      String key= dict.allKeys().objectAtIndex(i);
      String value = (String) dict.valueForKey(key);
      list.add(new BasicNameValuePair(key, value));
    }
    
    return list;
  }
  
  public static String post(String server, String path, NSDictionary<String, String> dict, boolean isSecure, int port){
    String resultString = null;
    DefaultHttpClient httpclient = new DefaultHttpClient();
    try {
      
      HttpEntity entity = httpPostResponse(server, path, dict, isSecure, port, httpclient).getEntity();
      resultString = EntityUtils.toString(entity);
    } 
    catch (IOException e1) {
      resultString = "IOException";
      e1.printStackTrace();
    }
    finally {httpclient.getConnectionManager().shutdown();}
    return resultString;
  }
  
  public static String post(String server, String path, NSDictionary<String, String> dict, boolean isSecure){
    return post(server, path, dict, isSecure, defaultPort(isSecure));
  }
  
  @SuppressWarnings("unchecked")
  public static NSDictionary postRest(String server, String path, NSDictionary<String, String> dict, boolean isSecure, int port){
    String resultString = ATLHttpClientUtilities.post(server, path, dict, isSecure);
    return parseRest(resultString);
  }
  
  @SuppressWarnings("unchecked")
  public static NSDictionary postRest(String server, String path, NSDictionary<String, String> dict, boolean isSecure){
    return postRest(server, path, dict, isSecure, defaultPort(isSecure));
  }
  
  
  public static String put(String server, String path, NSDictionary<String, String> dict, boolean isSecure, int port){
    String resultString = null;
    DefaultHttpClient httpclient = new DefaultHttpClient();
    try {
      
      HttpEntity entity = httpPutResponse(server, path, dict, isSecure, port, httpclient).getEntity();
      resultString = EntityUtils.toString(entity);
    } 
    catch (IOException e1) {
      resultString = "IOException";
      e1.printStackTrace();
    }
    finally {httpclient.getConnectionManager().shutdown();}
    return resultString;
    
  }
  
  public static String put(String server, String path, NSDictionary<String, String> dict, boolean isSecure){
    return put(server, path, dict, isSecure, defaultPort(isSecure));
  }
  
  @SuppressWarnings("unchecked")
  public static NSDictionary putRest(String server, String path, NSDictionary<String, String> dict, boolean isSecure, int port){
    String resultString = ATLHttpClientUtilities.put(server, path, dict, isSecure);
    return parseRest(resultString);
  }
  
  @SuppressWarnings("unchecked")
  public static NSDictionary putRest(String server, String path, NSDictionary<String, String> dict, boolean isSecure){
    return putRest(server, path, dict, isSecure, defaultPort(isSecure));
  }
  
  private static int defaultPort(boolean isSecure) {
    if (isSecure){return 443;}
    else {return 80;}
  }
  
  private static HttpHost httpHost(String server, boolean isSecure, int port) {
    HttpHost target;
    if (isSecure){target = new HttpHost(server, port, "https");}
    else {target = new HttpHost(server, port, "http");}
    return target;
  }
  
  private static HttpResponse httpGetResponse(String server, String path, boolean isSecure, int port, DefaultHttpClient httpclient) throws IOException, ClientProtocolException {
    return httpclient.execute(httpHost(server, isSecure, port),  new HttpGet(path));
  }
  
  private static HttpResponse httpDeleteResponse(String server, String path, boolean isSecure, int port, DefaultHttpClient httpclient) throws IOException, ClientProtocolException {
    return httpclient.execute(httpHost(server, isSecure, port), new HttpDelete(path));
  }
  
  @SuppressWarnings("unchecked")
  private static HttpResponse httpPostResponse(String server, String path, NSDictionary dict, boolean isSecure, int port, DefaultHttpClient httpclient) throws IOException, ClientProtocolException {
    HttpPost req = new HttpPost(path);
    req.setEntity(new UrlEncodedFormEntity(ATLHttpClientUtilities.toList(dict)));
    return httpclient.execute(httpHost(server, isSecure, port), req);
  }
  
  @SuppressWarnings("unchecked")
  private static HttpResponse httpPutResponse(String server, String path, NSDictionary dict, boolean isSecure, int port, DefaultHttpClient httpclient) throws IOException, ClientProtocolException {
    HttpPut req = new HttpPut(path);
    req.setEntity(new UrlEncodedFormEntity(ATLHttpClientUtilities.toList(dict)));
    return httpclient.execute(httpHost(server, isSecure, port), req);
  }
  
  @SuppressWarnings("unchecked")
  private static NSDictionary parseRest(String resultString) {
    ERXJSONRestParser parser = new ERXJSONRestParser();
    ERXStringRestRequest request = new ERXStringRestRequest(resultString);
    ERXRestRequestNode requestNode = parser.parseRestRequest(request, null, null);
    return (NSDictionary) requestNode.toNSCollection(null);
  }

}
