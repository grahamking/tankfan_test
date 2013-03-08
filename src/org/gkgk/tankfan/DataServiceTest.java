package org.gkgk.tankfan;

import android.test.AndroidTestCase;

public class DataServiceTest extends AndroidTestCase {

    public void testParse() {

        DataService serv = new DataService();
        serv.jsonData = "{\"beers\": [{}], \"events\": [{}], \"breweries\": [{}]}";
        serv.parse();

        this.assertEquals("beerJSON not correct",
                          "[{}]",
                          serv.beerJSON.toString());
    }
}
