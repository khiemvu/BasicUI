package com.qsoft.BasicUI;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: Khiemvx
 * Date: 10/8/13
 */
public class ProfileActivity extends Activity {

    static final int TIME_DIALOG_ID = 1111;
    private TextView tvUserName;
    private CheckBox cbShow;
    private Button btAddress;
    private Button btBirthday;
    private Button btOnline;
    private Button btGender;
    private Button btLogout;
    private Calendar cal;
    private TextView tvBirthday;
    private TextView tvOnline;
    private TextView tvAddress;
    private TextView tvRender;
    private RadioGroup rgbtGender;
    private RadioButton rbtMale;
    private RadioButton rbtFemale;
    private ListView lvFriend;
    private SimpleAdapter adapter;
    private Date dateUpdate;
    private int hour;
    private int minute;
    private String address = "Hải Dương";
    private String gender = "Male";

    String[] names = new String[]{"Name: Vũ Xuân Khiêm","Name: Nguyễn Hoàng Hải","Name: Nguyễn Lê Dung","Name: Đỗ Quang Ngọc"};
    String[] Phone = new String[]{"Phone: 124234","Phone: 124234","Phone: 124234","Phone: 124234"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        tvUserName = (TextView) findViewById(R.id.profile_tvUsername);
        tvBirthday = (TextView) findViewById(R.id.tvBirthdayResult);
        tvOnline = (TextView) findViewById(R.id.tvOnlineResult);
        tvAddress = (TextView) findViewById(R.id.tvAddressResult);
        tvAddress.setText(address);
        tvRender = (TextView) findViewById(R.id.tvGenderResult);
        lvFriend = (ListView) findViewById(R.id.lvFriend);
        tvRender.setText(gender);
        Intent intentLocal = getIntent();
        Bundle myBundle =  intentLocal.getExtras();
        String userName = myBundle.getString("USERNAME");
        tvUserName.setText("Hi, " + userName);
        tvUserName.setTextColor(R.string.Black);


        cbShow = (CheckBox) findViewById(R.id.cbListFriend);
        btBirthday = (Button) findViewById(R.id.btBirthday);
        btOnline = (Button) findViewById(R.id.btOnline);
        btAddress = (Button) findViewById(R.id.btAddress);
        btGender = (Button) findViewById(R.id.btGender);
        btLogout = (Button) findViewById(R.id.btLogout);
        rgbtGender = (RadioGroup) findViewById(R.id.rgbtGender);

        getDateTime();
        showListFriend();
        updateTime();
        updateGender();
        updateAddress();
        updateDate();
        logOut();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        tvAddress.setText(savedInstanceState.getString("ADDRESS"));
        tvBirthday.setText(savedInstanceState.getString("BIRTHDAY"));
        tvOnline.setText(savedInstanceState.getString("ONLINE"));
        tvRender.setText(savedInstanceState.getString("GENDER"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("ADDRESS", (String)tvAddress.getText());
        outState.putString("BIRTHDAY", (String)tvBirthday.getText());
        outState.putString("ONLINE", (String)tvOnline.getText());
        outState.putString("GENDER", (String)tvRender.getText());
    }

    private void showListFriend()
    {
        cbShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(cbShow.isChecked()){
                    simpleArray();
                }
                else{
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (ProfileActivity.this, android.R.layout.simple_list_item_2);
                    lvFriend.setAdapter(adapter);
                }
            }
        });
    }
    private void simpleArray(){
        String[]from = new String[] {"row_id", "col_1"};
        int[] to = new int[] { R.id.message_tv, R.id.time_tv};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < names.length; i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("row_id", "" + names[i]);
            map.put("col_1", "" + Phone[i]);
            fillMaps.add(map);
        }
        adapter = new SimpleAdapter(ProfileActivity.this, fillMaps, R.layout.list_item, from, to);
        lvFriend.setAdapter(adapter);
    }
    private void updateTime()
    {
        btOnline.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showDialog(TIME_DIALOG_ID);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListener, hour, minute,false);
        }
        return null;
    }
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hour   = hourOfDay;
            minute = minutes;
            updateTime(hour,minute);
        }
    };

    private void updateTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        tvOnline.setText(aTime);
    }

    private void logOut() {
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateAddress() {
        btAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] list = {"Hà Nội", "Hải Dương", "Hải Phòng", "Quảng Ninh"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Lựa chọn thành phố");
                final int checkedItem = -1;
                builder.setSingleChoiceItems(list, checkedItem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0){
                            address = "Hà Nội";
                        }
                        if(item == 1){
                            address = "Hải Dương";
                        }
                        if(item == 2){
                            address = "Hải Phòng";
                        }
                        if(item == 3){
                            address = "Quảng Ninh";
                        }
                    }
                });
                builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        tvAddress.setText(address);
                    }
                });
                builder.show();
            }
        });
    }
    private void updateGender() {
        btGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgbtGender.setVisibility(View.VISIBLE);
                rbtMale = (RadioButton) findViewById(R.id.rbtMale);
                rbtFemale = (RadioButton) findViewById(R.id.rbtFemale);
                rbtFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
                    {
                        if(rbtFemale.isChecked())  {
                            tvRender.setText(rbtFemale.getText());
                            rgbtGender.setVisibility(View.GONE);
                        }
                    }
                });
                rbtMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
                    {
                        if(rbtMale.isChecked()){
                            tvRender.setText(rbtMale.getText());
                            rgbtGender.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    private void updateDate() {
        btBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,int monthOfYear,int dayOfMonth) {
                        tvBirthday.setText((dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                        //save date update
                        cal.set(year, monthOfYear, dayOfMonth);
                        dateUpdate=cal.getTime();
                    }
                };
                String s=tvBirthday.getText()+"";
                String strArrtmp[]=s.split("/");
                int date=Integer.parseInt(strArrtmp[0]);
                int month=Integer.parseInt(strArrtmp[1])-1;
                int year=Integer.parseInt(strArrtmp[2]);
                DatePickerDialog pic=new DatePickerDialog(
                        ProfileActivity.this,
                        callback, year, month, date);
                pic.show();
            }
        });
    }

    private void getDateTime() {
        cal= Calendar.getInstance();
        SimpleDateFormat formatDate=null;
        formatDate=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String birthDay=formatDate.format(cal.getTime());
        tvBirthday.setText(birthDay);
        //Định dạng giờ
        formatDate=new SimpleDateFormat("hh:mm a",Locale.getDefault());
        String strTime=formatDate.format(cal.getTime());
        tvOnline.setText(strTime);
    }
}
