package org.pentagonal.android.recipicious.adapter;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.pentagonal.android.recipicious.response.Base;

import java.io.IOException;

/**
 * Created on Linux User : (snsv) on 7/18/17.
 * <p>
 * - Package : org.pentagonal.android.recipicious.adapter
 * - Project : Recipicious
 *
 * @author pentagonal
 * @since July, 18 2017
 */
@SuppressWarnings({
    "WeakerAccess",
    "unused"
})

public class BaseAdapter<T> extends BaseAdapterAbstract<T>
{
    @Override
    public void write(JsonWriter out, T value) throws IOException
    {
    }

    @Override
    public T read(JsonReader in) throws IOException
    {
        return readInJsonLoop(in, new Base());
    }
}
