package org.pentagonal.android.recipicious.util;

import java.io.Reader;

/**
 * Created on Linux User : (snsv) on 7/18/17.
 * <p>
 * - Package : org.pentagonal.android.recipicious.util
 * - Project : Recipicious
 *
 * @author pentagonal
 * @since July, 18 2017
 */
public class JsonReader extends com.google.gson.stream.JsonReader
{
    /**
     * Creates a new instance that reads a JSON-encoded stream from {@code in}.
     */
    public JsonReader(Reader in) {
        super(in);
    }
}
