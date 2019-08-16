package com.jbb.server.core.service;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.server.common.Home;
import com.jbb.server.core.domain.UserContact;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class UserContactServiceTest {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    public static class Request {
        public List<UserContact> contacts;
    }

    @Test
    public void testProcessContacts() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json =
            "{\"contacts\":[{\"_objectInstance\":{\"id\":\"1\",\"rawId\":null,\"displayName\":\"王永琦\",\"name\":{\"familyName\":\"王\",\"givenName\":\"永琦\",\"middleName\":\"\",\"formatted\":\"永琦王\"},\"nickname\":null,\"phoneNumbers\":[{\"id\":\"1\",\"pref\":false,\"value\":\"18689465953\",\"type\":\"mobile\"}],\"emails\":null,\"addresses\":null,\"ims\":null,\"organizations\":null,\"birthday\":null,\"note\":null,\"photos\":null,\"categories\":null,\"urls\":null},\"rawId\":\"1\"},{\"_objectInstance\":{\"id\":\"80\",\"rawId\":null,\"displayName\":\"来电专线\",\"name\":{\"familyName\":\"来电\",\"givenName\":\"专线\",\"middleName\":\"\",\"formatted\":\"专线来电\"},\"nickname\":null,\"phoneNumbers\":[{\"id\":\"82\",\"pref\":false,\"value\":\"4008016062\",\"type\":\"回拨电话\"},{\"id\":\"83\",\"pref\":false,\"value\":\"4009180338\",\"type\":\"回拨电话\"},{\"id\":\"84\",\"pref\":false,\"value\":\"07507841000\",\"type\":\"回拨电话\"},{\"id\":\"85\",\"pref\":false,\"value\":\"02886544200\",\"type\":\"回拨电话\"},{\"id\":\"86\",\"pref\":false,\"value\":\"059528394996\",\"type\":\"回拨电话\"},{\"id\":\"87\",\"pref\":false,\"value\":\"4008257119\",\"type\":\"回拨电话\"},{\"id\":\"88\",\"pref\":false,\"value\":\"4008640166\",\"type\":\"回拨电话\"},{\"id\":\"89\",\"pref\":false,\"value\":\"01053767367\",\"type\":\"回拨电话\"},{\"id\":\"90\",\"pref\":false,\"value\":\"02886544201\",\"type\":\"回拨电话\"},{\"id\":\"91\",\"pref\":false,\"value\":\"02886544226\",\"type\":\"回拨电话\"},{\"id\":\"92\",\"pref\":false,\"value\":\"059522564190\",\"type\":\"回拨电话\"},{\"id\":\"93\",\"pref\":false,\"value\":\"059528394740\",\"type\":\"回拨电话\"}],\"emails\":null,\"addresses\":null,\"ims\":null,\"organizations\":null,\"birthday\":null,\"note\":\"这是“来电”回拨模式专线号码。\",\"photos\":[{\"id\":\"96\",\"pref\":false,\"type\":\"url\",\"value\":\"content://com.android.contacts/contacts/80/photo\"}],\"categories\":null,\"urls\":[{\"id\":\"94\",\"pref\":false,\"value\":\"http://www.lightalk.com\",\"type\":\"home\"}]},\"rawId\":\"80\"}]}";
        try {
            JSONObject req = objectMapper.readValue(json, JSONObject.class);
            JSONArray contacts = req.getJSONArray("contacts");

            for (int i = 0; i < contacts.size(); i++) {
                JSONObject contactIns = (JSONObject)contacts.get(i);
                JSONObject contact = (JSONObject)contactIns.get("_objectInstance");
                System.out.println(contact.getString("displayName"));
                JSONArray phoneNumbers = contact.getJSONArray("phoneNumbers");
                for (int j = 0; j < phoneNumbers.size(); j++) {
                    JSONObject phone = (JSONObject)phoneNumbers.get(j);
                    System.out.println("=======" + phone.getString("value"));
                }
            }
            System.out.println(req);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
