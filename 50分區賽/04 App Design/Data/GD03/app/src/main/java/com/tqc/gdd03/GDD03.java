package com.tqc.gdd03;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class GDD03 extends Activity
{
  public static boolean bIfDebug = false;
  public static String TAG = "HIPPO_DEBUG";
  private TextView mTextView01;
  private EditText mEditText01;
  private Button mButton01,mButton02;
  private ListView mListView01;
  private Spinner mSpinner01;
  private ArrayList<String> mList = new ArrayList<String>();
  private ArrayList<String> mListForSpinner = new ArrayList<String>();
  private ArrayAdapter<String> adapter, adapterSpinner;

  // http://data.taipei/
  private String strURI = "";
  String[] apiArray = {"j01.json", "j02.json", "j03.json"};
  private int flag = 0;

  // The BroadcastReceiver that tracks network connectivity changes.
  private NetworkReceiver receiver = new NetworkReceiver();
  public static final String WIFI = "Wi-Fi";
  public static final String ANY = "Any";
  private static boolean wifiConnected = false;
  private static boolean mobileConnected = false;
  public static boolean refreshDisplay = true;
  public static String sPref = null;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    init();
  }

  private void init()
  {
    mTextView01 = (TextView) findViewById(R.id.main_textView1);
    mEditText01 = (EditText) findViewById(R.id.main_editText1);
    mButton01 = (Button) findViewById(R.id.main_button1);
    mButton02 = (Button) findViewById(R.id.main_button2);
    mListView01 = (ListView) findViewById(R.id.main_listView1);

    mEditText01.setText(strURI);
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        if(refreshDisplay)
        {
          mTextView01.setText(getString(R.string.str_parsing));
          getAssetJsonData(getApplicationContext(), strURI);
        }
        else
        {
          mTextView01.setText(getString(R.string.connection_error));
        }
      }
    });

    mButton02.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        if(mList != null && adapter != null)
        {
          // TO DO
          // 6. ????????????????????????????????? ListView ??? ??????TextView??????????????????????????????????????????


          flag = 0;
          strURI = "j01.json";
        }


      }
    });

    initSpinner();
  }

  private void initSpinner()
  {
    mListForSpinner = new ArrayList<>();
    // https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=e7c46724-3517-4ce5-844f-5a4404897b7d
    mListForSpinner.add("?????????????????????");
    // https://tcgbusfs.blob.core.windows.net/blobyoubike/YouBikeTP.gz
    mListForSpinner.add("YouBike????????????????????????????????????");
    //  https://data.taipei/api/getDatasetInfo/downloadResource?id=8a569b5b-0cb1-4c7f-a064-780b3301354c&rid=9744ea1c-aafb-471c-8bb8-04f398ad415a
    mListForSpinner.add("?????????????????????????????????");

    // TO DO: ?????????Spinner???????????????API????????????
    //   (4)	??????Spinner?????????????????????????????????API????????????????????????????????????YouBike????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????JSON??????????????????ListView??????

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if(id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public class NetworkReceiver extends BroadcastReceiver
  {
    @Override
    public void onReceive(Context context, Intent intent)
    {
      ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      if (connMgr != null)
      {
        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnected())
        {
          wifiConnected = activeNetwork.isConnected();
          mobileConnected = activeNetwork.isConnected();
        }
        else
        {
          wifiConnected = false;
          mobileConnected = false;
        }
      }
      else
      {
        wifiConnected = false;
        mobileConnected = false;
      }

      // 3. ?????????????????????????????????????????????Toast?????????WiFi????????????????????????????????????Toast?????????????????????????????????
      // TO DO


      // 4. ??????????????? (???????????????)?????????ListView??????
      // TO DO


    }
  }

  // Checks the network connection and sets the wifiConnected and mobileConnected
  // variables accordingly.
  private void updateConnectedFlags()
  {
    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connMgr != null)
    {
      NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
      if(activeNetwork != null && activeNetwork.isConnected())
      {
        wifiConnected = activeNetwork.isConnected();
        mobileConnected = activeNetwork.isConnected();
      }
      else
      {
        wifiConnected = false;
        mobileConnected = false;
      }
    }
    else
    {
      wifiConnected = false;
      mobileConnected = false;
    }
  }

  private String getAssetJsonData(Context ctx, String fileName)
  {
    String result = "";
    try
    {
      InputStream is = ctx.getAssets().open(fileName);
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      result = new String(buffer, "UTF-8");

      mTextView01.setText(getString(R.string.str_parsing_ok));
      mList = new ArrayList<String>();
      String siteInfo = "";
      try
      {
        // 4. ????????????json???????????????API JSON??????????????????ListView?????????
        // Hint: JSONArray???mList.add("")
        // TO DO
        switch(flag)
        {
          case 0:
            // ?????? name ??? addr
            try
            {
              JSONArray data1 = new JSONObject(result).getJSONObject("result").getJSONArray("results");
              // TO DO

            }
            catch(JSONException e)
            {
              Log.e(TAG, e.toString());
              e.printStackTrace();
            }
            break;
          case 1:
            // ?????? sna ??? ar
            try
            {
              JSONObject data2 = new JSONObject(result).getJSONObject("retVal");
              // TO DO

            }
            catch(JSONException e)
            {
              Log.e(TAG, e.toString());
              e.printStackTrace();
            }
            break;
          case 2:
            try
            {
              JSONArray data3 = new JSONArray(result);
              // TO DO

            }
            catch(JSONException e)
            {
              Log.e(TAG, e.toString());
              e.printStackTrace();
            }
            break;
          default:
            mList = new ArrayList<>();
            adapter = new ArrayAdapter<String>(GDD03.this, android.R.layout.simple_list_item_1, mList);
            break;
        }
        mListView01.setAdapter(adapter);
        // TO DO : ??????ListView????????????????????????????????????...


      }
      catch(Exception e)
      {
        e.printStackTrace();
        Log.e(TAG, e.toString());
      }

    }
    catch(IOException ex)
    {
      ex.printStackTrace();
      return null;
    }
    return result;
  }

  @Override
  protected void onStart()
  {
    super.onStart();
    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

    // Retrieves a string value for the preferences. The second parameter
    // is the default value to use if a preference value is not found.
    sPref = sharedPrefs.getString("listPref", "Wi-Fi");
    updateConnectedFlags();
  }

  @Override
  protected void onResume()
  {
    super.onResume();
      // 1. ?????????onResume()???????????????BroadcastReceiver (receiver??????)????????????????????????"android.net.conn.CONNECTIVITY_CHANGE"???????????????????????????????????????????????????????????????????????????NetworkReceiver()?????????????????????


  }

  @Override
  protected void onPause()
  {
    super.onPause();
    // 2. ???1.??????????????????onPause()????????????receiver?????????

  }
}
