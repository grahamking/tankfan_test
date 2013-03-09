package org.gkgk.tankfan;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.ContentValues;
import android.test.AndroidTestCase;

public class DataServiceTest extends AndroidTestCase {

    static final String beerJSON = "{\"name\":\"Hoyne Dark Matter\",\"location\": \"Victoria, B.C.\", \"style\":\"Porter\",\"brewery\":\"Hoyne Brewing\",\"abv\":\"5.3%\",\"description\":\"Dark Matter: Deep, Deceptively Dark, Delicious. Is it a Stout? Is it a Brown? Is it a Porter? Dark Matter is elusive, soft on the tongue with hints of subtle roasted chocolate. Sometimes you need to go somewhere dark in order to see.\", \"url\": \"http://www.ratebeer.com/beer/hoyne-dark-matter/168799/\", \"pic\": \"http://www.beermebc.com/wp-content/uploads/2012/09/2012_09_GCBF-349-300x449.jpg\", \"ignoreIt\": \"Ignore this key\"}";

    static final String beerJSONArr = "["+ beerJSON +"]";

    public void testParse() {

        DataService serv = new DataService();
        serv.jsonData = "{\"beers\": "+ beerJSONArr +", \"events\": [{}], \"breweries\": [{}]}";
        serv.parse();

        try {
            JSONArray expected = new JSONArray(beerJSONArr);
            this.assertEquals("beerJSON wrong size",
                            expected.length(), serv.beerJSON.length());
            this.assertEquals("beerJSON wrong name",
                            expected.getJSONObject(0).getString("name"),
                            serv.beerJSON.getJSONObject(0).getString("name"));
        }
        catch (JSONException exc) {
            this.fail("JSONException in testParse: " + exc);
        }
    }

    public void testToContentValues() {

        JSONArray jsonArr = null;
        try{
            jsonArr = new JSONArray(beerJSONArr);
        }
        catch (JSONException exc) {
            this.fail("JSONException parsing beerJSON: " + exc);
        }

        DataService serv = new DataService();

        ContentValues v = null;
        try {
            List<ContentValues> vals = serv.toContentValues(
                    jsonArr, DBHelper.BEERS_COLUMNS);
            v = vals.get(0);
        }
        catch (JSONException exc) {
            this.fail("JSONException in toContentValues: " + exc);
        }

        this.assertFalse("Should only include requested keys",
                         v.containsKey("ignoreIt"));

        this.assertTrue("Should include all requested keys",
                        v.containsKey("abv"));

        this.assertEquals("Name not correct",
                          v.get("name"), "Hoyne Dark Matter");
    }
}
