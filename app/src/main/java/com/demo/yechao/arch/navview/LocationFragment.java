//package com.demo.yechao.arch.navview;
//
//import android.support.v4.app.Fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import com.demo.yechao.arch.R;
//
//
///**
// * @Author yechao111987@126.com
// * @date 2018/11/29 15:09
// */
//public class LocationFragment extends Fragment {
//    public static LocationFragment newInstance(String param1) {
//        LocationFragment fragment = new LocationFragment();
//        Bundle args = new Bundle();
//        args.putString("agrs1", param1);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public LocationFragment() {
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_location, container, false);
//        Bundle bundle = getArguments();
//        String agrs1 = bundle.getString("agrs1");
//        TextView tv = (TextView) view.findViewById(R.id.tv_location);
//        tv.setText(agrs1);
//        return view;
//    }
//}
