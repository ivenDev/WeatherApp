package com.example.cloniamix.weatherapp.screen_add_new_city_activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.mvp.contract.base_view.IAddnewCityView;
import com.example.cloniamix.weatherapp.screen_add_new_city_activity.presenter.AddNewCityPresenter;
import com.example.cloniamix.weatherapp.screen_city_list_activity.ui.CitiesListActivity;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewCityActivity extends AppCompatActivity implements IAddnewCityView {

    private AddNewCityPresenter mPresenter;
    private EditText mCityNameEditText;


    //region lifeCycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_city);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.subscribe(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        /*mPresenter.unsubscribe();
        mPresenter = null;*/
    }
    //endregion

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public String getCityName(){
        if (mCityNameEditText.getText().length()!= 0 ){
            return mCityNameEditText.getText().toString();
        }
        return null;
    }

    // TODO: 10.04.2019 поработать с флагами и со стеком переходов назад
    public void goToActivity(){
        startActivity(new Intent(this, CitiesListActivity.class)
                /*.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)*/);
    }

    private void init(){
        mPresenter = new AddNewCityPresenter();
        mCityNameEditText = findViewById(R.id.city_name_edit);

        Button searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(v -> mPresenter.onSearchBtnClicked());

        Button cancelBtn = findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(v -> mPresenter.onCancelBtnClicked());
    }
}
