package com.just_app.diplom;

import android.app.Fragment;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class Fragment_menu extends Fragment {

    public static final String EXTRA_ITEM =
            "com.just_app.diplom.item1";
    private ImageView nameImage;
    private Subject mSubject;
    private TextView tv;
    private String mName;
    private MediaPlayer mp;
    private int i = 0;
    private AssetManager mgr;
    private ObjectMapper mapper;
    private String mItem;
    final String TAG = "myLogs";

    public static Fragment_menu newInstance(String item) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ITEM, item);
        Fragment_menu fragment_menu = new Fragment_menu();
        fragment_menu.setArguments(args);
        return fragment_menu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = (String) getArguments().
                getSerializable(EXTRA_ITEM);
        LoadMediaTask loadMediaTask = new LoadMediaTask();
        loadMediaTask.execute(mItem);
        try {
            mSubject=loadMediaTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    class LoadMediaTask extends AsyncTask<String,String,Subject>{

        @Override
        protected Subject doInBackground(String... params) {
            mgr = getActivity().getAssets();
            mapper = new ObjectMapper();
            try {

                InputStream inputStream;
                inputStream = mgr.open(mItem);
                mSubject = mapper.readValue(inputStream, Subject.class);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mSubject;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v =(ViewGroup) inflater.inflate(
                R.layout.fragment_menu, container, false);
        mp = new MediaPlayer();
        Button forward = (Button) v.findViewById(R.id.forward);
       Button back = (Button) v.findViewById(R.id.back);
        nameImage = (ImageView) v.findViewById(R.id.name_image);
        tv = (TextView) v.findViewById(R.id.tv);
        OnChangePhoto();

        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch ((v.getId())) {
                    case R.id.forward:
                        mp.reset();
                        i=(i+1)%mSubject.content.size();
                        OnChangePhoto();
                        break;
                    case R.id.back:
                            mp.reset();
                            i=(i-1)%mSubject.content.size();
                        if (i<0)
                            i=mSubject.content.size()-1;
                            OnChangePhoto();
                        break;
                }
            }
        };

        forward.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
        return v;
    }

    public  void OnChangePhoto() {
        mgr = getActivity().getAssets();
        if (mSubject.content != null) {
            try {
                Uri imgUri = Uri.parse(mSubject.content.get(i).photos);
                InputStream stream = mgr.open(
                        imgUri.getPath().substring("/android_asset/".length())
                );
                Drawable drawableView = Drawable.createFromStream(stream, null);
                nameImage.setImageDrawable(drawableView);

                mName = mSubject.content.get(i).signature;
                tv.setText(mName);

                Uri soundUri = Uri.parse(mSubject.content.get(i).sounds);
                String sound2 = soundUri.getPath().substring("/android_asset/".length());

                if (sound2 != null) {

                    AssetFileDescriptor afd = getActivity().getAssets().openFd(sound2);
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    afd.close();
                    mp.prepare();
                    mp.start();
                }
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
