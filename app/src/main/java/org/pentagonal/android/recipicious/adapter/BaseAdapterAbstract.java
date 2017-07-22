package org.pentagonal.android.recipicious.adapter;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.pentagonal.android.recipicious.response.ResponseMapAble;

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

@SuppressWarnings({"WeakerAccess", "unchecked"})
abstract public class  BaseAdapterAbstract<T> extends TypeAdapter<T>
{
    protected T readInJsonLoop(
        JsonReader jsonReader,
        ResponseMapAble responseMapAble
    ) throws IOException {

        String keyName = null;
        ResponseMapAble lastMappable = null;
        while (jsonReader.hasNext()) {
            JsonToken peek = jsonReader.peek();
            switch (peek) {
                case BEGIN_ARRAY:
                        if (keyName != null) {
                            if (lastMappable != null) {
                                lastMappable = lastMappable.setMapArray(keyName);
                            } else {
                                lastMappable = responseMapAble.setMapArray(keyName);
                                responseMapAble.set(keyName, lastMappable);
                            }
                        }

                        jsonReader.beginArray();
                    break;
                case END_ARRAY:
                        jsonReader.endArray();
                        keyName = null;
                        lastMappable = null;
                    break;
                case BEGIN_OBJECT:
                        if (keyName != null) {
                            if (lastMappable != null) {
                                lastMappable = lastMappable.setMapObject(keyName);
                            } else {
                                lastMappable = responseMapAble.setMapObject(keyName);
                                responseMapAble.set(keyName, lastMappable);
                            }
                        }
                        jsonReader.beginObject();
                    break;
                case END_OBJECT:
                        jsonReader.endObject();
                        keyName     = null;
                        lastMappable = null;
                    break;
                case STRING:
                        if (lastMappable != null) {
                            lastMappable.set(keyName, jsonReader.nextString());
                        } else {
                            responseMapAble.set(keyName, jsonReader.nextString());
                        }
                    break;
                case NUMBER:
                        if (lastMappable != null) {
                            lastMappable.set(keyName, jsonReader.nextInt());
                        } else {
                            responseMapAble.set(keyName, jsonReader.nextInt());
                        }
                    break;
                case BOOLEAN:
                        if (lastMappable != null) {
                            lastMappable.set(keyName, jsonReader.nextBoolean());
                        } else {
                            responseMapAble.set(keyName, jsonReader.nextBoolean());
                        }
                    break;
                case NAME:
                        keyName = jsonReader.nextName();
                    break;
                case END_DOCUMENT:
                        jsonReader.close();
                    break;
                case NULL:
                        jsonReader.nextNull();
                        if (lastMappable != null) {
                            lastMappable.set(keyName, null);
                        } else {
                            responseMapAble.set(keyName, null);
                        }
                    break;
            }
        }

        return (T) responseMapAble;
    }
}
