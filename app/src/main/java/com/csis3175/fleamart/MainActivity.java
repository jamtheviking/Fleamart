package com.csis3175.fleamart;

import android.os.Bundle;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private Scene scene1, scene2;
    private Transition slideUpTransition;
    private boolean scene1Showing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TransitionConfig();



    }

    public void switchScene(View view) {
        if (scene1Showing) {

            TransitionManager.go(scene2, slideUpTransition);
            scene1Showing = false;
        }
        else {
            TransitionManager.go(scene1, slideUpTransition);
            scene1Showing = true;
        }

    }
    public void TransitionConfig(){
        ViewGroup sceneRoot;
        sceneRoot = findViewById(R.id.scene_root);
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.login_scene, this);
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_main, this);
        slideUpTransition = new Slide(Gravity.BOTTOM);
        slideUpTransition.setDuration(800);

    }
}
