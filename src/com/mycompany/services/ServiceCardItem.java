package com.mycompany.services;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.CardItem;
import com.mycompany.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceCardItem {

    private ArrayList<CardItem> cardItems;
    private static ServiceCardItem instance = null;
    private boolean resultOK;
    private ConnectionRequest req;

    private ServiceCardItem() {
        req = new ConnectionRequest();
    }

    public static ServiceCardItem getInstance() {
        if (instance == null) {
            instance = new ServiceCardItem();
        }
        return instance;
    }

    public boolean addCardItem(CardItem cardItem) {
        String url = Statics.BASE_URL + "/addcarditem";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("libelle", cardItem.getLibelle());
        req.addArgument("prix", String.valueOf(cardItem.getPrix()));
        req.addArgument("quantite", String.valueOf(cardItem.getQuantite()));
        req.addArgument("card_id", String.valueOf(cardItem.getCard_id()));
        req.addArgument("owner_id", String.valueOf(cardItem.getOwner_id()));

        req.addResponseListener(evt -> {
            resultOK = req.getResponseCode() == 200;
            req.removeResponseListener((ActionListener<NetworkEvent>) this);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        if (resultOK) {
            ToastBar.showMessage("L'article a été ajouté avec succès au panier", FontImage.MATERIAL_ACCESS_TIME);
        } else {
            ToastBar.showMessage("Erreur lors de l'ajout de l'article au panier", FontImage.MATERIAL_ERROR);
        }
        return resultOK;
    }
public ArrayList<CardItem> parseCardItems(String jsonText) {
        try {
            cardItems = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> cardItemsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) cardItemsListJson.get("root");

            for (Map<String, Object> obj : list) {
                CardItem cardItem = new CardItem();
                float id_card_item = Float.parseFloat(obj.get("id").toString());
                cardItem.setId((int) id_card_item);
                cardItem.setLibelle(obj.get("libelle").toString());
                float prix = Float.parseFloat(obj.get("prix").toString());
                cardItem.setPrix((int) prix);
                float quantite = Float.parseFloat(obj.get("quantite").toString());
                cardItem.setQuantite((int) quantite);
                float card_id = Float.parseFloat(obj.get("card_id").toString());
                cardItem.setCard_id((int) card_id);
                float owner_id = Float.parseFloat(obj.get("owner_id").toString());
                cardItem.setOwner_id((int) owner_id);

                cardItems.add(cardItem);
            }
        } catch (IOException ex) {
            Log.e(ex);
        }

        return cardItems;
    }
 public ArrayList<CardItem> getAllCardItems() {
        String url = Statics.BASE_URL + "/cardItemjson";
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(evt -> {
            String jsonText = new String(req.getResponseData());
            cardItems = parseCardItems(jsonText);
            req.removeResponseListener(evt2 -> {
                // listener removed
            });
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return cardItems;
    }
 public boolean editCardItem(CardItem cardItem) {
    String url = Statics.BASE_URL + "/editCardItem";
    req.setUrl(url);
    req.setPost(false);
    req.addArgument("id", String.valueOf(cardItem.getId()));
    req.addArgument("libelle", cardItem.getLibelle());
    req.addArgument("prix", String.valueOf(cardItem.getPrix()));
    req.addArgument("quantite", String.valueOf(cardItem.getQuantite()));
    req.addArgument("card_id", String.valueOf(cardItem.getCard_id()));
    req.addArgument("owner_id", String.valueOf(cardItem.getOwner_id()));

    req.addResponseListener(evt -> {
        resultOK = req.getResponseCode() == 200;
        req.removeResponseListener((ActionListener<NetworkEvent>) this);
    });
    NetworkManager.getInstance().addToQueueAndWait(req);

    if (resultOK) {
        ToastBar.showMessage("L'élément de carte a été modifié avec succès", FontImage.MATERIAL_ACCESS_TIME);
    } else {
        ToastBar.showMessage("Erreur lors de la modification de l'élément de carte", FontImage.MATERIAL_ERROR);
    }
    return resultOK;
}
 public boolean deleteCardItem(CardItem cardItem) {
    String url = Statics.BASE_URL + "/deletecardItem/" + cardItem.getId();
    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
    req.setPost(false);

    req.addResponseListener(evt -> {
        resultOK = req.getResponseCode() == 200;
        req.removeResponseListener(evt2 -> {
            // listener removed
        });
    });
    NetworkManager.getInstance().addToQueueAndWait(req);

    if (resultOK) {
        ToastBar.showMessage("Carte supprimée avec succès", FontImage.MATERIAL_ACCESS_TIME);
    } else {
        ToastBar.showMessage("Erreur lors de la suppression de la carte", FontImage.MATERIAL_ERROR);
    }
    return resultOK;
}
}