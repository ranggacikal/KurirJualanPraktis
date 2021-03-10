package com.example.kurirjualanpraktis.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "kurirjualanpraktis";
    private static final String KEY_ID = "keyid";
    private static final String KEY_TOKEN = "keytoken";
    private static final String KEY_NAMA = "keynama";
    private static final String KEY_FOTO = "keyfoto";
    private static final String KEY_KTP = "keyjk";
    private static final String KEY_JK = "keyjk";
    private static final String KEY_ALAMAT = "keyalamat";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_NOHP = "keynohp";
    private static final String KEY_IMEI = "keyimei";
    private static final String KEY_SIM = "keysim";
    private static final String KEY_STNK = "keystnk";
    private static final String KEY_JENIS = "keyjenis";
    private static final String KEY_MEREK = "keymerek";
    private static final String KEY_MILIK = "keymilik";
    private static final String KEY_STATUS = "keystatus";
    private static final String KEY_DATE = "keydate";

    private static SharedPrefManager mInstance;
    private static Context mCtx ;

    public SharedPrefManager(Context context) {
        mCtx = context ;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void updateFoto (Context context, String foto){
    }

    public void userLogin (loginuser user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID,user.getId());
        editor.putString(KEY_TOKEN,user.getToken());
        editor.putString(KEY_NAMA,user.getNama());
        editor.putString(KEY_FOTO,user.getFoto());
        editor.putString(KEY_KTP,user.getKtp());
        editor.putString(KEY_JK,user.getJk());
        editor.putString(KEY_ALAMAT,user.getAlamat());
        editor.putString(KEY_EMAIL,user.getEmail());
        editor.putString(KEY_PASSWORD,user.getPassword());
        editor.putString(KEY_NOHP,user.getNo_hp());
        editor.putString(KEY_IMEI,user.getImei());
        editor.putString(KEY_SIM,user.getSim());
        editor.putString(KEY_STNK,user.getStnk());
        editor.putString(KEY_JENIS,user.getJenis());
        editor.putString(KEY_MEREK,user.getMerek());
        editor.putString(KEY_MILIK,user.getMilik());
        editor.putString(KEY_STATUS,user.getStatus());
        editor.putString(KEY_DATE,user.getDate());
        //editor.putString(KEY_JK,user.getJk());

//        editor.putString(KEY_IDPROVINSI,user.getIdProvinsi());
//        editor.putString(KEY_IDKOTA,user.getIdKota());
//        editor.putString(KEY_IDKECAMATAN,user.getIdKecamatan());

        editor.apply();

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    //Mengecek sudah login atau belum
    public boolean isLoggedIn() {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;

    }

    //akan Memberikan login ke user
    public loginuser getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new loginuser(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_TOKEN, "-"),
                sharedPreferences.getString(KEY_NAMA, null),
                sharedPreferences.getString(KEY_FOTO,"-"),
                sharedPreferences.getString(KEY_KTP, "-"),
                sharedPreferences.getString(KEY_JK, "-"),
                sharedPreferences.getString(KEY_ALAMAT, "-"),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD,"-"),
                sharedPreferences.getString(KEY_NOHP, "-"),
                sharedPreferences.getString(KEY_IMEI,"-"),
                sharedPreferences.getString(KEY_SIM,"-"),
                sharedPreferences.getString(KEY_STNK,"-"),
                sharedPreferences.getString(KEY_JENIS,"-"),
                sharedPreferences.getString(KEY_MEREK,"-"),
                sharedPreferences.getString(KEY_MILIK,"-"),
                sharedPreferences.getString(KEY_STATUS,"-"),
                sharedPreferences.getString(KEY_DATE,"-")

        );
    }

    //logout user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
