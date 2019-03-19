package com.example.cloniamix.weatherapp;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

// TODO: 19.03.2019 Добавить необходимые библиотеки для работы с RecyclerView
// TODO: 19.03.2019 В activity_main добавить RecyclerView
// TODO: 19.03.2019 Добавить RecyclerView в Main Activity (адаптер и т.п.)
// TODO: 19.03.2019 В item_city_weather применить единый стиль и посмотреть какие значения рекомендует material design