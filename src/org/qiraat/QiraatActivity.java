					/* In the name of GOD, the Most Gracious, the Most Merciful */
package org.qiraat;

import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class QiraatActivity extends Activity {
	private ListView suraListView;
	int[] numAyasList = new int[114];
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
        	
        	setContentView(R.layout.main);
        	suraListView = (ListView)findViewById(R.id.surahList);
        	
        	ArrayList<String> surahList = new ArrayList<String>();
            
            
            XmlPullParser xpp=this.getResources().getXml(R.xml.qurandata);
            surahList = MetaDataParser.getSurahNames(this,xpp);
        	
            xpp=this.getResources().getXml(R.xml.qurandata);
            numAyasList = MetaDataParser.getNumAyas(this,xpp);
            
            CustomSuraListAdapter customAdapter = new CustomSuraListAdapter(this, surahList);
            suraListView.setAdapter(customAdapter);
            
            //OnCLick Example..
            suraListView.setOnItemClickListener(new OnItemClickListener()
            {
            	
            	@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
    				// TODO Auto-generated method stub
    				try
    				{
    					Intent displaySurahIntent = new Intent(QiraatActivity.this,DisplaySuraActivity.class);
    					Bundle bundle = new Bundle();
    					bundle.putString("position1", position+1+"");
    					bundle.putInt("position", position+1);
    					bundle.putInt("numAyas", QiraatActivity.this.numAyasList[position]);
    					String suraName = arg0.getItemAtPosition(position).toString();
    					bundle.putString("suraName",suraName);
    					displaySurahIntent.putExtras(bundle);
    					QiraatActivity.this.startActivity(displaySurahIntent);
    				}
    				catch(Exception exe)
    				{
    					Log.d("df", exe.toString());
    				}
    			}
            });
        }
        catch(Exception ex)
        {
        	AlertDialog.Builder adb=new AlertDialog.Builder(QiraatActivity.this);
			adb.setTitle("Error!");
			adb.setMessage("Encountered Exception: "+ex.toString());
			adb.setNegativeButton("Cancel", null);
			adb.show();
        }
        
        
    }
}