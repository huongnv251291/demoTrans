package vn.icheck.drag.demo.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    float dY;
    int lastAction;
    private View dragViewCurr, dragView1, dragView2;
    private float startEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = findViewById(R.id.acbtest);
        dragView1 = findViewById(R.id.draggable_view_1);
        dragView1.post(new Runnable() {
            @Override
            public void run() {
                int dra = dragView1.getHeight();
                Log.e("run", "" + dra);
                dragView1.setTranslationY(-dra * 2 / 3);
            }
        });
        dragView2 = findViewById(R.id.draggable_view_2);
        dragView2.post(new Runnable() {
            @Override
            public void run() {
                int dra = dragView2.getHeight();
                Log.e("run", "" + dra);
                dragView2.setTranslationY(dra * 2 / 3);
            }
        });
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                lastAction = MotionEvent.ACTION_DOWN;
                startEvent = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                float temp = event.getRawY() - startEvent;
                Log.e("ACTION_MOVE", temp + "");
                if (dragViewCurr == null) {
                    if (temp < 0) {
                        dragViewCurr = dragView2;
                    } else {
                        dragViewCurr = dragView1;
                    }
                    dY = dragViewCurr.getHeight();
                }


                switch (dragViewCurr.getId()) {
                    case R.id.draggable_view_1:
                        dragViewCurr.setTranslationY(temp-dY);
                        Log.e("draggable_view_1", temp + "");
                        break;
                    case R.id.draggable_view_2:

                        dragViewCurr.setTranslationY(temp);
                        Log.e("draggable_view_2",temp + "");
                        break;
                }
//                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) dragView.getLayoutParams();
//                layoutParams.topMargin = (int) (layoutParams.topMargin + temp);
//                dragView.setLayoutParams(layoutParams);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                dragViewCurr = null;
                if (lastAction == MotionEvent.ACTION_DOWN)
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                        }
                    });

                break;

            default:
                return false;
        }
        return true;
    }
}
