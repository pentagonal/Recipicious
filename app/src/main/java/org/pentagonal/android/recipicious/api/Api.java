// @todo make api call collection

package org.pentagonal.android.recipicious.api;

import org.pentagonal.android.recipicious.adapter.PostAdapter;
import org.pentagonal.android.recipicious.response.Post;
import java.io.IOException;

/**
 * Created on Linux User : (snsv) on 7/14/17.
 * <p>
 * - Package : org.pentagonal.android.recipicious.api
 * - Project : Recipicious
 *
 * @author pentagonal
 * @since July, 14 2017
 */
public class Api
{
    public static Post getPost(int offset)
        throws IOException
    {
        return HttpApiFactory
            .post(
                Post.class,
                new PostAdapter(),
                "a.php?offset=" + offset// + offset
            ).setParam(
                "title", "The Title Of Post"
            ).setParam(
                "body[Must]", "Content of must be a!"
            ).setParam(
                "body[Hello]", "Hello you!"
            ).setParam(
                "id",
                1
            ).setParam(
                "userId",
                2
            ).send();
    }
}
