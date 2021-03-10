package com.example.kurirjualanpraktis.sharedPreferences;

public class loginuser {

    private String id;
    private String token;
    private String nama;
    private String foto;
    private String ktp;
    private String jk;
    private String alamat;
    private String email;
    private String password;
    private String no_hp;
    private String imei;
    private String sim;
    private String stnk;
    private String jenis;
    private String merek;
    private String milik;
    private String status;
    private String date;

    public loginuser(String id, String token, String nama, String foto, String ktp, String jk, String alamat,
                     String email, String password, String no_hp, String imei, String sim, String stnk, String jenis,
                     String merek, String milik, String status, String date) {
        this.id = id;
        this.token = token;
        this.nama = nama;
        this.foto = foto;
        this.ktp = ktp;
        this.jk = jk;
        this.alamat = alamat;
        this.email = email;
        this.password = password;
        this.no_hp = no_hp;
        this.imei = imei;
        this.sim = sim;
        this.stnk = stnk;
        this.jenis = jenis;
        this.merek = merek;
        this.milik = milik;
        this.status = status;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getStnk() {
        return stnk;
    }

    public void setStnk(String stnk) {
        this.stnk = stnk;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getMilik() {
        return milik;
    }

    public void setMilik(String milik) {
        this.milik = milik;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
