package com.luminous.s_version1;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RoundProgressBar barStroke;
    RoundProgressBar barStrokeText;
    RoundProgressBar barFill;
    int currentValue = 0;

    public int sNumber=0;
    public int todayNumber=0;
    public int baseNumber=0;
    public int goalNumber=6;



    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            SharedPreferences.Editor number= getSharedPreferences("data",MODE_PRIVATE).edit();
            barStroke = (RoundProgressBar) findViewById(R.id.barStroke);
            barStroke.setMax(goalNumber);

            switch (item.getItemId()) {
                case R.id.plus0_5:
                    sNumber+=1;
                    number.putInt("sNumber",sNumber);
                    number.apply();

                    barStroke.setValue(sNumber);
                    return true;
                case R.id.mines0_5:
                    sNumber-=1;
                    if (sNumber<0) {sNumber =0;}
                    number.putInt("sNumber",sNumber);
                    number.apply();

                    barStroke.setValue(sNumber);
                    return true;
                case R.id.reset:
                    sNumber=0;
                    number.putInt("sNumber",sNumber);
                    number.apply();

                    barStroke.setValue(sNumber);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences.Editor number= getSharedPreferences("data",MODE_PRIVATE).edit();
        SharedPreferences readnumber= getSharedPreferences("data",MODE_PRIVATE);
        sNumber= readnumber.getInt ("sNumber",0);
        goalNumber=readnumber.getInt("goalNumber",0);

        if (goalNumber==0){
            Toast.makeText(MainActivity.this,"目标值未设置",Toast.LENGTH_SHORT).show();
            goalNumber =8;
            number.putInt("goalNumber",goalNumber);
            number.apply();}

        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        barStroke = (RoundProgressBar) findViewById(R.id.barStroke);
        barStroke.setValue(sNumber);
        barStroke.setMax(goalNumber);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button setMax = (Button)findViewById(R.id.setmax);
        setMax.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {

                AlertDialog.Builder setMaxDialog= new AlertDialog.Builder(MainActivity.this);
                final EditText editGoal = new EditText(MainActivity.this);
                         setMaxDialog.setTitle("设置目标值");
                setMaxDialog.setView(editGoal);
                setMaxDialog.setPositiveButton ("确认",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick (DialogInterface setMaxDialog,int which){
                        SharedPreferences.Editor number= getSharedPreferences("data",MODE_PRIVATE).edit();
                        String n=editGoal.getText().toString();
                        goalNumber = Integer.valueOf(n);
                        number.putInt("goalNumber",goalNumber);
                        number.apply();
                        barStroke.setValue(sNumber);
                        barStroke.setMax(goalNumber);
                        Toast.makeText(MainActivity.this,"目标值已设置为"+goalNumber,Toast.LENGTH_SHORT).show();
                    }
                        });
                setMaxDialog.setNegativeButton ("取消",null);
                setMaxDialog.show();
            }
        });


    }
}
