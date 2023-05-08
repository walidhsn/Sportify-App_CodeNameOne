/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Reservation;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author WALID
 */
public class ServiceReservation {

    public static ServiceReservation instance = null;

    private ConnectionRequest req;

    public static ServiceReservation getInstance() {
        if (instance == null) {
            instance = new ServiceReservation();
        }
        return instance;
    }

    public ServiceReservation() {
        req = new ConnectionRequest();
    }

    public void ajouterReservation(Reservation reservation, int id_user, int id_terrain) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String url = Statics.BASE_URL + "/reservationApiAjoute/" + id_user + "/" + id_terrain + "?dateReservation=" + dateFormat.format(reservation.getDateReservation()) + "&startTime=" + datetimeFormat.format(reservation.getStartTime()) + "&endTime=" + datetimeFormat.format(reservation.getEndTime()) + "&nbPerson=" + String.valueOf(reservation.getNbPerson());
        req.setUrl(url);

        req.addResponseListener((evt) -> {
            String response = new String(req.getResponseData());
            System.out.println("Reservation ajoutée: " + response);
            if (req.getResponseCode() == 400) {
                // handle error response
                String errorMessage = "";
                try {
                    JSONParser parser = new JSONParser();
                    Map<String, Object> result = parser.parseJSON(new CharArrayReader(response.toCharArray()));
                    errorMessage = (String) result.get("message");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Dialog.show("Error", errorMessage, "OK", null);
            } else if (req.getResponseCode() == 200) {
                Dialog.show("Success", "The Terrain Has been Reserved at the Selected Date and Time.", "OK", null);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public void UpdareReservation(Reservation reservation) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String url = Statics.BASE_URL + "/reservationApiModifier/" + reservation.getId() + "?dateReservation=" + dateFormat.format(reservation.getDateReservation()) + "&startTime=" + datetimeFormat.format(reservation.getStartTime()) + "&endTime=" + datetimeFormat.format(reservation.getEndTime()) + "&nbPerson=" + String.valueOf(reservation.getNbPerson());
        req.setUrl(url);
        req.addResponseListener((evt) -> {
            String response = new String(req.getResponseData());
            System.out.println("Reservation ajoutée: " + response);
            if (req.getResponseCode() == 400) {
                // handle error response
                String errorMessage = "";
                try {
                    JSONParser parser = new JSONParser();
                    Map<String, Object> result = parser.parseJSON(new CharArrayReader(response.toCharArray()));
                    errorMessage = (String) result.get("message");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Dialog.show("Error", errorMessage, "OK", null);
            } else if (req.getResponseCode() == 200) {
                Dialog.show("Success", "The Terrain Has been Reserved at the Selected Date and Time.", "OK", null);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public void DeleteReservation(int id_reservation) {

        String url = Statics.BASE_URL + "/reservationApiSupprimer/" + id_reservation;
        req.setUrl(url);
        req.addResponseListener((a)
                -> {
            String str = new String(req.getResponseData());
            System.out.println("Data == " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        if (req.getResponseCode() == 200) {
            Dialog.show("Success", "The Reservation Has been deleted successfully", "OK", null);
        } else {
            Dialog.show("Error", "it seems there's an error at deleting this Reservation", "OK", null);
        }
    }
    public ArrayList<Reservation> afficherReservation_idClient(int id_user) {
        ArrayList<Reservation> result = new ArrayList<>();
        String url = Statics.BASE_URL + "/reservationApiAfficherIdUser/" + id_user;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    String strData = new String(req.getResponseData(), "UTF-8");
                    Map<String, Object> mapReservations = jsonp.parseJSON(new StringReader(strData));
                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapReservations.get("root");
                    if (listOfMaps != null) {
                        for (Map<String, Object> obj : listOfMaps) {
                            Reservation r = new Reservation();
                            float id = Float.parseFloat(obj.get("id").toString());                 
                            Double doubleValue = Double.parseDouble(obj.get("nbPerson").toString());
                            int nbperson = (int) Math.round(doubleValue);
                            SimpleDateFormat dateFormat_d = new SimpleDateFormat("yyyy-MM-dd");
                            String date_Res = obj.get("dateReservation").toString();          
                            Date dateReservation = dateFormat_d.parse(date_Res);
                            String starttime = obj.get("startTime").toString();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            Date startTime = dateFormat.parse(starttime);
                            String endtime = obj.get("endTime").toString();          
                            Date endTime = dateFormat.parse(starttime);
                            boolean resStatus = Boolean.parseBoolean(obj.get("resStatus").toString());

                            // setting the values :
                            r.setId((int) id);
                            r.setNbPerson(nbperson);
                            r.setDateReservation(dateReservation);
                            r.setStartTime(startTime);
                            r.setEndTime(endTime);
                            r.setResStatus(resStatus);
                            result.add(r);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

}
