package wags;


import java.util.HashMap;

import wags.Common.AppController;
import wags.Common.ClientFactory;
import wags.Common.Tokens;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

/**
 * WagsEntry
 *
 * This is the entry point for this GWT application.
 * Check if user is currently logged in or not.
 * If they are logged in direct them to the Editor.
 * If they are not logged redirect them based on
 * the loc variable in URL.
 *
 * @author Robert Bost <bostrt@appstate.edu>
 *
 */

public class WagsEntry implements EntryPoint 
{
	
	@Override
	public void onModuleLoad() 
	{
		// Check if the user is logged in already.
		String isLoggedInURL = Proxy.getBaseURL()+"?cmd=GetUserDetails";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(isLoggedInURL));
				
		@SuppressWarnings("unused")
		final AppController app = ClientFactory.getAppController();
		
		try {
			@SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback(){
				@Override
				public void onResponseReceived(Request request,	Response response) 
				{
					WEStatus status = new WEStatus(response);
					if(status.getStat() == WEStatus.STATUS_ERROR) {
						History.newItem(Tokens.DEFAULT, false);
					}
					else if(status.getStat() == WEStatus.STATUS_SUCCESS){
						String loc = getParam();
						HashMap<String, String> message = status.getMessageMap();
						History.newItem(loc, false);
						AppController.setUserDetails(message);
					} else if(status.getStat() == WEStatus.STATUS_WARNING){
						Window.alert("WagsEntry: WEStatus = Warning");
						String loc = getParam();
						History.newItem(loc, false);
					}
					History.fireCurrentHistoryState(); 
				}
				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Could not connect to server.");
				}
			});
		}catch(RequestException e){
			Window.alert(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * So, I'd like to use Window.Location.getParameter(), but it doesn't 
	 * work for values after the '#', so I have to roll my own if I want 
	 * that to work. This is probably not the most efficient thing ever, 
	 * but it works.
	 * 
	 * Given a param, it will look through url and find the first occurence of 
	 * '?%param%=', then return whatever follows it (until it finds something 
	 * that is not a letter).
	 * 
	 * 
	 * @param param parameter in the URL that we're looking for
	 * 
	 * @return the value associated with it
	 */
	private String getParam() {
		StringBuilder sb = new StringBuilder();
		String url = Window.Location.getHref();
		char[] urlArr = url.toCharArray();
		
		int start = url.indexOf('#');
		
		if (start == -1) {
			return Tokens.DEFAULT; //param did not exist
		}
		
		for (int i = start + 1; i < urlArr.length; i++) {
			sb.append(urlArr[i]);
 		}
		
		return sb.toString();
	}
}
