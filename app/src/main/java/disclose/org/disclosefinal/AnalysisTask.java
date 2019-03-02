package disclose.org.disclosefinal;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AnalysisTask extends AsyncTask<String,Void,HashMap<String, Double>> {
    @Override
    protected HashMap<String, Double> doInBackground(String... text) {
        double score = 0;
        HashMap<String, Double> hashMap = new HashMap<>();

        ToneAnalyzer service = new ToneAnalyzer("2017-09-21");
        service.setUsernameAndPassword("ee4c7361-8aa6-4d11-8124-af1357f7ad84", "6kZb6Gigp3a0");
//        this.text = text;
        ToneOptions toneOptions = new ToneOptions.Builder().html(text[0]).build();
        ToneAnalysis tone = service.tone(toneOptions).execute();
        JsonParser parser = new JsonParser();

        JsonObject jsonArgs = parser.parse(String.valueOf(tone)).getAsJsonObject();

        JsonElement doctone = jsonArgs.get("document_tone");
        JsonObject dtObj = doctone.getAsJsonObject();
        JsonElement toneElem = dtObj.get("tones");
        JsonArray tones = toneElem.getAsJsonArray();

        for (JsonElement pa : tones) {

            JsonObject toneObj = pa.getAsJsonObject();
            JsonElement toneScore = toneObj.get("score");
            JsonElement toneID = toneObj.get("tone_id");
            score = toneScore.getAsDouble();


            if(     toneID.getAsString().equals("sadness") ||
                    toneID.getAsString().equals("joy") ||
                    toneID.getAsString().equals("analytical")){

                System.out.println("Detected Joy or Sadness");
                //return (toneScore +" "+ toneID);
                hashMap.put(toneID.getAsString().toUpperCase(), toneScore.getAsDouble());
                return hashMap;

            }
//            System.out.println(toneScore +" "+ toneID);
//            hashMap.put(toneID.getAsString(), toneScore.getAsDouble());
//            return hashMap;

        }
        System.out.println(text);
        hashMap.put("NEUTRAL", 1.0);
        return hashMap;

    }

    protected void onPostExecute(Double score) {
        //super.onPostExecute(score);
//        MessageDisplay_Act.listView.
    }

}
