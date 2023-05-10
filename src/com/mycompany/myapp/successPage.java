/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Reservation;
import com.mycompany.shared.SharedComponents;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author WALID
 */
public class successPage extends Form {

    private Button backButton;
    //The Account Sid and Token at console.twilio.com
    public static final String ACCOUNT_SID = "ACe1969f27c9ebaba39c1c2e19532653e5";
    public static final String AUTH_TOKEN = "53b72d6c70636292563f45e93c8725f3";

    public successPage(Resources theme, Reservation r) {
        super("Successful Payment");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0x343a40);  // Set the background color of the Form

        Font largeBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        Label message = new Label("This confirms that we've just received your online payment for your Reservation.");
        message.getUnselectedStyle().setFont(largeBoldSystemFont);
        add(message);
        backButton = new Button("Back To List");
        backButton.addActionListener(e -> {
            removeAll();
            new myReservation(theme).show();
        });
        add(backButton);
        // Back button on the ToolBar :
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeAll();
                new myReservation(theme).show();
            }
        };
        getToolbar().addCommandToLeftBar(back);
        send_sms_to_Client(Integer.parseInt(SharedComponents.user.getPhone()), r);
    }

    private void send_sms_to_Client(int phone, Reservation reservation) {

        try {
            //init twilio
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String send_number = "+216" + String.valueOf(phone);
            String from_number = "+15075650863";
            String body = "Sportify: Thank you for making a reservation with us! We look forward to seeing you on : ";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String date_res = dateFormat.format(reservation.getDateReservation());
            String start_time = timeFormat.format(reservation.getStartTime());
            String end_time = timeFormat.format(reservation.getEndTime());
            body += date_res + ", at : " + start_time + " ,ends at : " + end_time;
            Message message = Message
                    .creator(
                            new PhoneNumber(send_number),
                            new PhoneNumber(from_number),
                            body
                    )
                    .create();
            System.out.println("message Sent!!.");
        } catch (final ApiException e) {

            System.err.println(e);

        }
    }
}
