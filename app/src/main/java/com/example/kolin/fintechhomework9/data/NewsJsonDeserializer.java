package com.example.kolin.fintechhomework9.data;

import com.example.kolin.fintechhomework9.data.model.NewsPojo;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by kolin on 27.11.2017.
 */

public class NewsJsonDeserializer implements JsonDeserializer<NewsPojo> {
    @Override
    public NewsPojo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()){

            NewsPojo newsPojo = new NewsPojo();

            JsonObject jsonObject = json.getAsJsonObject();

            newsPojo.setId(jsonObject.get("id").getAsInt());
            newsPojo.setName(jsonObject.get("name").getAsString());
            newsPojo.setText(jsonObject.get("text").getAsString());
            newsPojo.setBankInfoTypeId(jsonObject.get("bankInfoTypeId").getAsInt());

            JsonObject publicationDate = jsonObject.get("publicationDate").getAsJsonObject();

            newsPojo.setPublicationDate(publicationDate.get("milliseconds").getAsLong());

            return newsPojo;
        }
        return null;
    }
}
