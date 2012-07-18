package jp.m11.texasholdemdealer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		Judgement judgement = new Judgement();
		judgement.setCard(51, 0);
		judgement.setCard(1, 1);
		judgement.setCard(2, 2);
		judgement.setCard(3, 3);
		judgement.setCard(4, 4);
		judgement.setCard(20, 5);
		judgement.setCard(0, 6);
		judgement.judge();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


}
