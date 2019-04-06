package app2.linuxduck.com.catgamethecatgame;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // ImageViews
    private ImageView catGamePlayImageView;

    // AnimationDrawables
    private AnimationDrawable catGamePlayAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ImageViews
        catGamePlayImageView = findViewById(R.id.catgameplayimageview);
        catGamePlayImageView.setImageResource(R.drawable.cat_play_animation);

        // AnimationDrawables
        catGamePlayAnimationDrawable = (AnimationDrawable) catGamePlayImageView.getDrawable();
        catGamePlayAnimationDrawable.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startActivity(new Intent(this, GameActivity.class));
        return true;
    }
}
