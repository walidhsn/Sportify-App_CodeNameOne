package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.util.Callback;
import com.mycompany.entities.Card;
import com.mycompany.entities.CardItem;
import com.mycompany.entities.Commande;
import static com.mycompany.services.ServiceTerrain.instance;
import com.mycompany.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardService {
    private Card card = null;
    private ConnectionRequest req;
     public static CardService instance = null;
    public static CardService getInstance() {
        if (instance == null) {
            instance = new CardService();
        }
        return instance;
    }

    public CardService() {
        req = new ConnectionRequest();
    }
 public List<CardItem> parseCardItems(List<Map<String, Object>> cardItemsJson) {
    List<CardItem> cardItems = new ArrayList<>();
    try {
        for (Map<String, Object> cardItemJson : cardItemsJson) {
            String idValue = cardItemJson.get("id").toString();
            System.out.println("ID value: " + idValue);
            double idDouble = Double.parseDouble(idValue);
            int id = (int) idDouble;
            Double quantity = Double.parseDouble(cardItemJson.get("quantity").toString());
            String libelle = cardItemJson.get("libelle").toString();
            float prix = Float.parseFloat(cardItemJson.get("prix").toString());
            float ownerId = Float.parseFloat(cardItemJson.get("owner_id").toString());

            CardItem cardItem = new CardItem(id, libelle, prix, quantity.intValue(), 0, (int)ownerId);
            cardItems.add(cardItem);
        }
    } catch (Exception ex) {
        Log.e(ex);
    }

    return cardItems;
}








    public Card parseCard(String jsonText) {
        Card card = null;
        try {
            JSONParser parser = new JSONParser();
            Map<String, Object> cardJson = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            //int id = Integer.parseInt(cardJson.get("id").toString());
            int id = (int) Double.parseDouble(cardJson.get("id").toString());

            float total = Float.parseFloat(cardJson.get("total").toString());

            List<Map<String, Object>> cardItemsJson = (List<Map<String, Object>>) cardJson.get("cardItems");
            List<CardItem> cardItems = parseCardItems(cardItemsJson);

            card = new Card(id, total, cardItems);
        } catch (Exception ex) {
            Log.e(ex);
        }

        return card;
    }

    public String showByUserId(String userId) {
        String url = Statics.BASE_URL + "/api/v1/cart/user/" + userId;
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(url);
        req.setPost(false);

        final StringBuilder responseBuilder = new StringBuilder();

        req.addResponseListener(evt -> {
            byte[] responseData = req.getResponseData();
            if (responseData != null) {
                String jsonText = new String(responseData);
                responseBuilder.append(jsonText);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        String jsonResponse = responseBuilder.toString();
        System.out.println(jsonResponse);

        return jsonResponse;
    }
    
  //  public void checkout(int cardId, int userId, Callback<Integer> callback) {
  public void checkout(int cardId, int userId, Commande c) {
        String url = Statics.BASE_URL + "/api/commandejson/checkout/" + cardId + "/" + userId + "?firstname="+c.getFirstname()+"&lastname="+c.getLastname()+"&email="+c.getEmail()+"&tel="+c.getTel()+"&city="+c.getCity()+"&address="+c.getAdresse();
        req.setUrl(url);
        req.addResponseListener((a)
                -> {
            String str = new String(req.getResponseData());
            System.out.println("Data == " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        if (req.getResponseCode() == 200) {
            Dialog.show("Success", "The Commande Has been added successfully", "OK", null);
        }
    }

    public interface Callback<T> {
        void onSuccess(T result);

        void onError(String errorMessage);

        // Add the following method
        void onSuccessInt(int result);
    }

    /*public Card showByUserId(String userId) {
    String url = Statics.BASE_URL + "/api/v1/cart/user/" + userId;
    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
    req.setPost(false);

    final Card[] cardWrapper = new Card[1]; // Mutable container

    req.addResponseListener(evt -> {
        String jsonText = new String(req.getResponseData());
        Card parsedCard = parseCard(jsonText);
        cardWrapper[0] = parsedCard; // Assign the parsed card to the wrapper
        // Use the 'parsedCard' object as needed
    });
    NetworkManager.getInstance().addToQueueAndWait(req);
    Card retrievedCard = cardWrapper[0];
    System.out.println(retrievedCard);

    // Return null or handle the case when card is not retrieved successfully
    return retrievedCard;
}*/
}
 /* public Card parseCard(String jsonText) {
    Card card = null;
    try {
        if (jsonText != null) {
            JSONParser parser = new JSONParser();
            Map<String, Object> cardJson = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            float id = Float.parseFloat(cardJson.get("id").toString());
            float total = Float.parseFloat(cardJson.get("total").toString());

            // Parse the "user" field
            Object userObject = cardJson.get("user");
            int userId = 0;
            if (userObject instanceof Map) {
                Map<String, Object> userJson = (Map<String, Object>) userObject;
                userId = userJson.containsKey("id") ? ((Number) userJson.get("id")).intValue() : 0;
            } else if (userObject instanceof List) {
                List<Map<String, Object>> userList = (List<Map<String, Object>>) userObject;
                if (!userList.isEmpty()) {
                    Map<String, Object> userJson = userList.get(0);
                    userId = userJson.containsKey("id") ? ((Number) userJson.get("id")).intValue() : 0;
                }
            }

            // Check if "cardItems" field exists and is not null
            Object cardItemsObject = cardJson.get("cardItems");
            List<CardItem> cardItems = new ArrayList<>();
            if (cardItemsObject instanceof List) {
                List<Map<String, Object>> cardItemsJson = (List<Map<String, Object>>) cardItemsObject;
                cardItems = parseCardItems(cardItemsJson);
            }

            card = new Card((int) id, total, cardItems);
        }
    } catch (IOException ex) {
        Log.e(ex);
    } catch (Exception ex) {
        Log.e(ex);
    }

    return card;
}*/

    // hedhy kanet temchy public List<CardItem> parseCardItems(List<Map<String, Object>> cardItemsJson) {
   /* List<CardItem> cardItems = new ArrayList<>();
    if (cardItemsJson != null) {
        for (Map<String, Object> cardItemJson : cardItemsJson) {
            int id = ((Number) cardItemJson.get("id")).intValue();
            int quantity = ((Number) cardItemJson.get("quantity")).intValue();
            String libelle = (String) cardItemJson.get("libelle");
            float prix = Float.parseFloat(cardItemJson.get("prix").toString());
            int ownerId = ((Number) cardItemJson.get("owner_id")).intValue();

            CardItem cardItem = new CardItem( libelle, prix, quantity);
            cardItems.add(cardItem);
            System.out.println(cardItems);
        }
    }
    return cardItems;
}*/