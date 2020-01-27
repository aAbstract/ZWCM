package com.example.android1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
//import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import org.w3c.dom.Text;
//import android.widget.TextView;

public class MainActivity extends Activity {

    public static Client client;
    public static boolean isConnected = false;

    public class Client {
        public void netWrite(char x) {
            this.moutput.println(x);
        }

        public void clearResources() {
            try {
                this.socket.close();
                this.moutput.close();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

        private Socket socket;
        private int mServerPort;
        private String mServerIP;
        private PrintWriter moutput;
        private TextView mShowState;
        private ImageButton mbtn;

        public Client(int _serverport, String _serverip, TextView _state, ImageButton btn) {
            this.mServerPort = _serverport;
            this.mServerIP = _serverip;
            this.mShowState = _state;
            this.mbtn = btn;
            //new Thread(new ClientThread()).start();
            try {
                this.socket = new Socket(mServerIP, mServerPort);
                this.moutput = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                this.mShowState.setTextColor(Color.GREEN);
                this.mShowState.setText("Connected");
                isConnected = true;
                mbtn.setImageResource(R.drawable.disconnect);
            } catch (UnknownHostException e) {
                //e.printStackTrace();
                this.mShowState.setTextColor(Color.RED);
                this.mShowState.setText("Disconnected");
                isConnected = false;
                mbtn.setImageResource(R.drawable.connect);
            } catch (IOException e) {
                //e.printStackTrace();
                this.mShowState.setTextColor(Color.RED);
                this.mShowState.setText("Disconnected");
                isConnected = false;
                mbtn.setImageResource(R.drawable.connect);
            } catch (Exception e) {
                //e.printStackTrace();
                this.mShowState.setTextColor(Color.RED);
                this.mShowState.setText("Disconnected");
                isConnected = false;
                mbtn.setImageResource(R.drawable.connect);
            }

        }

//        public void onClick(View view) {
//            try {
//                EditText et = (EditText) findViewById(R.id.EditText01);
//                String str = et.getText().toString();
//                PrintWriter out = new PrintWriter(new BufferedWriter(
//                        new OutputStreamWriter(socket.getOutputStream())),
//                        true);
//                out.println(str);
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        class ClientThread implements Runnable {
//
//            @Override
//            public void run() {
//
//                try {
//                    //InetAddress serverAddr = InetAddress.getByName(mServerIP);
//
//                    socket = new Socket(mServerIP, mServerPort);
//
//                } catch (UnknownHostException e1) {
//                    //Log.println(Log.DEBUG,"Socket","ERROR In Connection " + mServerIP + " : " + mServerPort);
//                    Log.println(Log.DEBUG,"Socket",e1.getMessage());
//                } catch (IOException e1) {
//                    //Log.println(Log.DEBUG,"Socket","ERROR In Connection" + mServerIP + " : " + mServerPort);
//                    Log.println(Log.DEBUG,"Socket",e1.getMessage());
//                }
//
//            }
//
//        }
    }

    RelativeLayout layout_joystick;
    ImageView image_joystick, image_border;
//    TextView textView1, textView2, textView3, textView4, textView5;

    JoyStickClass js;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textView1 = (TextView)findViewById(R.id.textView1);
//        textView2 = (TextView)findViewById(R.id.textView2);
//        textView3 = (TextView)findViewById(R.id.textView3);
//        textView4 = (TextView)findViewById(R.id.textView4);
//        textView5 = (TextView)findViewById(R.id.textView5);

        layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);
        js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        final EditText ip_text_box = (EditText) findViewById(R.id.ip_tv);
        final TextView state = (TextView) findViewById(R.id.state);

        final ImageButton connect = (ImageButton) findViewById(R.id.connect);

        ImageButton upbtn = (ImageButton) findViewById(R.id.up_action);
        ImageButton downbtn = (ImageButton) findViewById(R.id.down_action);
        ImageButton leftbtn = (ImageButton) findViewById(R.id.left_action);
        ImageButton rightbtn = (ImageButton) findViewById(R.id.right_action);

        ImageButton armUp = (ImageButton) findViewById(R.id.arm_up);
        ImageButton armDown = (ImageButton) findViewById(R.id.arm_down);
        ImageButton armStop = (ImageButton) findViewById(R.id.arm_stop);

        connect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!isConnected) {
                    String addr = ip_text_box.getText().toString();
                    String ip = addr.split(":")[0];
                    String port = addr.split(":")[1];
                    client = new Client(Integer.parseInt(port), ip, state, connect);
                    state.setTextColor(Color.GREEN);
                    state.setText("Connected: " + "192.168.43.163");
                    connect.setImageResource(R.drawable.disconnect);
                    isConnected = true;
                } else {
                    client.clearResources();
                    client = null;
                    state.setTextColor(Color.RED);
                    state.setText("Disconnected");
                    connect.setImageResource(R.drawable.connect);
                    isConnected = false;
                }
            }
        });

        upbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                client.netWrite('w');
            }
        });

        downbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                client.netWrite('s');
            }
        });

        leftbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                client.netWrite('a');
            }
        });

        rightbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                client.netWrite('d');
            }
        });

        armUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                client.netWrite('z');
            }
        });

        armDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                client.netWrite('c');
            }
        });

        armStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                client.netWrite('x');
            }
        });

        layout_joystick.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                client.netWrite('e');
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
//                    textView1.setText("X : " + String.valueOf(js.getX()));
//                    textView2.setText("Y : " + String.valueOf(js.getY()));
//                    textView3.setText("Angle : " + String.valueOf(js.getAngle()));
//                    textView4.setText("Distance : " + String.valueOf(js.getDistance()));

                    int direction = js.get4Direction();
                    if (direction == JoyStickClass.STICK_UP) {
//                        textView5.setText("Direction : Up");
                    } else if (direction == JoyStickClass.STICK_RIGHT) {
//                        textView5.setText("Direction : Right");
                    } else if (direction == JoyStickClass.STICK_DOWN) {
//                        textView5.setText("Direction : Down");
                    } else if (direction == JoyStickClass.STICK_LEFT) {
//                        textView5.setText("Direction : Left");
                    } else if (direction == JoyStickClass.STICK_NONE) {
//                        textView5.setText("Direction : Center");
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
//                    textView1.setText("X :");
//                    textView2.setText("Y :");
//                    textView3.setText("Angle :");
//                    textView4.setText("Distance :");
//                    textView5.setText("Direction :");
                }
                return true;
            }
        });
    }
}