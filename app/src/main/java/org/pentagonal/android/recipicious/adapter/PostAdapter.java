package org.pentagonal.android.recipicious.adapter;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.pentagonal.android.recipicious.response.Post;

import java.io.IOException;

/**
 * Created on Linux User : (snsv) on 7/14/17.
 * <p>
 * - Package : org.pentagonal.android.recipicious.adapter
 * - Project : Recipicious
 *
 * @author pentagonal
 * @since July, 14 2017
 */
public class PostAdapter extends BaseAdapterAbstract<Post>
{
    @SuppressWarnings("unchecked")
    @Override
    public void write(JsonWriter out, Post post) throws IOException
    {
        out.beginObject()
            .name("id").value((int) post.get("id"))
            .name("title").value((String) post.get("title"))
            .name("userId").value((int) post.get("userId"))
            .name("body").value((String) post.get("body"))
        .endObject();
    }

    @Override
    public Post read(JsonReader in) throws IOException
    {
        return readInJsonLoop(in, new Post());
    }
}
