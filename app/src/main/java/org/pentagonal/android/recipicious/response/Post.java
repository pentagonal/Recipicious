package org.pentagonal.android.recipicious.response;

import android.support.annotation.Nullable;


/**
 * Created on Linux User : (snsv) on 7/14/17.
 * <p>
 * - Package : org.pentagonal.android.recipicious.response
 * - Project : Recipicious
 *
 * @author pentagonal
 * @since July, 14 2017
 */
public class Post<E> extends ResponseMapAble<E>
{
    @SuppressWarnings("unchecked")
    @Override
    public @Nullable E get(String propName, E defaults)
    {
        if (propName.equals("id") || propName.equals("userId")) {
            return (E) Integer.valueOf(parseToInt(map.get(propName)));
        }

        if (map.containsKey(propName)) {
            return (E)map.get(propName);
        }

        return defaults;
    }
}

