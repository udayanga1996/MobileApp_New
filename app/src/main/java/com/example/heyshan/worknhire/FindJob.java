package com.example.heyshan.worknhire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class FindJob extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_job);

        Button plumberbutton=findViewById(R.id.btnPlumber);
        Button cookerbutton=findViewById(R.id.btnCooker);
        Button Electricalbutton=findViewById(R.id.btnElectricalRepair);
        Button ACbutton=findViewById(R.id.btnACservice);
        Button Painterbutton=findViewById(R.id.btnPainter);
        Button Housekeeperbutton=findViewById(R.id.btnhouseKeepr);
        Button carpenterbutton=findViewById(R.id.btncarpenter);
        Button otherbutton=findViewById(R.id.btnOther);

        plumberbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindJob.this,
                        "Plumbers are selected", Toast.LENGTH_SHORT).show();
                Intent selectemployee=new Intent(FindJob.this,SelectEmployee.class);
                selectemployee.putExtra("apiurl","Plumber");
                startActivity(selectemployee);

            }
        });

        cookerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindJob.this,
                        "cooks are selected", Toast.LENGTH_SHORT).show();
                Intent selectemployee=new Intent(FindJob.this,SelectEmployee.class);
                selectemployee.putExtra("apiurl","Cook");
                startActivity(selectemployee);

            }
        });

        Electricalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindJob.this,
                        "Electrical repairs are selected", Toast.LENGTH_SHORT).show();
                Intent selectemployee=new Intent(FindJob.this,SelectEmployee.class);
                selectemployee.putExtra("apiurl","ElectricalRepair");
                startActivity(selectemployee);

            }
        });

        ACbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindJob.this,
                        "AC servicers are selected", Toast.LENGTH_SHORT).show();
                Intent selectemployee=new Intent(FindJob.this,SelectEmployee.class);
                selectemployee.putExtra("apiurl","ACservice");
                startActivity(selectemployee);

            }
        });

        Painterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindJob.this,
                        "Painters are selected", Toast.LENGTH_SHORT).show();
                Intent selectemployee=new Intent(FindJob.this,SelectEmployee.class);
                selectemployee.putExtra("apiurl","Painter");
                startActivity(selectemployee);

            }
        });

        Housekeeperbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindJob.this,
                       "House keepers are selected", Toast.LENGTH_SHORT).show();
                Intent selectemployee=new Intent(FindJob.this,SelectEmployee.class);
                selectemployee.putExtra("apiurl","Housekeeper");
                startActivity(selectemployee);

            }
        });

        carpenterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindJob.this,
                        "Carpenters are selected", Toast.LENGTH_SHORT).show();
                Intent selectemployee=new Intent(FindJob.this,SelectEmployee.class);
                selectemployee.putExtra("apiurl","Carpenter");
                startActivity(selectemployee);
            }
        });

        otherbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectemployee=new Intent(FindJob.this,SelectEmployee.class);
                selectemployee.putExtra("apiurl","Others");
                startActivity(selectemployee);

            }
        });


    }


}
