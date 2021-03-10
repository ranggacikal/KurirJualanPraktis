package com.example.kurirjualanpraktis.model.login;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("response")
	private ResponseItem response;

	public void setResponse(ResponseItem response){
		this.response = response;
	}

	public ResponseItem getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"ResponseLogin{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}