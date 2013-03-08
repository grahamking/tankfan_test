package org.gkgk.tankfan;

import android.test.AndroidTestCase;

public class DBHelperTest extends AndroidTestCase {

    public void testMakeCreate() {

        DBHelper dbh = new DBHelper(null, null, null, 1);
        String result;
        result = dbh.makeCreate("test_table", new String[]{"one", "two"});

        this.assertTrue("Should start with CREATE TABLE",
                        result.startsWith("CREATE TABLE test_table ("));

        this.assertTrue("Should have 'one' as text column",
                        result.indexOf("one text,") != -1);

        this.assertTrue("Should have 'two' as text column",
                        result.indexOf("two text") != -1);

        this.assertTrue("Should end with closing bracket and no comma",
                        result.endsWith("text)"));
    }
}
