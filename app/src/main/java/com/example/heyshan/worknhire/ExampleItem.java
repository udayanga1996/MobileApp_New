package com.example.heyshan.worknhire;

public class ExampleItem {
    private String mEmpname;
    private String mEmpEmail;
    private String mEmpAvailable;


    public ExampleItem(String empName, String empEmail,String empAvailable ){
        mEmpEmail=empEmail;
        mEmpname=empName;
        mEmpAvailable=empAvailable;
    }
    public String getName(){
        return mEmpname;
    }

    public String getEmail() {
        return mEmpEmail;
    }

    public  String getEmpAvailable(){return  mEmpAvailable;}
}
