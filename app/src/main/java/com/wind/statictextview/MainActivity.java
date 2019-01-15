package com.wind.statictextview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private TestKeywordMoreTextView text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_view = findViewById(R.id.text_view);
    }

    public void changeText(View v) {
        String label = "秦始皇（前259年—前210年），嬴姓，赵氏，名政，又名赵正（政）、秦政，或称祖龙 [1-2]  ，秦庄襄王之子。 [3]  中国历史上著名的政治家、战略家、改革家，完成华夏大一统的铁腕政治人物，也是中国第一个称皇帝的君主。 [4] \n" +
                "秦始皇是出生于赵国都城邯郸（今邯郸），并在此度过了少年时期。前247年，13岁时即王位。 [5]  前238年，22岁时，在故都雍城举行了国君成人加冕仪式，开始“亲理朝政”，除掉吕不韦、嫪毐等人， [6]  重用李斯、尉缭，自前230年至前221年，先后灭韩、赵、魏、楚、燕、齐六国，39岁时完成了统一中国大业，建立起一个以汉族为主体统一的中央集权的强大国家——秦朝，并奠定中国本土的疆域。 [4] \n" +
                "秦始皇认为自己的功劳胜过之前的三皇五帝，采用三皇之“皇”、五帝之“帝”构成“皇帝”的称号， [7]  是中国历史上第一个使用“皇帝”称号的君主，所以自称“始皇帝”。同时在中央实行三公九卿，管理国家大事。地方上废除分封制，代以郡县制，同时书同文，车同轨，统一度量衡。对外北击匈奴，南征百越，修筑万里长城，修筑灵渠，沟通水系。\n" +
                "但是到了后期，求仙梦想长生，苛政虐民，扼杀民智，动摇了秦朝统治的根基，前210年，秦始皇东巡途中驾崩于邢台沙丘。 [8] \n" +
                "秦始皇是中国历史上一位叱咤风云富有传奇色彩的划时代人物，是中国历史上第一个大一统王朝——秦王朝的开国皇帝，对中国和世界历史产生深远影响，把中国推向大一统时代，奠定中国两千余年政治制度基本格局，被明代思想家李贽誉为“千古一帝” [9]  。 [4] ";
        text_view.setKeyword("秦始皇");
        text_view.setText(label);
    }

    public void changeColor(View v) {
        text_view.setTextColor(Color.RED);
    }

    public void changeSize(View v) {
        text_view.setTextSize(24, TypedValue.COMPLEX_UNIT_SP);
    }
}
