package com.kuyu.kuxianghui.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.ui.HtmlActivity;
import com.kuyu.kuxianghui.ui.LoginActivity;
import com.kuyu.kuxianghui.ui.ModifyPasswordActivity;
import com.kuyu.kuxianghui.ui.app.AppSharedPreferences;
import com.kuyu.kuxianghui.util.IntentFactory;


public class MineFragment extends BaseFragment implements  View.OnClickListener{
    private Button logoutButton;

    private RelativeLayout rl_customer;
    private RelativeLayout rl_cash;
    private RelativeLayout rl_order;
    private RelativeLayout rl_income;
    private RelativeLayout rl_complain;
    private RelativeLayout rl_change_pwd;
    private RelativeLayout rl_help;
    private RelativeLayout rl_feedback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        TextView tv= (TextView) mView.findViewById(R.id.testchangepwd);
        tv.setOnClickListener(this);
        findViews();
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 查找view
     */
    @Override
    protected void findViews() {
        logoutButton = (Button)mView.findViewById(R.id.btn_logout);
        rl_customer = (RelativeLayout)mView.findViewById(R.id.rl_customer);
        rl_cash = (RelativeLayout)mView.findViewById(R.id.rl_cash);
        rl_order = (RelativeLayout)mView.findViewById(R.id.rl_order);
        rl_income = (RelativeLayout)mView.findViewById(R.id.rl_income);
        rl_complain = (RelativeLayout)mView.findViewById(R.id.rl_complain);
        rl_change_pwd = (RelativeLayout)mView.findViewById(R.id.rl_change_pwd);
        rl_help = (RelativeLayout)mView.findViewById(R.id.rl_help);
        rl_feedback = (RelativeLayout)mView.findViewById(R.id.rl_feedback);


        rl_customer.setOnClickListener(this);
        rl_cash.setOnClickListener(this);
        rl_order.setOnClickListener(this);
        rl_income.setOnClickListener(this);
        rl_complain.setOnClickListener(this);
        rl_change_pwd.setOnClickListener(this);
        rl_help.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }

    @Override

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_customer:{
                startActivity(goHtmlActivity(this.getActivity(), getString(R.string.mine_customer), Constants.URL_MY_CUSTOMER));
                break;
            }
            case R.id.rl_cash:{
                startActivity(goHtmlActivity(this.getActivity(), getString(R.string.mine_get_money_history), Constants.URL_MY_CASH));
                break;
            }
            case R.id.rl_order:{
                startActivity(goHtmlActivity(this.getActivity(), getString(R.string.mine_order), Constants.URL_MY_ORDER));
                break;
            }
            case R.id.rl_income:{
                startActivity(goHtmlActivity(this.getActivity(), getString(R.string.mine_income), Constants.URL_MY_INCOME));
                break;
            }
            case R.id.rl_complain:{
                startActivity(goHtmlActivity(this.getActivity(), getString(R.string.mine_complaint), Constants.URL_MY_COMPLIANT));
                break;
            }
            case R.id.rl_change_pwd:{
                startActivity(new Intent(this.getActivity(), ModifyPasswordActivity.class));
//                startActivity(IntentFactory.goHomeActivity());
                break;
            }
            case R.id.rl_help:{
                startActivity(goHtmlActivity(this.getActivity(), getString(R.string.mine_help), Constants.URL_HELP));
                break;
            }
            case R.id.rl_feedback:{
                startActivity(goHtmlActivity(this.getActivity(), getString(R.string.mine_feedback), Constants.URL_FEEDBACK));
                break;
            }
            case R.id.btn_logout:{
                logout();
                break;
            }
        }
    }
    private void logout(){
        //TODO:退出功能待实现
        new AppSharedPreferences(getActivity().getApplicationContext()).remove_token();
        startActivity(new Intent(this.getActivity(), LoginActivity.class));
    }

//    private void logout(){
//        JSONObject request = VolleyRequest.getInstance(getActivity()).newGetMobileCodeRequest("18088827810");
//        Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                ToastUtils.makeText(getActivity(),response.toString());
//            }
//        };
//        AppController.customRequest(getActivity(),AppController.COMMON_REQUEST_TAG,request,onSuccess,
//                AppController.dErrorListener);
//    }

    public Intent goHtmlActivity(Context ctx,String title,String url) {
        Intent intent = new Intent(ctx, HtmlActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        return intent;
    }

}


