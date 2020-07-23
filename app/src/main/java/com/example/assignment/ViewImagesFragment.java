package com.example.assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.common.base.Charsets;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewImagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewImagesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<GetUrl> UrlList;
    RecyclerView recyclerView;

    public ViewImagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewImagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewImagesFragment newInstance(String param1, String param2) {
        ViewImagesFragment fragment = new ViewImagesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_view_images, container, false);

         class VersionTask extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... strings) {
                String result = null;
                URL url;
                HttpURLConnection connection = null;
                try {
                    url = new URL("https://jsonplaceholder.typicode.com/photos");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStreamReader is = new InputStreamReader(connection.getInputStream(), Charsets.UTF_8);
                    BufferedReader reader = new BufferedReader(is);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    String json = sb.toString();

                    json = json.replace("\\", "");
                    json = json.substring(1);
                    json = json.substring(0, json.length() - 2);
                    result = json;

                } catch (IOException e) {
                    Log.d("VersionTask", Log.getStackTraceString(e));
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                UrlList = new ArrayList<>();
                if (result != null) {
                    try {
                        result.replace("[" , "");
                        result.replace("]" , "");

                        result.replace("},{" , "}@{" );
                        String objs[] = result.split("@");
                        System.out.println("\n size = \n"+ objs.length);
                        for(String obj : objs){
                            JSONObject object = new JSONObject(obj);
                            GetUrl url = new GetUrl(object.getString("thumbnailUrl"));
                            UrlList.add(url);
                        }
                        System.out.println("size = "+ UrlList.size());

                        PLAdapter plAdapter = new PLAdapter(UrlList);
                        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                        recyclerView.setAdapter(plAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        VersionTask versionTask = new VersionTask();
         versionTask.execute();
        return view;
    }

    public class PLAdapter extends RecyclerView.Adapter<ViewImagesFragment.PLAdapter.PSViewHolder>{

        private List<GetUrl> PRList;

        public PLAdapter(List<GetUrl> list) {
            this.PRList = list;
        }

        @NonNull
        @Override
        public ViewImagesFragment.PLAdapter.PSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.card , parent , false);
            return new ViewImagesFragment.PLAdapter.PSViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull final ViewImagesFragment.PLAdapter.PSViewHolder holder, int position) {
            final GetUrl url = PRList.get(position);

            class DownLoadImageTask extends AsyncTask<String,Void, Bitmap>{
                /*
                    doInBackground(Params... params)
                        Override this method to perform a computation on a background thread.
                 */
                protected Bitmap doInBackground(String...urls){
                    String urlOfImage = url.getUrl();
                    Bitmap logo = null;
                    try{
                        InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                        logo = BitmapFactory.decodeStream(is);
                    }catch(Exception e){ // Catch the download exception
                        e.printStackTrace();
                    }
                    return logo;
                }

                /*
                    onPostExecute(Result result)
                        Runs on the UI thread after doInBackground(Params...).
                 */
                protected void onPostExecute(Bitmap result){
                    holder.image.setImageBitmap(result);
                }
            }

            DownLoadImageTask downLoadImageTask = new DownLoadImageTask();
            downLoadImageTask.execute();
        }



        @Override
        public int getItemCount() {
            return PRList.size();
        }

        class PSViewHolder extends RecyclerView.ViewHolder{

            ImageView image;

            public PSViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.recycler_image);
            }
        }
    }
}