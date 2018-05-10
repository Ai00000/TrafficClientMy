/**
 *
 */
package com.mad.trafficclient.fragment;


import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mad.trafficclient.R;
import com.mad.trafficclient.httppost.HttpThread;
import com.mad.trafficclient.util.LoadingDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * @author zhaowei
 *
 */
public class FragmentHome extends Fragment
{
    private Button  btQueryDefaultHttpClient,btQueryVolley,btQueryAsync;
    private TextView twInfo;

    private TextView twResult;
    private String strUrl;
    private String strJson;
    private EditText etUrl;
    private EditText etPort;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		twResult = (TextView) view.findViewById(R.id.textview_result);
		etUrl = (EditText) view.findViewById(R.id.edit_eg_url);
		etPort = (EditText) view.findViewById(R.id.edit_eg_port);
		twResult.setText(R.string.info_result);

		twInfo = (TextView) view.findViewById(R.id.textview_info);
		twInfo.setText(R.string.info_app);

		return view;
	}

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
     */
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

	/**
	* @Title: initView
	* @Description: TODO
	* @return void
	* @throws
	*/
	private void initView() {

		btQueryDefaultHttpClient =(Button) getActivity().findViewById(R.id.button_query_DefaultHttpClient);
		btQueryVolley =(Button) getActivity().findViewById(R.id.button_query_Volley);

		btQueryDefaultHttpClient.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	queryDefaultHttpClient();
            }
        });
		btQueryVolley.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	queryVolley();
            }
        });

	}


	private void queryDefaultHttpClient(){

        strUrl = "http://"+ etUrl.getText() +":" +etPort.getText() + "/transportservice/action/GetRoadStatus";
        strJson = "{\"RoadId\":" + Integer.valueOf("1") + "}";

        System.out.println("start url:" + strUrl);
        System.out.println("start strJson:" + strJson);

        HttpThread jsonThread = new HttpThread(getActivity(), mHandler);
        jsonThread.setUrl(strUrl);
        jsonThread.setJsonstring(strJson);
        jsonThread.start();


	}

	/**
	* @Title: queryVolley
	* @Description: TODO
	* @return void
	* @throws
	*/
	private void queryVolley(){

		LoadingDialog.showDialog(getActivity());
			JSONObject params = new JSONObject();
			try {
				params.put("BusStationId", 1);
				params.put("UserName", "jack");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        strUrl = "http://"+ etUrl.getText() +":" +etPort.getText() + "/transportservice/action/GetBusStationInfo.do";

			RequestQueue mQueue = Volley.newRequestQueue(getActivity());
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, strUrl, params, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					// TODO Auto-generated method stu
					Log.d("TAG", response.toString());
					String str = response.toString();
					resultSettext(str);

					LoadingDialog.disDialog();
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					LoadingDialog.disDialog();
				}
			});
			mQueue.add(jsonObjectRequest);


	}

	/**
	 *
	 */
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
        	twResult.setText( "" );
            if (msg.what == 1 || msg.what == 901) {
            	twResult.setText( (String) msg.obj );
            }
        }
    };

    /**
    * @Title: resultSettext
    * @Description: TODO
    * @param @param stContent
    * @return void
    * @throws
    */
    private void resultSettext(String stContent) {
    	twResult.setText( "" );
    	twResult.setText( stContent );
	}

}
