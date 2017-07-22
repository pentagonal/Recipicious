package org.pentagonal.android.recipicious.response;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on Linux User : (snsv) on 7/18/17.
 * <p>
 * - Package : org.pentagonal.android.recipicious.response
 * - Project : Recipicious
 *
 * @author pentagonal
 * @since July, 18 2017
 */

@SuppressWarnings({
    "WeakerAccess",
    "UnusedReturnValue",
    "unused",
    "TypeParameterHidesVisibleType",
    "unchecked"
})
abstract public class ResponseMapAble<E>
{
    protected Map map = new CustomMap();

    public class CustomMap<V> extends HashMap
    {
        public V get(Object key, @Nullable V returnDefault)
        {
            if (super.containsKey(key)) {
                return (V)super.get(key);
            }
            return returnDefault;
        }
    }

    public void set(String propName, @Nullable E value)
    {
        map.put(propName, value);
    }

    public @Nullable
        E get(String propName, E defaults)
    {
        if (containsKey(propName)) {
            return (E)map.get(propName);
        }

        return defaults;
    }

    public @Nullable E get(String propName)
    {
        return get(propName, null);
    }

    protected <E> int parseToInt(E num)
    {
        String number = (String) num;
        number = number.trim();
        if (number.length() > 0 && ! number.matches("/[^0-9]/")) {
            try {
                return Integer.parseInt(number);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    final public class MappableObject extends ResponseMapAble<E>
    {
        protected String Name;

        public String getName()
        {
            return Name;
        }

        public MappableObject(String name)
        {
            this.Name = name;
        }
    }

    final public class MappableArray extends ResponseMapAble<E>
    {
        protected String Name;

        public String getName()
        {
            return Name;
        }

        public MappableArray(String name)
        {
            this.Name = name;
        }

    }

    public ResponseMapAble setMapObject(String propName)
    {
        return new MappableObject(propName);
    }

    public ResponseMapAble setMapArray(String propName)
    {
        return new MappableArray(propName);
    }

    public boolean containsKey(String key)
    {
        return map.containsKey(key);
    }

    public boolean containsValue(E value)
    {
        return map.containsValue(value);
    }

    public void clear()
    {
        map.clear();
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "With Map : " + map.toString();
    }
}
