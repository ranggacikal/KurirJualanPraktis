package com.example.kurirjualanpraktis.model.login;

import com.google.gson.annotations.SerializedName;

public class ResponseItem{

	@SerializedName("date")
	private String date;

	@SerializedName("jk")
	private String jk;

	@SerializedName("milik")
	private String milik;

	@SerializedName("no_hp")
	private String noHp;

	@SerializedName("ktp")
	private String ktp;

	@SerializedName("stnk")
	private String stnk;

	@SerializedName("id_kurir")
	private String idKurir;

	@SerializedName("token")
	private String token;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("merek")
	private String merek;

	@SerializedName("password")
	private String password;

	@SerializedName("nama")
	private String nama;

	@SerializedName("foto")
	private String foto;

	@SerializedName("sim")
	private String sim;

	@SerializedName("jenis")
	private String jenis;

	@SerializedName("imei")
	private Object imei;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setJk(String jk){
		this.jk = jk;
	}

	public String getJk(){
		return jk;
	}

	public void setMilik(String milik){
		this.milik = milik;
	}

	public String getMilik(){
		return milik;
	}

	public void setNoHp(String noHp){
		this.noHp = noHp;
	}

	public String getNoHp(){
		return noHp;
	}

	public void setKtp(String ktp){
		this.ktp = ktp;
	}

	public String getKtp(){
		return ktp;
	}

	public void setStnk(String stnk){
		this.stnk = stnk;
	}

	public String getStnk(){
		return stnk;
	}

	public void setIdKurir(String idKurir){
		this.idKurir = idKurir;
	}

	public String getIdKurir(){
		return idKurir;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setMerek(String merek){
		this.merek = merek;
	}

	public String getMerek(){
		return merek;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setSim(String sim){
		this.sim = sim;
	}

	public String getSim(){
		return sim;
	}

	public void setJenis(String jenis){
		this.jenis = jenis;
	}

	public String getJenis(){
		return jenis;
	}

	public void setImei(Object imei){
		this.imei = imei;
	}

	public Object getImei(){
		return imei;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseItem{" + 
			"date = '" + date + '\'' + 
			",jk = '" + jk + '\'' + 
			",milik = '" + milik + '\'' + 
			",no_hp = '" + noHp + '\'' + 
			",ktp = '" + ktp + '\'' + 
			",stnk = '" + stnk + '\'' + 
			",id_kurir = '" + idKurir + '\'' + 
			",token = '" + token + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",merek = '" + merek + '\'' + 
			",password = '" + password + '\'' + 
			",nama = '" + nama + '\'' + 
			",foto = '" + foto + '\'' + 
			",sim = '" + sim + '\'' + 
			",jenis = '" + jenis + '\'' + 
			",imei = '" + imei + '\'' + 
			",email = '" + email + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}